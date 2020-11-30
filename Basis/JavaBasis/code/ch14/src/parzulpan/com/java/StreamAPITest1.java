package parzulpan.com.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : Stream 的中间操作
 */

public class StreamAPITest1 {

    // 筛选和切片
    @Test
    public void test1() {
        List<Employee> employees = EmployeeData.getEmployees();

        // filter(Predicate p) 接收 Lambda，从流中排除某些元素
        Stream<Employee> stream = employees.stream();
        stream.filter(e -> e.getSalary() > 7000).forEach(System.out::println);  // 查询员工表中薪金大于 7000 的员工信息

        System.out.println();

        // limit(long maxSize) 截断流，使其元素不超过给定数量
        employees.stream().limit(3).forEach(System.out::println);

        System.out.println();

        // skip(long n) 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
        employees.stream().skip(3).forEach(System.out::println);

        System.out.println();

        // distinct() 筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
        employees.add(new Employee(1010, "刘强东", 40, 80000));
        employees.add(new Employee(1010, "刘强东", 40, 80000));
        employees.add(new Employee(1010, "刘强东", 40, 80000));
        employees.add(new Employee(1010, "刘强东", 40, 80000));
        employees.add(new Employee(1010, "刘强东", 40, 80000));
        employees.add(new Employee(1010, "刘强东", 40, 80000));
        employees.add(new Employee(1010, "刘强东", 40, 80000));
        employees.stream().distinct().forEach(System.out::println);
    }

    // 映射
    @Test
    public void test2() {
        // map(Function f) 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        List<String> strings = Arrays.asList("AA", "BB", "fE", "fkk", "Ae");
        strings.stream().map(String::toUpperCase).forEach(System.out::println);

        // 获取员工姓名长度大于 3 的员工姓名
        List<Employee> employees = EmployeeData.getEmployees();
        Stream<String> stringStream = employees.stream().map(Employee::getName);
        stringStream.filter(name -> name.length() > 3).forEach(System.out::println);

        // flatMap(Function f) 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
        // 注意这两个的区别，有点类似 add() 和 addAll()
        Stream<Stream<Character>> streamStream = strings.stream().map(StreamAPITest1::fromStringToStream);
        streamStream.forEach(s -> {
            s.forEach(System.out::println);
        });
        Stream<Character> characterStream = strings.stream().flatMap(StreamAPITest1::fromStringToStream);
        characterStream.forEach(System.out::println);
    }

    // 将字符串中的多个字符构成的集合转换为对应的 Stream 的实例
    public static Stream<Character> fromStringToStream(String str) {
        ArrayList<Character> arrayList = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            arrayList.add(c);
        }

        return arrayList.stream();
    }

    // 排序
    @Test
    public void test3() {
        // sorted() 产生一个新流，其中按自然顺序排序
        List<Integer> integers = Arrays.asList(11, 24, 453, 463, 1, 325, -134);
        integers.stream().sorted().forEach(System.out::println);

        // sorted(Comparator com) 产生一个新流，其中按比较器顺序排序
        EmployeeData.getEmployees().stream().sorted(
                (o1, o2) -> Integer.compare(o1.getAge(), o2.getAge())
        ).forEach(System.out::println);
    }
}
