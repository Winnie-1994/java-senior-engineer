package szu.panda;

import java.lang.annotation.*;

/**
 * @Author: Winnie
 * @Date: 2019-07-17
 * @Description:
 */
@Target({ElementType.METHOD, ElementType.FIELD})  //指明注解支持的使用范围，方法和属性可用
@Retention(RetentionPolicy.RUNTIME)  //指明注解保留的的时间长短，运行时保留
@Inherited  //指明该注解类型被自动继承
@Documented //指明拥有这个注解的元素可以被javadoc此类的工具文档化
public @interface MyAnnotation {
    String name = "wuniting";
    Integer age = 18;
    String hobby() default "eating";
}