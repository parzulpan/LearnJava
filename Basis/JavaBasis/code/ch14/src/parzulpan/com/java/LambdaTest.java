package parzulpan.com.java;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : Lambda 表达式
 */

public class LambdaTest {

    // Lambda 表达式 - 使用举例
    @Test
    public void test1() {
        // 匿名内部类
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        r1.run();

        // 使用 Lambda 表达式
        Runnable r2 = () -> System.out.println("hello Lambda");
        r2.run();

        // 使用匿名内部类作为参数传递
        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        int compare = com1.compare(12, 21);
        System.out.println(compare);

        // 使用 Lambda 表达式
        Comparator<Integer> com2 = (o1, o2) ->Integer.compare(o1, o2);
        int compare1 = com2.compare(12, 21);
        System.out.println(compare1);

        // 使用 方法引用
        Comparator<Integer> com3 = Integer::compare;
        int compare2 = com3.compare(12, 21);
        System.out.println(compare2);

    }

    // 语法格式
    @Test
    public void test2() {
        // 格式一：无参，无返回值
        Runnable r1 = () -> {
            System.out.println("格式一：无参，无返回值");
        };
        r1.run();

        // 格式二：Lambda 需要一个参数，但是没有返回值
        Consumer<String> con = (String str) -> {
            System.out.println(str);
        };
        con.accept("格式二：Lambda 需要一个参数，但是没有返回值");

        // 格式三：数据类型可以省略，因为可由编译器推断得出，称为"类型推断"
        Consumer<String> con1 = (str) -> {
            System.out.println(str);
        };
        con1.accept("格式三：数据类型可以省略，因为可由编译器推断得出，称为\"类型推断\"");

        // 格式四：Lambda 若只需要一个参数时，参数的小括号可以省略
        Consumer<String> con2 = str -> {
            System.out.println(str);
        };
        con1.accept("格式四：Lambda 若只需要一个参数时，参数的小括号可以省略");

        // 格式五：Lambda 需要两个或以上的参数，多条执行语句，并且可以有返回值
        Comparator<Integer> com = (x, y) -> {
            System.out.println("格式五：Lambda 需要两个或以上的参数，多条执行语句，并且可以有返回值");
            return Integer.compare(x, y);
        };
        int compare = com.compare(10, 20);
        System.out.println(compare);

        // 格式六：当 Lambda 体只有一条语句时，return 与大括号若有，都可以省略
        Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
        int compare1 = com1.compare(20, 10);
        System.out.println(compare1);

    }

}
