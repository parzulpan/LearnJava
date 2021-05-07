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
* 《Java 虚拟机规范》中的 `"<clinit>"` `"<init>"` `"invokespecial 指令"`

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

事务的传播行为：

* 使用：`@Transactional(propagation = Propagation.REQUIRED)`

* **REQUIRED** 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。**一般用于增删改操作**
* **SUPPORTS** 支持当前事务，如果当前没有事务，就以非事务方式执行。**一般用于查操作**
* MANDATORY 使用当前的事务，如果当前没有事务，就抛出异常
* REQUERS_NEW 新建事务，如果当前在事务中，把当前事务挂起
* NOT_SUPPORTED 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
* NEVER 以非事务方式运行，如果当前存在事务，抛出异常
* NESTED 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行 REQUIRED 类似的操作

事务的隔离级别：

* `TRANSACTION_READ_UNCOMMITTED` **读未提交**：允许事务读取未被其他事务提交的变更，**出现** 脏读、不可重复读、幻读等问题。
* `TRANSACTION_READ_COMMITTED` **读已提交**：只允许事务读取已经被其他事务提交的变更，**避免** 脏读问题，**出现** 不可重复读、幻读等问题。这是 Oracle 默认的事务隔离级别
* `TRANSACTION_REPEATABLE_READ` **可重复读**：确保在同一个事务中多次读取同样记录的结果是一致的，**避免** 脏读、不可重复读问题，**出现** 幻读等问题。这是 MySQL 默认的事务隔离级别。
* `TRANSACTION_SERIALIZABLE` **串行化**：确保事务可以从一个表中读取相同的行，在这个事务持续期间，禁止其他事务对该表进行增删改操作，**避免**上面所有问题。

总结：

* 事务的传播行为和隔离级别必须牢记于心。

推荐阅读：

* [【Spring】Spring 事务控制](https://www.cnblogs.com/parzulpan/p/14176323.html)
* [【JDBC核心】数据库事务](https://www.cnblogs.com/parzulpan/p/14129976.html)

## SpringMVC 中如何个解决 POST 请求中文乱码问题，GET 请求又如何处理

**对于 Post 请求（`form 标签 method=post`）**：解决的方法是在 `web.xml` 中配置一个编码过滤器

```xml
<web-app>

  <!-- 配置解决中文乱码的过滤器 -->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <!-- 设置过滤器中的属性值 -->
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <!-- 过滤所有请求 -->
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

</web-app>
```

**对于 Get 请求**：解决的方法是修改 Tomcat 的 `server.xml` 配置文件，添加 `<Connector URIEncoding="UTF-8"  useBodyEncodingForURI="true"/>`

推荐阅读：

* [请求参数乱码问题](https://www.cnblogs.com/parzulpan/p/14180698.html#%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0%E4%B9%B1%E7%A0%81%E9%97%AE%E9%A2%98)

## SpringMVC 的工作流程

![SpringMVC 的工作流程](https://images.cnblogs.com/cnblogs_com/parzulpan/1905354/o_201223131544SpringMVCServlet%E6%B5%81%E7%A8%8B.png)

总结：

* SpringMVC 是基于组件执行流程，弄清楚每个组件的作用，过一遍流程即可。

参考阅读：

* [SpringMVC 的工作流程](https://www.cnblogs.com/parzulpan/p/14180698.html#%E5%85%A5%E9%97%A8%E6%A1%88%E4%BE%8B%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90)

## MyBatis 中当实体类中的属性名和表中的字段名不一样时该如何处理

例如：

**属性名：实体类 id name password**

**字段名：数据库 id name pwd**

mybatis 会根据数据库的字段名去找对应的实体类的属性名，它会将所有列名转换为小写，然后去找实体类中对应的 set 方法 ，set 方法后面的字段就对应数据库的字段名；如果不一样就会返回 null。

基本上有三种方式解决：

* 修改 set 方法名字，不推荐使用
* 给 sql 语句取别名，字段少时推荐使用
* xml 配置文件设置结果集映射 ResultMap

## Linux 常用的服务类相关命令

**对于 CentOS6**：

* 注册在系统中的标准化程序
* 有方便统一的管理方式
  * `service 服务名 start`
  * `service 服务名 stop`
  * `service 服务名 restart`
  * `service 服务名 reload`
  * `service 服务名 status`
* 查看服务的方法 `/etc/init.d/服务名`
* 查看服务的命令 `chkconfig --list|grep xxx`
* 通过 `chkconfig` 命令设置自启动
  * `chkconfig --level 5 服务名 on/off`

**对于 CentOS7**：

* 注册在系统中的标准化程序
* 有方便统一的管理方式
  * `systemctl start 服务名(xxxx.service)`
  * `systemctl restart 服务名(xxxx.service)`
  * `systemctl stop 服务名(xxxx.service)`
  * `systemctl reload 服务名(xxxx.service)`
  * `systemctl status 服务名(xxxx.service)`
* 查看服务的方法 `/usr/lib/systemd/system`
* 查看服务的命令 `systemctl list-unit-files` `systemctl --type service`
* 通过 `systemctl` 命令设置自启动
  * `systemctl enable/disable 服务名`

## Git 常用的分支相关命令

* 查看分支：

  ```shell
  # 查看本地分支
  $ git branch
  # 查看远程分支
  $ git branch -r
  # 查看本地、服务器所有分支
  $ git branch -a
  # 显示本地分支和服务器分支的映射关系
  $ git branch -vv
  ```

* 创建分支

  ```shell
  # 创建本地分支，新分支创建后不会自动切换为当前分支
  $ git branch [branch name]
  ```

* 创建分支后切换到新分支

  ```shell
  # 建立分支后切换到新分支
  $ git checkout -b [branch name]
  ```

* 切换到指定分支

  ```shell
  # 切换到指定分支
  $ git checkout [branch name]
  ```

* 本地分支关联到远程分支

  ```shell
  # 本地分支建立，与远程分支同步之后，就可以直接使用 git pull 命令了
  $ git branch --set-upstream-to=origin/<远端branch_name> <本地branch_name>
  # 举例 将本地 dev 分支关联到远程 dev 分支
  $ git branch --set-upstream-to=origin/dev dev
  ```

* 合并分支

  ```shell
  # 合并分支,将名称为[name]的分支与当前分支合并
  $ git merge [name]
  ```

* 删除分支

  ```shell
  # 删除远程分支
  $ git push origin --delete [branch name]
  # 删除本地分支(-d 删除已经参与了合并的分支，对于未有合并的分支是无法删除的，如果想强制删除一个分支，可以使用 -D 选项） 
  $ git branch -d [branch name]
  ```

* 显示分支和提交

  ```shell
  # 显示分支和提交记录
  $ git show-branch
  ```

## Redis 持久化

RDB 和 AOF 的优缺点，以及使用建议。

总结：

推荐阅读：

* [【Redis3.0.x】持久化](https://www.cnblogs.com/parzulpan/p/14215141.html)

## MySQL 什么时候适合创建索引

总结：

推荐阅读：

* [【MySQL 高级】索引优化分析](https://www.cnblogs.com/parzulpan/p/14215392.html)

## JVM 的垃圾回收机制



## Redis 在项目中的常见使用场景



## MQ 在项目中的常见使用场景



推荐阅读：

* [MQ 的应用场景](https://www.cnblogs.com/parzulpan/p/14223189.html#%E5%BA%94%E7%94%A8%E5%9C%BA%E6%99%AF)

## ES 和 Solr 的异同

总结：

* 当单纯的对已有数据进行搜索时，Solr 更快
* 当实时建立索引时, Solr 会产生 io 阻塞，查询性能较差，Elasticsearch 具有明显的优势
* 随着数据量的增加，Solr 的搜索效率会变得更低，而 Elasticsearch 却没有明显的变化
* Solr 的架构不适合实时搜索的应用，Solr 利用 Zookeeper 进行分布式管理，而 Elasticsearch 自身带有分布式系统关联功能，Solr 本质就是一个 动态 web 项目（Tomcat）
* Solr 支持更多格式的数据，而 Elasticsearch 仅支持 json 文件格式
* Solr 在传统的搜索应用中表现好于 Elasticsearch，但在处理实时搜索应用时效率明显低于 Elasticsearch
* Solr 是传统搜索应用的有力解决方案，但 Elasticsearch 更适用于新兴的实时搜索应用

## 设计实现单点登录功能

单点登录：分布式系统中，一处登录多处使用。



## 设计实现购物车功能

至少需要考虑以下两个问题：

* 购物车和用户的关系？
  * 一个用户必须对应一个购物车
  * 单点登录一定在购物车功能之前
* 跟购物车相关的操作的有那些？
  * 添加购物车
    * 用户未登录状态：将数据保存到非关系型数据库或者 Cookie 中
    * 用户已登录状态：将数据保存到数据库（关系型和非关系型）中
  * 展示购物车
    * 用户未登录状态：直接从 Cookie 中取得数据展示
    * 用户已登录状态：从 数据库 和 Cookie 中取得数据展示

## 总结

