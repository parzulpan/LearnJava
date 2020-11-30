package parzulpan.com.java9;

import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : Java9 增强的 Stream API
 */

public class StreamTest {


    @Test
    public void test() {
        // takeWhile() 的使用
        // 用于从 Stream 中获取一部分数据，接收一个 Predicate 来进行选择。在有序的 Stream 中，takeWhile 返回从开头开始的尽量多的元素。
        List<Integer> integers = Arrays.asList(45, 43, 76, 87, 42, 77, 90, 73, 67, 88);
        integers.stream().takeWhile(x -> x < 50).forEach(System.out::println);
        System.out.println();
        List<Integer> integers1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        integers1.stream().takeWhile(x -> x < 5).forEach(System.out::println);

        System.out.println();

        // dropWhile() 的使用
        // dropWhile 的行为与 takeWhile 相反，返回剩余的元素。
        integers.stream().dropWhile(x -> x < 50).forEach(System.out::println);
        System.out.println();
        integers1.stream().dropWhile(x -> x < 5).forEach(System.out::println);

        System.out.println();

        // ofNullable() 的使用
        // 允许创建一个单元素 Stream，可以包含一个非空元素，也可以创建一个空 Stream。
        List<String> list = new ArrayList<>();
        list.add("AA");
        list.add(null);
        System.out.println(list.stream().count());  // 2
        Stream<Object> stream1 = Stream.ofNullable(null);
        System.out.println(stream1.count());    // 0
        Stream<String> stream = Stream.ofNullable("hello world");
        System.out.println(stream.count()); // 1

        System.out.println();

        // iterate() 重载的使用
        // 可以让你提供一个 Predicate (判断条件) 来指定什么时候结束迭代。
        // Java8
        Stream.iterate(1, i -> i + 1).limit(10).forEach(System.out::println);
        // Java9
        Stream.iterate(1, i -> i < 11, i -> i + 1).forEach(System.out::println);

        System.out.println();

        // Optional 类中 stream() 的使用
        List<String> strings = Arrays.asList("Tom", "Jerry", "Hei");
        Optional<List<String>> stringsOptional = Optional.of(strings);
        Stream<List<String>> stream2 = stringsOptional.stream();
        stream2.flatMap(Collection::stream).forEach(System.out::println);
    }
}
