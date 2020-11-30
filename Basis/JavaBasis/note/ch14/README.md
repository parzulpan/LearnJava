# Java8 新特性
  
## Lambda 表达式

Lambda 是一个匿名函数，我们可以把 Lambda 表达式理解为是一段可以传递的代码（将代码像数据一样进行传递）。使用它可以写出更简洁、更灵活的代码。

Lambda 表达式的本质是作为函数式接口的实例。

`->`， 该操作符被称为 Lambda 操作符或箭头操作符。它将 Lambda 分为两个部分：

* 左侧：指定了 Lambda 表达式需要的**参数列表**；
* 右侧：指定了 **Lambda 体**，是抽象方法的实现逻辑，也即 Lambda 表达式要执行的功能。

```java
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
```

总结六种语法格式情况：

* 左边：Lambda 形参列表的参数类型可以省略（类型推断）；如果形参列表只有一个参数，其一对 `()` 也可以省略；
* 右边：Lambda 体应该使用一对 `{}` 包裹；如果 Lambda 体只有一条执行语句（可能是 return 语句）那么可以省略这一对 `{}` 和 return 关键字；

## 函数式接口

在 Java8 中，**Lambda 表达式就是一个函数式接口的实例**。这就是
Lambda 表达式和函数式接口的关系。也就是说，**只要一个对象是函数式接口
的实例，那么该对象就可以用 Lambda 表达式来表示**。

所以以前用匿名实现类表示的现在都可以用 Lambda 表达式来写。

作为参数传递 Lambda 表达式：为了将 Lambda 表达式作为参数传递，接收 Lambda 表达式的参数类型必须是与该 Lambda 表达式兼容的函数式接口的类型。

Java 内置四大核心函数式接口：

* `Consumer<T>` 消费型接口。对类型为 T 的对象应用操作，包含方法：
`void accept(T t)`
* `Supplier<T>` 供给型接口。返回类型为T的对象，包含方法：`T get()`
* `Function<T, R>` 函数型接口。对类型为 T 的对象应用操作，并返回结果。结果是 R 类型的对象。包含方法：`R apply(T t)`
* `Predicate<T>` 断定型接口。确定类型为 T 的对象是否满足某约束，并返回 boolean 值。包含方法：`boolean test(T t)`

## 方法引用&构造器引用

对于方法引用，

当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用。

**方法引用可以看做是 Lambda 表达式深层次的表达。换句话说，方法引用就
是 Lambda 表达式，也就是函数式接口的一个实例，通过方法的名字来指向
一个方法，可以认为是 Lambda 表达式的一个**语法糖**。**

**要求**：实现接口的抽象方法的参数列表和返回值类型，必须与方法引用的
方法的参数列表和返回值类型保持一致。

**格式**：使用操作符 `::` 将类（或对象）与方法名分隔开来。

主要使用情况：

* 情况 1，对象::实例方法名
* 情况 2，类::静态方法名
* 情况 3，类::实例方法名

针对于情况 1 和 2，要求接口中的抽象方法的形参列表和返回值类型与方法引用的形参列表和返回值类型相同。

针对于情况 3，要求当函数式接口方法的第一个参数是需要引用方法的调用者，并且第二个参数是需要引用方法的参数（或无参数）时。`ClassName::methodName`

```java
BiPredicate<String, String> bp = (x, y) -> x.equals(y);

// 等同于
BiPredicate<String, String> bp1 = String::equals;
boolean flag = bp1.test("hello", "hi);
```

对于构造器引用，

与函数式接口相结合，自动与函数式接口中方法兼容。可以把构造器引用赋值给定义的方法，**要求**：构造器参数列表要与接口中抽象方法的参数列表一致！且方法的返回值即为构造器对应类的对象。**格式**：`ClassName::new`

```java
Function<Integer, MyClass> fun = (n) -> new MyClass(n);

// 等同于
Function<Integer, MyClass> fun1 = MyClass::new;
```

对于数组引用，**格式**：`type[] :: new`

```java
Function<Integer, Integer[]> fun = (n) -> new Integer[n];

// 等同于
Function<Integer, Integer[]> fun1 = Integer[]::new;
```

## StreamAPI

Stream API java.util.stream）把真正的函数式编程风格引入到 Java 中。使用 Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数据库查询。也可以使用 Stream API 来并行执行操作。

Stream 和 Collection 集合的**区别**：

* Collection 是一种静态的内存数据结构，而 Stream 是有关计算的。
* 前者是主要面向内存，存储在内存中，后者主要是面向 CPU，通过 CPU 实现计算。

Stream 通俗的讲，是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。

**注意**：

* Stream 自己不会存储元素。
* Stream 不会改变源对象。相反，他们会返回一个持有结果的新 Stream。
* Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。

Stream 的操作**三个步骤**：

* **创建 Stream**，一个数据源（如：集合、数组），获取一个流；
* **中间操作**，一个中间操作链，对数据源的数据进行处理；
* **终止操作**，一旦执行终止操作，就执行中间操作链，并产生结果。之后，不会再被使用；

```java
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
```

```java
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
```

```java
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
```

## Optional 类

空指针异常是导致 Java 应用程序失败的最常见原因。Google 著名的 Guava 项目引入了 Optional 类，Guava 通过使用检查空值的方式来防止代码污染，它鼓励程序员写更干净的代码。

`Optional<T>` 类是一个容器类，它可以保存类型 T 的值，代表这个值存在。或者仅仅保存 null，表示这个值不存在。原来用 null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且可以避免空指针异常。

如果值存在则 `isPresent()` 方法会返回 true，调用 `get()` 方法会返回该对象。

```java
package parzulpan.com.java1;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc :
 */

public class Boy {
    private Girl girl;

    public Boy() {
    }

    public Boy(Girl girl) {
        this.girl = girl;
    }

    public Girl getGirl() {
        return girl;
    }

    public void setGirl(Girl girl) {
        this.girl = girl;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "girl=" + girl +
                '}';
    }
}
```

```java
package parzulpan.com.java1;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc :
 */

public class Girl {
    private String name;

    public Girl() {
    }

    public Girl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

```java
package parzulpan.com.java1;

import org.junit.Test;

import java.util.Optional;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : Optional 类
 */

public class OptionalTest {

    // 创建Optional类对象的方法
    @Test
    public void test1() {
        // Optional.of(T t) 创建一个 Optional 实例，t必须非空；
        Girl girl = new Girl();
        Optional<Girl> girl1 = Optional.of(girl);
        System.out.println(girl1);

        // Optional.empty() 创建一个空的 Optional 实例
        Optional<Object> empty = Optional.empty();
        System.out.println(empty);

        // Optional.ofNullable(T t) t 可以为 null
        girl = null;
        Optional<Girl> girl2 = Optional.ofNullable(girl);
        System.out.println(girl2);

        // Optional 的应用
        Boy boy = null;
        String girlName1 = getGirlName(boy);
        System.out.println(girlName1);  // AAA

        boy = new Boy();
        String girlName2 = getGirlName(boy);
        System.out.println(girlName2);  // BBB

        boy = new Boy(new Girl("CCC"));
        String girlName3 = getGirlName(boy);
        System.out.println(girlName3);  // CCC
    }

    // 使用 Optional 优化
    public String getGirlName(Boy boy) {
        // 避免 Boy 和 Girl 为 null
        Optional<Boy> boyOptional = Optional.ofNullable(boy);
        // T orElse(T other) 如果有值则将其返回，否则返回指定的other对象
        Boy boy1 = boyOptional.orElse(new Boy(new Girl("AAA")));

        Girl girl = boy1.getGirl();

        Optional<Girl> girlOptional = Optional.ofNullable(girl);
        Girl girl1 = girlOptional.orElse(new Girl("BBB"));

        return girl1.getName();
    }
}
```

## 练习和总结
