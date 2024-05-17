package com.massivelyflammableapps.shared;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CommandHandler {
    String classPath = "";

    public boolean createCommandFile(String commandClass, String commandCode) {
        String jarPath = "C:/Users/megad/Desktop/Scalable/traderie-clone/traderie/offers-service/target/";

        String sourcePath = jarPath.toString() + "commands/" + commandClass + ".java";

        File sourceFile = new File(sourcePath);
        if (sourceFile.exists()) {
            instanceMap.remove(commandClass);
            return false;
        }

        try (FileWriter aWriter = new FileWriter(sourcePath, false)) {
            aWriter.write(commandCode);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCommandFile(String commandClass) {
        String jarPath = "C:/Users/megad/Desktop/Scalable/traderie-clone/traderie/offers-service/target/";

        String sourcePath = jarPath.toString() + "commands/" + commandClass + ".java";

        try {
            File sourceFile = new File(sourcePath);
            if (!sourceFile.exists()) {
                return false;
            }

            instanceMap.remove(commandClass);
            return sourceFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Map<String, Object> instanceMap = new HashMap<>();

    public Object runIt(String commandClass, Object paramsObj[]) {
        String jarPath = "C:/Users/megad/Desktop/Scalable/traderie-clone/traderie/offers-service/target/";
        try {
            @SuppressWarnings("rawtypes")
            Class params[] = new Class[paramsObj.length];

            for (int i = 0; i < params.length; i++) {
                params[i] = (Class<?>) paramsObj[i].getClass();
            }

            if (instanceMap.containsKey(commandClass)) {
                Object existingInstance = instanceMap.get(commandClass);
                Method thisMethod = existingInstance.getClass().getDeclaredMethod("execute", params);
                return thisMethod.invoke(existingInstance, paramsObj);
            }

            try {

                String filePath = jarPath.toString() + "commands/" + commandClass + ".java";
                File javaFile = new File(filePath);

                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
                Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(javaFile);

                List<String> optionList = new ArrayList<>();
                optionList.add("-d");
                optionList.add("out");

                JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, optionList, null,
                        compilationUnits);
                boolean success = task.call();

                if (success) {
                    System.out.println("Compilation successful.");

                    URLClassLoader classLoader = URLClassLoader
                            .newInstance(new URL[] { new File("out").toURI().toURL() });
                    String className = "com.massivelyflammableapps.shared." + commandClass;
                    Class<?> cls = Class.forName(className, true, classLoader);

                    Object instance = cls.getDeclaredConstructor().newInstance();
                    System.out.println("Instance created: " + instance);

                    instanceMap.put(commandClass, instance);

                    Method thisMethod = cls.getDeclaredMethod("execute", params);
                    classLoader.close();
                    return thisMethod.invoke(instance, paramsObj);

                } else {
                    System.out.println("Compilation failed.");
                }

                fileManager.close();
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException
                    | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
