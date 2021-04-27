# Java 高频面试题（一）

## 自增变量

如下代码的运算结果：

```java
package java_one;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 自增变量
 * i = 1
 * i = 2 j = 1
 * i = 4 k = 11
 * i = 4 k = 1 k = 11
 */

public class IncreasingVariable {
    public static void main(String[] args) {
        int i = 1;
        i = i++;
        int j = i++;
        int k = i + ++i * i++;
        System.out.println("i = " + i);
        System.out.println("j = " + j);
        System.out.println("k = " + k);
    }
}
```

总结：

* 赋值 = ，最后计算
* = 右边的从左到右加载值依次压入操作数栈中
* 实际先算那个，看运算符优先级
* 自增、自减操作都是直接修改变量的值，不经过操作数栈
* 最后的赋值之前，临时结果也存储在操作栈中

## 单例设计模式

### 饿汉式1

实现如下：

```java
package java_one;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 饿汉式 - 直接实例化
 * 在类初始化时直接创建实例对象，不管是否需要这个对象都会创建
 * 1. 构造器私有化
 * 2. 自行创建，并用公有静态变量保存
 * 3. 向外提供这个实例
 */

public class Singleton1 {
    public static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() { }
}
```

测试代码：

```java
class Singleton1Test {
    public static void main(String[] args) {
        Singleton1 s1 = Singleton1.INSTANCE;
        Singleton1 s2 = Singleton1.INSTANCE;
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }
}
```

### 饿汉式2

实现如下：

```java
package java_one;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 饿汉式 - 枚举式
 * 枚举类型表示该类型的对象是有限的几个，可以限定为一个，就成了单例
 */

public enum Singleton2 {
    INSTANCE
}
```

测试代码：

```java
class Singleton2Test {
    public static void main(String[] args) {
        Singleton2 s1 = Singleton2.INSTANCE;
        Singleton2 s2 = Singleton2.INSTANCE;
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }
}
```

### 饿汉式3

实现如下：

```java
package java_one;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 饿汉式 - 静态代码块
 * 这种方式通常适用于复杂的实例化
 */

public class Singleton3 {
    public static final Singleton3 INSTANCE;
    static {
        // ...
        INSTANCE = new Singleton3();
    }
}
```

测试代码：

```java
class Singleton3Test {
    public static void main(String[] args) {
        Singleton3 s1 = Singleton3.INSTANCE;
        Singleton3 s2 = Singleton3.INSTANCE;
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }
}
```

### 懒汉式1

实现如下：

```java
package java_one;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 懒汉式 - 线程不安全，适用于单线程
 * 延迟创建实例对象
 * 1. 构造器私有化
 * 2. 用一个私有静态变量保存这个唯一的实例
 * 3. 提供一个静态方法，获取这个实例对象
 */

public class Singleton4 {
    private static Singleton4 instance;
    private Singleton4() { }
    public static Singleton4 getInstance() {
        if (instance == null) {
            // ...
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new Singleton4();
        }
        return instance;
    }
}

```

测试代码：

```java
class Singleton4Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Singleton4> callable = new Callable<Singleton4>() {

            @Override
            public Singleton4 call() throws Exception {
                return Singleton4.getInstance();
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Singleton4> future1 = executorService.submit(callable);
        Future<Singleton4> future2 = executorService.submit(callable);
        Singleton4 s1 = future1.get();
        Singleton4 s2 = future2.get();
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
        executorService.shutdown();
    }
}
```

### 懒汉式2

实现如下：

```java
package java_one;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 懒汉式 - 线程安全，适用于多线程
 */

public class Singleton5 {
    /** 为了避免初始化操作的指令重排序，给 instance 加上了 volatile */
    private static volatile Singleton5 instance;
    private Singleton5() { }
    public static Singleton5 getInstance() {
        if (instance == null) {
            synchronized(Singleton5.class) {
                if (instance == null) {
                    // ...
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new Singleton5();
                }
            }
        }
        return instance;
    }
}
```

测试代码：

```java
class Singleton5Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Singleton5> callable = Singleton5::getInstance;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Singleton5> future1 = executorService.submit(callable);
        Future<Singleton5> future2 = executorService.submit(callable);
        Singleton5 s1 = future1.get();
        Singleton5 s2 = future2.get();
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
        executorService.shutdown();
    }
}
```

### 懒汉式3

实现如下：

```java
package java_one;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 懒汉式 - 静态内部类，线程安全，适用于多线程
 * 当内部类被加载和初始化时，才会创建实例对象
 */

public class Singleton6 {
    private Singleton6() {}
    private static class Inner {
        private static final Singleton6 INSTANCE = new Singleton6();
    }
    public static Singleton6 getInstance() {
        return Inner.INSTANCE;
    }
}
```

测试代码：

```java
class Singleton6Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Singleton6> callable = Singleton6::getInstance;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Singleton6> future1 = executorService.submit(callable);
        Future<Singleton6> future2 = executorService.submit(callable);
        Singleton6 s1 = future1.get();
        Singleton6 s2 = future2.get();
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
        executorService.shutdown();
    }
}
```

总结：

* 如果是饿汉式，不需要考虑线程安全问题，枚举形式最简单
* 如果是懒汉式，需要考虑线程安全问题，静态内部类形式最简单

推荐阅读：

* [【设计模式】单例模式](https://www.cnblogs.com/parzulpan/p/13496610.html)
* [剑指offer 面试题2：实现Singleton模式](https://www.cnblogs.com/parzulpan/p/11255553.html)
* [采用延迟加载的线程安全单例](https://refactoringguru.cn/design-patterns/singleton/java/example#example-2)
* [Java面试官最爱问的volatile关键字](https://www.techug.com/post/java-volatile-keyword.html)
* [volatile 关键字的原理剖析与实例讲解](https://blog.csdn.net/u012723673/article/details/80682208)

## 类初始化和实例初始化

如下代码的运算结果：

```java
public class Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(1)");
    }
    Father() {
        System.out.print("(2)");
    }
    {
        System.out.print("(3)");
    }
    public int test() {
        System.out.print("(4)");
        return 1;
    }
    public static int method() {

        System.out.print("(5)");
        return 1;
    }
}

public class Son extends Father{
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(6)");
    }
    Son() {
        // super(); 写不写都存在，在子类构造器中一定会调用父类的构造器
        System.out.print("(7)");
    }
    {
        System.out.print("(8)");
    }
    @Override
    public int test() {
        System.out.print("(9)");
        return 1;
    }
    public static int method() {

        System.out.print("(10)");
        return 1;
    }
}

package java_one.three;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.three
 * @desc 3. 类初始化和实例初始化
 */

public class Test {
    public static void main(String[] args) {
        // 正确输出 5 1 10 6 9 3 2 9 8 7
        Son s1 = new Son();
        System.out.println();
        // 正确输出 9 3 2 9 8 7
        Son s2 = new Son();
    }
}
```

总结：

* 类初始化过程
  * 一个类要创建实例需要先加载并初始化该类
  * 一个子类要初始化需要先初始化父类
  * 一个类初始化就是执行 `<clinit>()` 方法
    *  `<clinit>()` 方法由**静态类变量显示赋值代码**和**静态代码块**组成
    * 静态类变量显示赋值代码和静态代码块从上到下顺序执行
    *  `<clinit>()` 方法**只执行一次**
* 实例初始化过程
  * 一个实例初始化就是执行 `<init>()` 方法
    *  `<init>()` 方法可能重载由多个，有几个构造器就有几个 `<init>()` 方法
    *  `<init>()` 方法由**非静态类变量显示赋值代码**和**非静态代码块**和**对应构造器代码**组成
    * 非静态类变量显示赋值代码和非静态代码块从上到下顺序执行，对应构造器代码最后执行
    *  `<init>()` 方法的首行是 super() 或 super(实参列表) ，即对应父类的  `<init>()` 方法。它写不写都存在，在子类构造器中一定会调用父类的构造器
* 方法的重写
  * 不可以被重写的方法：final 方法、静态方法、private 等子类不可见的方法
  * 对象的多态性：
    * 子类如果重写父类的方法，通过子类对象调用的一定是子类重写过的代码
    * 非静态方法默认的调用对象是 this
    * this 对象在构造器中（<init>()）就是正在创建的对象
* 由父到子，静态先行，考虑对象多态性。静态代码块 -> 非静态代码块 -> 构造函数

推荐阅读：

* [【Java基础】面向对象下](https://www.cnblogs.com/parzulpan/p/14130192.html)
* 《JVM 虚拟机规范》中的 "<clinit>" "<init>" "invokespecial 指令"

## 方法的参数传递机制

如下代码的运算结果：

```java
package java_one.four;

import java.util.Arrays;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.four
 * @desc 4. 方法的参数传递机制
 * i = 1
 * str = hello
 * num = 2
 * arr = [2, 2, 3, 4, 5]
 * myData.a = 11
 */

public class ParameterPassing {
    public static void main(String[] args) {
        int i = 1;
        String str = "hello";
        Integer num = 2;
        int[] arr = {1, 2, 3, 4, 5};
        MyData myData = new MyData();

        change(i, str, num, arr, myData);

        System.out.println("i = " + i);
        System.out.println("str = " + str);
        System.out.println("num = " + num);
        System.out.println("arr = " + Arrays.toString(arr));
        System.out.println("myData.a = " + myData.a);
    }

    public static void change(int j, String s, Integer n, int[] a, MyData m) {
        j += 1;
        s += "world";
        n += 1;
        a[0] += 1;
        m.a += 1;
    }
}

class MyData {
    int a = 10;
}
```

总结：

* 形参是基本数据类型时，传递的是数据值
* 实参是引用数据类型时，传递的是地址值
* 特殊的类型，例如 String、包装类等对象具有不可变性

推荐阅读：

* [【Java基础】面向对象上](https://www.cnblogs.com/parzulpan/p/14130119.html)

## 递归和迭代

有 n 步台阶，一次只能上 1 步或 2 步，共有多少种走法？

```java
package java_one.five;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.five
 * @desc 5. 递归和迭代
 */

public class Fibonacci {
    private final int CONSTANT = 1000000007;

    public int numWaysByRecursion(int n) {
        if (n < 3) {
            return n;
        }
        return (numWaysByRecursion(n - 1) % CONSTANT + numWaysByRecursion(n - 2) % CONSTANT) % CONSTANT;
    }

    public int numWaysByIteration(int n) {
        int first = 0, second = 1, sum;
        for (int i = 0; i < n; ++i) {
            sum = (first + second) % CONSTANT;
            first = second;
            second = sum;
        }
        return second;
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.numWaysByRecursion(0));
        System.out.println(fibonacci.numWaysByRecursion(1));
        System.out.println(fibonacci.numWaysByRecursion(2));
        System.out.println(fibonacci.numWaysByRecursion(10));
        System.out.println();
        System.out.println(fibonacci.numWaysByIteration(0));
        System.out.println(fibonacci.numWaysByIteration(1));
        System.out.println(fibonacci.numWaysByIteration(2));
        System.out.println(fibonacci.numWaysByIteration(10));
    }
}
```

总结：

* 方法调用自身称为递归
  * 优点：大问题转化为小问题，代码简洁，可读性好
  * 缺点：递归调用浪费了空间，递归太深容易造成堆栈的溢出
* 利用变量的原值推出新值称为迭代
  * 优点：代码运行效率高，因为时间只会因循环次数增加而增加，而且没有额外的空间开销
  * 缺点：代码不够简洁，可读性较差

推荐阅读：

* [【剑指 Offer】10-I.斐波那契数列](https://www.cnblogs.com/parzulpan/p/14221650.html)
* [【剑指 Offer】10-II.青蛙跳台阶问题](https://www.cnblogs.com/parzulpan/p/14221652.html)

## 成员变量和局部变量

如下代码的运算结果：

```java
package java_one.six;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.six
 * @desc 6. 成员变量和局部变量
 * 抓住：
 * 局部变量存在栈中，它每次调用都是新的生命周期
 * 实例变量存在堆中，它随着对象的创建而初始化，随着对象的回收而消亡，每一个对象的实例变量是独立的
 * 类变量存在方法区中，它随着类的初始化而初始化，随着类的卸载而消亡，该类的所有对象的类变量是共享的
 */

public class MembersLocalVariables {
    static int s;
    int i;
    int j;

    {
        int i = 1;
        i++;
        j++;
        s++;
    }

    public void test(int j) {
        j++;
        i++;
        s++;
    }

    public static void main(String[] args) {
        MembersLocalVariables m1 = new MembersLocalVariables();
        MembersLocalVariables m2 = new MembersLocalVariables();
        m1.test(10);
        m1.test(20);
        m2.test(30);
        // 2 1 5
        System.out.println(m1.i + " " + m1.j + " " + m1.s);
        // 1 1 5
        System.out.println(m2.i + " " + m2.j + " " + m2.s);
    }
}
```

总结：

* 局部变量与成员变量的区别

  | 区别项       | 局部变量                                                     | 成员变量                                                     |
  | ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | 声明位置     | 方法形参或内部、代码块类、构造器内等                         | 类中方法外，其中有 static 修饰的称为**类变量**，没有 static 修饰的称为**实例变量** |
  | 修饰符       | 不能用权限修饰符修饰，可以用 final 修饰                      | public、protected、private、final、static、volatile、transient |
  | 初始化值     | 没有默认初始化值，必须显式赋值，方可使用；特别的，形参在调用时赋值 | 有默认初始化值，同数组类似                                   |
  | 内存加载位置 | 栈中                                                         | 类变量在方法区中，实例变量在堆中                             |
  | 作用域       | 从声明处开始，到所属的大括号结束                             | 对于类变量：在当前类中`“类名.”`，在其他类中`“类名.”`或`“对象名.”` ；对于实例变量：在当前类中`“this.”`，在其他类中`“对象名.”` |
  | 生命周期     | 它每次调用都是新的生命周期                                   | 对于类变量：它随着类的初始化而初始化，随着类的卸载而消亡，该类的所有对象的类变量是共享的；对于实例变量：它随着对象的创建而初始化，随着对象的回收而消亡，每一个对象的实例变量是独立的 |

* 如何区分同名局部变量和成员变量

  * 局部变量与实例变量重名时，在实例变量前面加`“this.”`
  * 局部变量与类变量重名时，在类变量前面加`“类名.”`

推荐阅读：

* [【Java基础】面向对象上](https://www.cnblogs.com/parzulpan/p/14130119.html)

## Spring Bean 的作用域和生命周期

Spring Bean 有五个作用域，比较基础的有：

* singleton，这是默认作用域，它会为每个 IOC 容器创建一个唯一的 Bean 实例
* prototype，它会针对每个 getBean 请求，为 IOC 容器创建一个单独的 Bean 实例

如果是 Web 容器，还支持：

* request，在WEB 项目中，Spring 创建一个 Bean 的对象，将对象存入到 request 域中
* session，在WEB 项目中，Spring 创建一个 Bean 的对象，将对象存入到 session 域中
* global session，在WEB 项目中，应用在集群环境，如果没有集群环境那么它相当于 session

Spring Bean 的生命周期分为创建和销毁两个过程：

**创建**：

* 实例化 Bean 对象
* 设置 Bean 属性
* 如果通过接口声明了依赖关系，还会注入 Bean 对容器基础设施层面的依赖
* 调用 BeanPostProcessor 的前置初始化方法 postProcessBeforeInitialization
* 如果实现了 InitializationBean 接口，还会调用 afterPropertiesSet
* 调用 Bean 自身定义的 init 方法
* 调用 BeanPostProcessor 的后置初始化方法 postProcessAfterInitialization
* Bean 创建完毕

**销毁**：

* 调用 DisposableBean 的 destroy 方法
* 调用 Bean 自身定义的 destroy 方法

总结：

* Bean 的生命周期是完全被容器所管理的，从属性设置到各种依赖关系，都是容器负责注入，并进行各个阶段其他事宜的处理，Spring 容器为应用开发者定义了清晰的生命周期沟通界面。

推荐阅读：

* [【Spring】Spring 入门](https://www.cnblogs.com/parzulpan/p/14164990.html#ioc)

## Spring 支持的常用数据库事务传播属性(行为)和事务隔离级别



总结：

推荐阅读：

* [【Spring】Spring 事务控制](https://www.cnblogs.com/parzulpan/p/14176323.html)
* [【JDBC核心】数据库事务](https://www.cnblogs.com/parzulpan/p/14129976.html)



## SpringMVC 中如何个解决 POST 请求中文乱码问题，GET 请求又如何处理



## SpringMVC 的工作流程



## MyBatis 中当实体类中的属性名和表中的字段名不一样时该如何处理



## Linux 常用的服务类相关命令



## Git 常用的分支相关命令



## Redis 持久化



## MySQL 什么时候创建索引



## JVM 的垃圾回收机制



## Redis 在项目中的常见使用场景



## MQ 在项目中的常见使用场景



## ES 和 Solr 的异同



## 设计实现单点登录功能



## 设计实现购物车功能





## 总结

