package szu.panda;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 指定class 进行加载e
 */
public class LoaderTest {
    public static void main(String[] args) throws Exception {
        URL classUrl = new URL("file:D:\\");//jvm 类放在位置

        URLClassLoader parentLoader = new URLClassLoader(new URL[]{classUrl});

        while (true) {
            // 创建一个新的类加载器
            URLClassLoader loader = new URLClassLoader(new URL[]{classUrl});
//            URLClassLoader loader = new URLClassLoader(new URL[]{classUrl}, parentLoader); //双亲委派，不会重新加载classloader

            // 问题：静态块触发
            Class clazz = loader.loadClass("HelloService");
            System.out.println("HelloService所使用的类加载器：" + clazz.getClassLoader());

            Object newInstance = clazz.newInstance(); //反射创建一个对象
            Object value = clazz.getMethod("test").invoke(newInstance);
            System.out.println("调用getValue获得的返回值为：" + value);

            Thread.sleep(3000L); // 1秒执行一次
            System.out.println();

            //  help gc  -verbose:class
            newInstance = null;
            loader = null;

        }

        // System.gc();
    }
}
