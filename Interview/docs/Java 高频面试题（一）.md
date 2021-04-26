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

```



推荐阅读：

* [【Java基础】面向对象下](https://www.cnblogs.com/parzulpan/p/14130192.html)
* 

## 方法的参数传递机制



## 递归和迭代



## 成员变量和局部变量



## Spring Bean 的作用域之间的区别



## Spring 支持的常用数据库事务传播属性和事务隔离级别



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

