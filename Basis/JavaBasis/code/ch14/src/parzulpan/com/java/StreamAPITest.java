package parzulpan.com.java;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : 创建 Stream
 */

public class StreamAPITest {

    // 创建 Stream方式一：通过集合
    @Test
    public void test1() {
        List<Employee> employees = EmployeeData.getEmployees();
        Stream<Employee> stream = employees.stream();
        Stream<Employee> employeeStream = employees.parallelStream();
    }

    // 创建 Stream方式二：通过数组
    @Test
    public void test2() {
        int[] ints = {1, 3, 43, 5, 463};
        IntStream stream = Arrays.stream(ints);

        Employee ppa = new Employee(1001, "PPA");
        Employee ooa = new Employee(1002, "OOA");
        Employee[] employees = {ppa, ooa};
        Stream<Employee> stream1 = Arrays.stream(employees);


    }

    // 创建 Stream方式三：通过Stream的of()
    @Test
    public void test3() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);

    }

    // 创建 Stream方式四：创建无限流
    @Test
    public void test4() {
        // 1. 迭代
        // 遍历前 10 个偶数
        Stream<Integer> iterate = Stream.iterate(0, x -> x + 2);
        iterate.limit(10).forEach(System.out::println);

        // 2. 生成
        Stream<Double> generate = Stream.generate(Math::random);
        generate.limit(10).forEach(System.out::println);
    }
}
