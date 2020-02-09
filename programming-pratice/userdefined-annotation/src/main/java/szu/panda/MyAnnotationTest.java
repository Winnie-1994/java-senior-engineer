package szu.panda;

/**
 * @Author: Winnie
 * @Date: 2019-07-17
 * @Description:
 */

public class MyAnnotationTest {
    @MyAnnotation(hobby="I like eating")
    private static String name;

    @MyAnnotation(hobby="I like eating")
    public void test(){
        System.out.println("test");

    }

    public static void testName(){
        System.out.println(name);
    }
}