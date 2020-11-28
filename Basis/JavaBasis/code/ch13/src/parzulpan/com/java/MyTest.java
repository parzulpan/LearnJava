package parzulpan.com.java;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 获取运行时类的完整结构
 */

public class MyTest {

    // 通过反射获取运行时类的完整结构 - 全部的属性
    @Test
    public void test1() {
        Class<MyPerson> clazz = MyPerson.class;

        // public Field[] getFields()
        // 返回此 Class 对象所表示的类或接口的 public 的 Field，包括父类
        Field[] fields = clazz.getFields();

        for (Field f: fields) {
            System.out.println(f);
        }

        System.out.println();

        // public Field[] getDeclaredFields()
        // 返回此 Class 对象所表示的类或接口的 全部 Field，不包括父类
        Field[] declaredFields = clazz.getDeclaredFields();

        for(Field f : declaredFields) {
            System.out.println(f);

            // public int getModifiers() 以整数形式返回此 Field 的修饰符
            System.out.println(f.getModifiers());
            System.out.println(Modifier.toString(f.getModifiers()));    // 得到字符串形式的修饰符

            // public Class<?> getType() 得到 Field 的属性类型
            System.out.println(f.getType());

            //  public String getName() 返回 Field 的名称
            System.out.println(f.getName());
            System.out.println();
        }
    }

    // 通过反射获取运行时类的完整结构 - 全部的方法
    @Test
    public void test2() {
        Class<MyPerson> clazz = MyPerson.class;

        // public Method[] getMethods()
        // 返回此 Class 对象所表示的类或接口的 public 的方法，包括父类
        Method[] methods = clazz.getMethods();

        for (Method m : methods) {
            System.out.println(m);
        }

        System.out.println();

        // public Method[] getDeclaredMethods()
        // 返回此 Class 对象所表示的类或接口的全部方法，不包括父类
        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method m : declaredMethods) {
            System.out.println(m);

            // public Class<?> getReturnType() 取得全部的返回值
            System.out.println(m.getReturnType());

            // public Class<?>[] getParameterTypes() 取得全部的参数
            System.out.println(Arrays.toString(m.getParameterTypes()));

            // public int getModifiers() 取得修饰符
            System.out.println(m.getModifiers());
            System.out.println(Modifier.toString(m.getModifiers()));    // 得到字符串形式的修饰符

            // public Class<?>[] getExceptionTypes() 取得异常信息
            System.out.println(Arrays.toString(m.getExceptionTypes()));

            // public Annotation[] getAnnotations() 取得注解信息，需要是 RUNTIME 的
            System.out.println(Arrays.toString(m.getAnnotations()));
            System.out.println();
        }
    }

    @Test
    // 通过反射获取运行时类的完整结构 - 全部的构造器、所继承的父类、接口、类所在的包
    public void test3() {
        Class<MyPerson> clazz = MyPerson.class;

        // public Constructor<T>[] getConstructors()
        // 返回此 Class 对象所表示的类的所有 public 构造方法，不包括父类
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor c : constructors) {
            System.out.println(c);
        }

        System.out.println();

        // public Constructor<T>[] getDeclaredConstructors()
        // 返回此 Class 对象表示的类声明的所有构造方法，不包括父类
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor c : declaredConstructors) {
            System.out.println(c);
        }

        System.out.println();

        // public Class<? Super T> getSuperclass()
        // 返回表示此 Class 所表示的实体（类、接口、基本类型）的父类的 Class
        Class<? super MyPerson> superclass = clazz.getSuperclass();
        System.out.println(superclass);

        System.out.println();

        // public Type getGenericSuperclass()
        // 返回表示此 Class 所表示的实体（类、接口、基本类型）的带泛型父类的 Class
        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);

        System.out.println();

        // public Class<?>[] getInterfaces()
        // 确定此对象所表示的类或接口实现的接口
        Class<?>[] interfaces = clazz.getInterfaces();
        System.out.println(Arrays.toString(interfaces));

        System.out.println();

        // public Type[] getGenericInterfaces()
        // 确定此对象所表示的类或接口实现的泛型接口
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        System.out.println(Arrays.toString(genericInterfaces));

        System.out.println();

        // public Package getPackage()
        // 返回类所在的包
        Package aPackage = clazz.getPackage();
        System.out.println(aPackage);
    }
}
