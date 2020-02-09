package szu.panda;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: Winnie
 * @Date: 2019-07-17
 * @Description:
 */
public class MyAnnotationProcessor {

    public static void main(String[] args) throws Exception {

        try {

            //加载annotationTest.class类
            Class clazz = MyAnnotationProcessor.class.getClassLoader().loadClass("szu.panda.MyAnnotationTest");

            //获取属性
            Field[] fields = clazz.getDeclaredFields();
            //遍历属性
            for (Field field : fields) {
                if (field.isAnnotationPresent(MyAnnotation.class)) {
                    MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
                    field.setAccessible(true);
                    field.set(String.class, myAnnotation.hobby());
                    field.setAccessible(false);
                }
            }

            //获取类中的方法
            Method[] methods = clazz.getMethods();
            //遍历方法
            for (Method method : methods) {

                //判断方法是否带有MyMessage注解
                if (method.isAnnotationPresent(MyAnnotation.class)) {
                    // 获取所有注解 method.getDeclaredAnnotations();
                    // 获取MyMessage注解
                    MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
//                    System.out.println("hobby:" + myAnnotation.hobby());
                }
                if (method.getName().equals("testName")) {
                    method.invoke(clazz);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}