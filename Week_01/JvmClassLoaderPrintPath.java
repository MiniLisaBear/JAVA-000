package com.swpu.jikejava.week01;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/*
 * 功能描述: 查看各个类加载器加载了哪些jar，以及把哪些路 径加到了classpath
 * @Author: th
 * @Date: 2020/10/18 20:37
 */
public class JvmClassLoaderPrintPath {
    public static void main(String[] args) {
        //启动类加载器
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url : urls) {
            System.out.println(" ==> " + url.toExternalForm());// 构造URL的字符串表示形式
        }
        //扩展类加载器
        //JvmClassLoaderPrintPath.class.getClassLoade
        printClassLoader("扩展类加载器", JvmClassLoaderPrintPath.class.getClassLoader().getParent());
        //应用类加载器
        //当前类所对应的类加载器属于应用类加载器
        printClassLoader("应用类加载器", JvmClassLoaderPrintPath.class.getClassLoader());
    }

    public static void printClassLoader(String name, ClassLoader classLoader) {
        if (classLoader != null) {
            System.out.println(name + " ClassLoader -> " + classLoader.toString());
            printURLForClassLoader(classLoader);
        } else {
            System.out.println(name + " ClassLoader -> null");
        }
    }

    public static void printURLForClassLoader(ClassLoader classLoader) {
        Object ucp = insightField(classLoader, "ucp");
        Object path = insightField(ucp, "path");
        ArrayList ps = (ArrayList) path;
        for (Object p : ps) {
            System.out.println(" ==> "+p.toString());
        }
    }

    public static Object insightField(Object object, String fName) {
        try {
            Field f = null;
            if (object instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fName);
            } else {
                f = object.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return f.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
