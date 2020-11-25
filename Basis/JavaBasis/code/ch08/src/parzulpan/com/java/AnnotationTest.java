package parzulpan.com.java;

import java.lang.annotation.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-25
 * @Desc : 注解的使用
 */

@MyAnnotation()
public class AnnotationTest {
    public static void main(String[] args) {
        Class clazz = MyAnnotation.class;
        Annotation annotation = clazz.getAnnotation(MyAnnotation.class);
        MyAnnotation myAnnotation = (MyAnnotation) annotation;
        String info = myAnnotation.value();
        System.out.println(info);

    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MyAnnotation {
    String value() default "hello";
}