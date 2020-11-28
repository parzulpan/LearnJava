package parzulpan.com.java;

import org.junit.Test;

import java.util.Random;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 通过反射 创建对应的运行时类的对象
 */

public class NewInstanceTest {

    // 创建运行时类的对象
    @Test
    public void test1() {
        Class<Person> clazz = Person.class;

        try {
            /* 调用了运行时类的空参构造器，需要满足两个条件
             1. 类必须有一个无参数的构造器
             2. 类的构造器的访问权限需要足够，通常设置为 public

             所以，在 JavaBean 中要求提供一个 public 的空参构造器，原因：
             1. 便于通过反射，创建运行时类的对象；
             2. 便于子类继承此运行时类的时候，默认调用 super() 保证父类有此构造器
             */
            Person p1 = clazz.newInstance();
            System.out.println(p1);

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 体会反射的动态性
    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            int num = new Random().nextInt(3);// 0 1 2
            String classPath;
            switch (num) {
                case 0:
                    classPath = "java.util.Date";
                    break;
                case 1:
                    classPath = "java.lang.Object";
                    break;
                case 2:
                    classPath = "parzulpan.com.java.Person";
                    break;
                default:
                    classPath = "";
            }

            try {
                Object instance = getInstance(classPath);
                System.out.println(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 创建一个指定类的对象
     * @param classPath 指定类的全类名
     * @return  指定类的对象
     */
    public Object getInstance(String classPath) throws Exception {
        Class<?> clazz = Class.forName(classPath);
        return clazz.newInstance();
    }

}
