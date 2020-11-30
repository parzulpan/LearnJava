# Java9 新特性

## 模块化系统

Java 和相关生态在不断丰富的同时也越来越暴露出一些问题：

* **Java 运行环境的膨胀和臃肿**。每次 JVM 启动的时候，至少会 30～60MB 的内存加载，主要原因是 JVM 需要加载 **`rt.jar`**，不管其中的类是否被类加载器加载，第一步整个 jar 都会被 JVM 加载到内存当中去，**而模块化可以根据模块的需要加载程序运行需要的 class**；
* **当代码库越来越大，创建复杂，盘根错节的“意大利面条式代码”的几率呈指数级的增长**。不同版本的类库交叉依赖导致让人头疼的问题，这些都阻碍了 Java 开发和运行效率的提升；
* 很难真正地对代码进行封装, 而系统并没有对不同部分（也就是 JAR 文件）之间的依赖关系有个明确的概念。**每一个公共类都可以被类路径之下任何其它的公共类所访问到，这样就会导致无意中使用了并不想被公开访问的 API**。

模块将由通常的类和新的模块声明文件（module-info.java）组成。该文件是位于 java 代码结构的顶层，该模块描述符明确地定义了我们的模块需要什么依赖关系，以及哪些模块被外部使用。在 exports 子句中未提及的所有包默认情况下将封装在模块中，不能在外部使用。

* **exports**：控制着哪些包可以被其它模块访问到。所有不被导出的包默认
都被封装在模块里面。
* **requires**：指明对其它模块的依赖。

```java
/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : Basis/JavaBasis/code/java11/src/module-info.java
 */

module java11 {
    exports com.parzulpan.bean;
}
```

```java
/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : Basis/JavaBasis/code/ch14/src/module-info.java
 */

module ch14 {
    requires java11;
    requires junit;
}
```

## jshell 命令

像 Python 和 Scala 之类的语言早就有交互式编程环境 **`REPL (read - evaluate - print - loop)`**，以交互式的方式对语句和表达式进行求值。开发者只需要输入一些代码，就可以在编译前获得对程序的反馈。

* jShell 也可以从文件中加载语句或者将语句保存到文件中。
* jShell 也可以是 tab 键进行自动补全和自动添加分号。

## 接口的私有方法

Java8 中规定接口中的方法除了抽象方法之外，还可以定义静态方法和默认的方法。一定程度上，扩展了接口的功能，此时的接口更像是一个**抽象类**。

在Java9 中，接口更加的灵活和强大，连方法的访问权限修饰符都可以声明为 **private** 的了，此时方法将不会成为你对外暴露的 API 的一部分。

```java
package parzulpan.com.java9;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : 自定义接口
 */

public interface MyInterface {

    void methodAbstract();

    // 只能自己调用
    static void methodStatic() {
        System.out.println("我是接口中的静态方法");
    }

    default void methodDefault() {
        System.out.println("我是接口中的默认方法");
        methodPrivate();
    }

    // 不能在接口外部调用
    private void methodPrivate() {
        System.out.println("我是接口中的私有方法");
    }
}
```

```java
package parzulpan.com.java9;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc :  Java9 接口的私有方法
 */

public class MyInterfaceImp implements MyInterface {
    @Override
    public void methodAbstract() {
        System.out.println("我实现接口中的方法");
    }

    @Override
    public void methodDefault() {
        System.out.println("我实现了接口中的默认方法");
    }

    public static void main(String[] args) {
        MyInterfaceImp myInterfaceImp = new MyInterfaceImp();
        myInterfaceImp.methodAbstract();
        myInterfaceImp.methodDefault();
    }
}
```

## 钻石操作符的的使用升级

在 Java8 中，与匿名实现类共同使用钻石操作符，会编译报错信息：`Cannot use “<>” with anonymous inner classes`.

```java
Comparator<Object> com = new Comparator<>(){
    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
};
```

而在 Java9 中，以上代码可以正常运行。

## try 语句的改进

在 Java8 中，可以实现资源的**自动关闭**，但是**要求执行后必须关闭的所有资源必须在 try 子句中初始化**，否则编译不通过。如下：

```java
try(InputStreamReader reader = new InputStreamReader(System.in)){
    //读取数据
}catch (IOException e){
    e.printStackTrace();
}
```

而在 Java9 中，**可以在 try 子句中使用已经初始化过的资源**，此时的资源是 **final** 的。如下：

```java
InputStreamReader reader = new InputStreamReader(System.in);
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        try (reader; writer){
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
```

## String 存储结构变更

String 再也不用 `char[]` 来存储，改成了 `byte[]` 加上**编码标记**，节约了一些空间。

更明确的说，与字符串相关的类（例如 AbstractStringBuilder，StringBuilder 和 StringBuffer）将更新为使用相同的表示形式，HotSpot VM 的固有字符串操作也将更新。

```java
public final class String implements Serializable, Comparable<String>, CharSequence {
    @Stable
    private final byte[] value;
}
```

## 集合工厂方法：快速创建只读集合

调用集合中静态方法 `of()`，可以将不同数量的参数传输到此工厂方法中。此功能可用于 Set 和 List，也可用于 Map 的类似形式。此时得到的集合，是不可变的，即在创建后，继续添加元素到这些集合会导致 “UnsupportedOperationException” 。

```java
package parzulpan.com.java9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : Java9 集合工厂方法：快速创建只读集合
 */

public class CollectionsTest {
    public static void main(String[] args) {
        // Java8
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add("Tom");
        arrayList.add("Jerry");
        arrayList.add("Hei");
        Collections.unmodifiableList(arrayList);
        System.out.println(arrayList);

        // Java9
        List<String> tom = List.of("Tom", "Jerry", "Hei");
        System.out.println(tom);
    }
}
```

## InputStream 加强

一个非常有用的方法：`transferTo()`，可以用来将数据直接传输到 OutputStream，这是在处理原始数据流时非常常见的一种用法，如下：

```java
ClassLoader cl = this.getClass().getClassLoader();
try (InputStream is = cl.getResourceAsStream("hello.txt");
        OutputStream os = new FileOutputStream("src\\hello1.txt")) {
    is.transferTo(os); // 把输入流中的所有数据直接自动地复制到输出流中
} catch (IOException e) {
    e.printStackTrace();
}
```

## 增强的 Stream API

在 Java 9 中，Stream 接口中添加了 4 个新的方法：`takeWhile`, `dropWhile`, `ofNullable`，还有个 `iterate` 方法的新重载方法，可以
让你提供一个 Predicate（判断条件）来指定什么时候结束迭代。

```java
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
```

## Javascript 引擎升级：Nashorn

Java9 包含一个用来解析 Nashorn 的 ECMAScript 语法树的 API。这个 API 使得 IDE 和服务端框架不需要依赖 Nashorn 项目的内部实现类，就能够分析 ECMAScript 代码。

JavaScript：

* ECMAScript；
* DOM；
* BOM；

## 练习和总结
