package parzulpan.com.java;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 反射初体验
 */

public class ReflectionTest {

    // 反射之前，对 Person 的操作
    @Test
    public void test1() {
        // 创建 Person 对象
        Person p1 = new Person("Tom", 22);

        // 调用属性
        p1.age = 10;
        System.out.println(p1.toString());

        // 调用方法
        p1.show();

        // 不能调用私有的构造器、属性、方法等
    }

    // 反射之后，对 Person 的操作
    @Test
    public void test2() {
        try {
            // 通过反射，创建 Person 对象
            Class<Person> clazz = Person.class;
            Constructor<Person> cons = clazz.getConstructor(String.class, int.class);
            Person p1 = cons.newInstance("Tom", 22);

            // 通过反射，调用属性
            Field age = clazz.getDeclaredField("age");
            age.set(p1, 10);
            System.out.println(p1.toString());

            // 通过反射，调用方法
            Method show = clazz.getDeclaredMethod("show");
            show.invoke(p1);

            System.out.println();

            // 通过反射，能调用私有的构造器、属性、方法等

            // 调用私有的构造器
            Constructor<Person> cons1 = clazz.getDeclaredConstructor(String.class);
            cons1.setAccessible(true);
            Person p2 = cons1.newInstance("Jerry");
            System.out.println(p2.toString());

            // 调用私有的属性
            Field name = clazz.getDeclaredField("name");
            name.setAccessible(true);
            name.set(p2, "JerryCat");
            System.out.println(p2);

            // 调用私有的方法
            Method showNation = clazz.getDeclaredMethod("showNation", String.class);
            showNation.setAccessible(true);
            Object nation = showNation.invoke(p2, "China");
            System.out.println(nation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取 Class 类的实例
    @Test
    public void test3() {
        // 方式一：通过运行时类的属性 .class
        Class<Person> clazz1 = Person.class;
        System.out.println(clazz1);

        // 方式二：通过运行时类的对象，调用 getClass()
        Person p1 = new Person();
        Class<? extends Person> clazz2 = p1.getClass();
        System.out.println(clazz2);

        Class<?> clazz3 = null;
        Class<?> clazz4 = null;
        try {
            // 方式三：通过 Class 的静态方法，forName(String classPath)，这个方式使用频率最高
            clazz3 = Class.forName("parzulpan.com.java.Person");
            System.out.println(clazz3);
            clazz4 = Class.forName("java.lang.String");
            System.out.println(clazz4);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 这三种方式获得的类的实例均相同
        System.out.println(clazz1 == clazz2);   // true
        System.out.println(clazz1 == clazz3);   // true

        // 方式四：使用类的加载器，ClassLoader
        try {
            ClassLoader clazzL = this.getClass().getClassLoader();
            Class<?> clazz5 = clazzL.loadClass("parzulpan.com.java.Person");
            System.out.println(clazz5);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 哪些类型可以有 Class 对象
    @Test
    public void test4() {
        Class<Object> c1 = Object.class;
        Class<Comparable> c2 = Comparable.class;
        Class<String[]> c3 = String[].class;
        Class<int[][]> c4 = int[][].class;
        Class<ElementType> c5 = ElementType.class;
        Class<Override> c6 = Override.class;
        Class<Integer> c7 = int.class;
        Class<Void> c8 = void.class;
        Class<Class> c9 = Class.class;

        int[] a = new int[10];
        int[] b = new int[100];
        Class<? extends int[]> c10 = a.getClass();
        Class<? extends int[]> c11 = b.getClass();
        // 只要元素类型与维度一样，就是同一个Class
        System.out.println(c10 == c11);     // true
    }
}
