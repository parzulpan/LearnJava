package parzulpan.com.java;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : Stream 的终止操作
 */

public class StreamAPITest2 {

    // 匹配与查找
    @Test
    public void test1() {
        List<Employee> employees = EmployeeData.getEmployees();

        // allMatch(Predicate p) 检查是否匹配所有元素
        System.out.println(employees.stream().allMatch(e -> e.getAge() > 18));  // 是否所有的员工的年龄都大于 18

        // anyMatch(Predicate p) 检查是否至少匹配一个元素
        System.out.println(employees.stream().anyMatch(e -> e.getSalary() > 8000)); // 是否存在员工的工资大于 8000

        // noneMatch(Predicate p) 检查是否没有匹配所有元素
        System.out.println(employees.stream().noneMatch(e -> e.getName().startsWith("马"))); // 是否存在员工姓 "马"

        // findFirst() 返回第一个元素
        Optional<Employee> first = employees.stream().findFirst();
        System.out.println(first);

        // findAny() 返回当前流中的任意元素
        Optional<Employee> any = employees.parallelStream().findAny();
        System.out.println(any);

        // count() 返回流中元素总数
        long count = employees.stream().filter(e -> e.getSalary() > 8000).count(); // 员工的工资大于 8000 的人数
        System.out.println(count);

        //max(Comparator c) 返回流中最大值
        Optional<Double> max = employees.stream().map(Employee::getSalary).max(Double::compare);    // 员工的最高工资
        System.out.println(max);

        //min(Comparator c) 返回流中最小值

        Optional<Employee> min = employees.stream().min(
                (o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary())
        );  // 最低工资的员工
        System.out.println(min);

        //forEach(Consumer c) 内部迭代(使用 Collection 接口需要用户去做迭代，称为外部迭代。相反，Stream API 使用内部迭代
        employees.stream().forEach(System.out::println);    //  内部迭代
        employees.forEach(System.out::println); // 外部迭代
    }

    // 归约
    @Test
    public void test2() {

        // reduce(T identity, BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。返回 T
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = integers.stream().reduce(10, Integer::sum); // 计算 1-10 的和 + 10 的结果
        System.out.println(sum);

        //reduce(BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
        List<Employee> employees = EmployeeData.getEmployees();
        Optional<Double> reduce = employees.stream().map(Employee::getSalary).reduce(Double::sum);  // 计算员工工资总和
        System.out.println(reduce);

        // map 和 reduce 的连接通常称为 map-reduce 模式，因 Google 用它来进行网络搜索而出名。
    }

    // 收集
    @Test
    public void test3() {
        // collect(Collector c) 将流转换为其他形式。接收一个 Collector 接口的实现，用于给 Stream 中元素做汇总的方法
        // Collector 接口中方法的实现决定了如何对流执行收集的操作(如收集到 List、Set、Map)
        // Collectors 实用类提供了很多静态方法，可以方便地创建常见收集器实例
        // 查找员工的工资大于 8000 的员工，结果返回为一个 List 或 Set
        List<Employee> employees = EmployeeData.getEmployees();
        List<Employee> collect = employees.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toList());
        System.out.println(collect);
        collect.forEach(System.out::println);

        Set<Employee> collect1 = employees.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toSet());
        System.out.println(collect1);
        collect1.forEach(System.out::println);
    }
}
