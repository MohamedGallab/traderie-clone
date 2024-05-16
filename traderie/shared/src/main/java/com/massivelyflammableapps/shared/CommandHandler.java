package com.massivelyflammableapps.shared;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class CommandHandler {
    String classPath = "./traderie/shared/src/main/java/com/massivelyflammableapps/shared/commands/";

    public boolean createCommandFile(String commandClass, String commandCode) {
        String sourcePath = classPath + commandClass + ".java";

        File sourceFile = new File(sourcePath);
        if (sourceFile.exists()) {
            return false;
        }

        try (FileWriter aWriter = new FileWriter(sourcePath, false)) {
            aWriter.write(commandCode);
            String[] source = { sourcePath };
            int result = com.sun.tools.javac.Main.compile(source);
            return (result == 0);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCommandFile(String commandClass) {
        String sourcePath = classPath + commandClass + ".java";

        try {
            File sourceFile = new File(sourcePath);
            if (!sourceFile.exists()) {
                return false;
            }

            return sourceFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Object runIt(String commandClass, Object paramsObj[]) {
        try {
            @SuppressWarnings("rawtypes")
            Class params[] = new Class[paramsObj.length];

            for (int i = 0; i < params.length; i++) {
                params[i] = (Class<?>) paramsObj[i].getClass();
            }

            URL classUrl = Paths.get(classPath).toUri().toURL();
            URL[] classUrls = { classUrl };

            URLClassLoader urlClassLoader = new URLClassLoader(classUrls);

            Class<?> thisClass = urlClassLoader.loadClass("com.massivelyflammableapps.shared.commands." + commandClass);
            Object iClass = thisClass.getDeclaredConstructor().newInstance();
            Method thisMethod = thisClass.getDeclaredMethod("execute", params);
            urlClassLoader.close();

            return thisMethod.invoke(iClass, paramsObj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
