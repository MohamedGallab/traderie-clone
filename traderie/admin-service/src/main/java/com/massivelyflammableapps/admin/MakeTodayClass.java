package com.massivelyflammableapps.admin;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class MakeTodayClass {
  String todayClass = "HelloWorld";
  // @Value("${service.classpath}")
  String classpath = "./traderie/admin-service/src/main/java/com/massivelyflammableapps/admin/";
  String todaySource = classpath + todayClass + ".java";

  public static void main(String args[]) {
    MakeTodayClass mtc = new MakeTodayClass();
    mtc.createIt();
    if (mtc.compileIt()) {
      System.out.println("Running " + mtc.todayClass + ":\n\n");
      mtc.runIt();
    } else {
      System.out.println(mtc.todaySource + " is bad.");
    }
  }

  public void createIt() {
    try (FileWriter aWriter = new FileWriter(todaySource, false)) {
      aWriter.write(
          "package com.massivelyflammableapps.admin; public class HelloWorld { public void doit() { System.out.println(\"Try programiz.pro\"); }}");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean compileIt() {
    String[] source = { todaySource };

    int result = com.sun.tools.javac.Main.compile(source);

    return (result == 0);
  }

  public void runIt() {
    try {
      // Convert the classpath to a URL
      URL classUrl = Paths.get(classpath).toUri().toURL();
      URL[] classUrls = { classUrl };
      
      // Create a new class loader with the directory
      URLClassLoader urlClassLoader = new URLClassLoader(classUrls);

      // Load the class
      Class<?> thisClass = urlClassLoader.loadClass("com.massivelyflammableapps.admin." + todayClass);
      Object iClass = thisClass.getDeclaredConstructor().newInstance();
      Method thisMethod = thisClass.getDeclaredMethod("doit");
      thisMethod.invoke(iClass);
      urlClassLoader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
