package parzulpan.com.java;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 调用运行时类的指定结构
 */

public class ReflectionTest1 {

    // 调用运行时类的指定结构 - 指定属性
    @Test
    public void test1() throws Exception{
        // 获取类的实例
        Class<Person> clazz = Person.class;

        // 创建运行时类的对象
        Person person = clazz.newInstance();    // 调用空参构造器

        // public Field getField(String name)
        // 返回此 Class 对象表示的类或接口的指定的 public 的 Field
        // 通常不用，因为很少用 public 修饰属性
        Field age = clazz.getField("age");
        age.set(person, 99);
        Object o = age.get(person);
        System.out.println(o);

        System.out.println();

        // public Field getDeclaredField(String name)
        // 返回此 Class 对象表示的类或接口的指定的 Field
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);   // 禁用访问安全检查
        name.set(person, "Tom");
        Object o1 = name.get(person);
        System.out.println(o1);
    }

    // 调用运行时类的指定结构 - 指定方法
    @Test
    public void test2() throws Exception{
        // 获取类的实例
        Class<Person> clazz = Person.class;

        // 创建运行时类的对象
        Person person = clazz.newInstance();

        // 1. 通过 Class 类的 getMethod(String name, Class…parameterTypes) 方法
        // 取得一个 Method 对象，并设置此方法操作时所需要的参数类型
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        // 2. 使用后使用 Object invoke(Object obj, Object[] args)进行调用，
        // 并向方法中传递要设置的 obj 对象的参数信息
        showNation.setAccessible(true);   // 禁用访问安全检查
        Object china = showNation.invoke(person, "China");
        System.out.println(china);  // 返回值

        System.out.println();

        // 对于原方法若为静态方法，此时形参 Object obj 可为 null
        Method showNationStatic = clazz.getDeclaredMethod("showNationStatic");
        showNationStatic.setAccessible(true);
        Object invoke = showNationStatic.invoke(null);
        System.out.println(invoke);
    }

    // 调用运行时类的指定结构 - 指定构造器
    // 用的比较少，一般会直接调用空参构造器 clazz.newInstance();
    @Test
    public void test3() throws Exception{
        Class<Person> clazz = Person.class;

        // 参数指明构造器的参数列表
        Constructor<Person> declaredConstructor = clazz.getDeclaredConstructor(String.class);
        declaredConstructor.setAccessible(true);

        // 调用此构造器创建运行时类的对象
        Person tom = declaredConstructor.newInstance("Tom");
        System.out.println(tom);    // Person{name='Tom', age=0}

    }
}
