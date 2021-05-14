# JVM 体系结构和底层原理

## JVM 体系结构

我们知道，实际上 JVM 也是一种软件，它是运行在操作系统之上的，它与硬件没有直接交互。

![JVM与操作系统的关系](https://images.cnblogs.com/cnblogs_com/parzulpan/1899738/o_210514075247JVM%E4%B8%8E%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F%E7%9A%84%E5%85%B3%E7%B3%BB.png)

JVM 的整个体系结构为：

![JVM 体系结构](https://images.cnblogs.com/cnblogs_com/parzulpan/1899738/o_210514075254JVM%E4%BD%93%E7%B3%BB%E7%BB%93%E6%9E%84.png)

下面的内容将逐一阐述这些知识点。

## 类加载器 ClassLoader

类加载器 是负责加载 `class` 文件的，它将 `class` 文件字节码内容加载到内存中，并将这些内容转换成方法区中的运行时数据结构。

**类加载器**只负责文件的加载，至于它是否可运行，则是由**执行引擎**决定。

值得注意的是，`class` 和 `Class` 是有很大的区别的。`class` 是指编译 Java 代码后所生成的以 `.class` 为后缀名的字节码文件。 而 `Class` 是指由 JDK 提供的 `java.lang.Class` 类，可以理解为封装类的模板，它大多用于反射场景，例如 JDBC 中的加载驱动 `Class.forName("com.mysql.jdbc.Driver");`。

观察下图，`Car.class` 字节码文件被类加载器加载并初始化，在方法区中生成了一个 `Car Class` 的类模板。在 `Car Class` 类模板的基础上，形成实例。可以对某个具体的实例进行 `getClass()` 操作，就可以得到该实例的类模板，即 `Car Class`。可以对这个类模板进行 `getClassLoader()` 操作，就可以得到这个类模板是由哪个类装载器进行加载的，即 `sun.misc.Launcher$AppClassLoader@18b4aac2`。

![ClassLoader说明](https://images.cnblogs.com/cnblogs_com/parzulpan/1899738/o_210514095014ClassLoader%E8%AF%B4%E6%98%8E.png)

```java
package cn.parzulpan;

/**
 * @author parzulpan
 */

public class ClassLoaderDemo {
    public static void main(String[] args) {
        // 在 Car Class 类模板的基础上，形成实例
        Car car1 = new Car();
        Car car2 = new Car();
        // 对某个具体的实例进行 getClass() 操作，就可以得到该实例的类模板，即 Car Class
        System.out.println(car1.getClass());
        // 对这个类模板进行 getClassLoader() 操作，就可以得到这个类模板是由哪个类装载器进行加载的，即 sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(car1.getClass().getClassLoader());
    }
}

class Car {

}
```

需要注意的是，`JVM` 并不仅仅只是检查文件后缀名是否是 `.class` 来判断是否加载，最主要的是通过 `.class` 文件中特定的文件标识，即 `cafe babe`，如下图：

![字节码文件](https://images.cnblogs.com/cnblogs_com/parzulpan/1899738/o_210514114810%E5%AD%97%E8%8A%82%E7%A0%81%E6%96%87%E4%BB%B6.png)



### 类加载器的分类

虚拟机自带的加载器：

* 启动类加载器（BootstrapClassLoader），也叫根加载器。它是在 JVM 启动时创建的，用于加载 `%JAVA_HOME%/jre/lib` 下面所有的类库
* 拓展类加载器（ExtClassLoader）。它是 `sun.misc.Launcher` 的一个内部类，用于加载 `%JAVA_HOME%/jre/lib/ext` 下面所有的类库
* 应用程序类加载器（AppClassLoader），也叫应用程序类加载器。它是 `sun.misc.Launcher` 的一个内部类，用于加载 Java 环境变量 CLASSPATH 所指定的路径下的类库，该路径可以通过 `System.getProperty("java.class.path");` 获取。也可以使用参数 `java -cp class目录路径` 覆盖。

用户自定义的加载器（CustomClassLoader）：用户自定义类的加载方式，它必须是 `java.lang.ClassLoader` 的子类。

它们之前的关系为：`CustomClassLoader` -> `AppClassLoader` -> `ExtClassLoader` -> `BootstrapClassLoader`



### 双亲委派和沙箱安全

```java
package cn.parzulpan;

/**
 * @author parzulpan
 *
 * 类加载器的双亲委派和沙箱安全
 */

public class MyObject {
    public static void main(String[] args) {
        // 自定义 Object
        MyObject myObject = new MyObject();
        // sun.misc.Launcher 是 JVM 相关调用的入口程序
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(myObject.getClass().getClassLoader());
        // sun.misc.Launcher$ExtClassLoader@1b6d3586
        System.out.println(myObject.getClass().getClassLoader().getParent());
        // null
        System.out.println(myObject.getClass().getClassLoader().getParent().getParent());

        System.out.println();

        // JDK 的 Object
        Object o = new Object();
        // null
        System.out.println(o.getClass().getClassLoader());
        // java.lang.NullPointerException
        System.out.println(o.getClass().getClassLoader().getParent());
        // java.lang.NullPointerException
        System.out.println(o.getClass().getClassLoader().getParent().getParent());

    }
}
```

观察上面程序，对于 自定义 Object，`myObject.getClass().getClassLoader().getParent().getParent()` 的返回值是 `null`，这是因为 BootstrapClassLoader 是使用 C++ 编写的，Java 在加载它的时候就成了 `null`。

对于 JDK 的 Object，`o.getClass().getClassLoader();` 的返回值直接为 `null`。

出现这种情况的原因是因为类加载器的加载顺序和机制。

**双亲委派**：

* 当一个类收到了类加载请求，它首先不会尝试自己去加载这个类，而是把这个请求委派给父类去完成。

**沙箱安全机制**：

* 



## 本地方法栈 Native Method Stack





## 程序计数器 Program Counter Register





## 方法区 Method Area



## 栈 Stack

### 栈存储什么数据



### 栈的运行原理



### 栈溢出 StackOverflowError



## 栈、堆、方法区的交互关系



## 堆 Heap

### 堆体系结构



### 对象在堆中的生命周期



### MinorGC 的过程



### HotSpot 虚拟机的内存管理



### 永久区



### 堆参数调优



### 堆溢出 OutOfMemoryError



## GC

### GC 垃圾收集机制



### GC 日志信息详解



### GC 四大算法

#### Java 对象是否存活



#### 复制算法



#### 标记清除算法



#### 标记压缩算法



#### 分代收集算法







