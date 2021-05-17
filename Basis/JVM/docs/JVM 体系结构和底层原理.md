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

值得注意的是，`class` 和 `Class` 是有很大的区别的。`class` 是指编译 Java 代码后所生成的以 `.class` 为后缀名的字节码文件。 而 `Class` 是指由 JDK 提供的 `java.lang.Class` 类，可以理解为封装类的模板，它大多用于反射场景，例如 JDBC 中的加载驱动 `Class.forName("com.mysql.jdbc.Driver");`。+

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

* 启动类加载器（BootstrapClassLoader），也叫根加载器。它是在 JVM 启动时创建的，用于加载 `%JAVA_HOME%/jre/lib/rt.jar` 所有的类库
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

对于 JDK 的 Object，`o.getClass().getClassLoader();` 的返回值直接为 `null`，说明它的加载器是 BootstrapClassLoader。

出现这种情况的原因是因为类加载器的加载顺序和机制。

**双亲委派**：

* 当一个类收到了类加载请求，它首先不会尝试自己去加载这个类，而是把这个请求委派给父类去完成。因此所有的加载请求都应该传送到启动类加载器中，只有当父类加载器反馈自己无法完成这个加载请求时（即在它的加载路径下没有找到所需加载的 Class），子类加载器才会尝试自己去加载。
* 这个做的一个**好处**是：比如加载位于 `rt.jar` 包中的类 `java.lang.Object`，不管是哪个加载器加载到这个类，最终都会委派给顶层的启动类加载器，能确保哪怕使用了不用的类加载器，最终得到的都是同样的一个 Object 对象。

**沙箱安全机制**：

* 它是基于双亲委派机制而采取的一种 JVM 的自我保护机制，假设要写一个 `java.lang.String` 的类，由于双亲委派，此请求会先交给启动类加载器，但是启动类加载器在加载类首先通过包和类名查找 `%JAVA_HOME%/jre/lib/rt.jar` 中有没有该类，有则优先加载它。

* 这就保证了 Java 的**运行机制不会被破坏**，确保你的代码不会污染到 Java 的源码。

  ```java
  package java.lang;
  
  public class String {
      // 错误: 在类 java.lang.String 中找不到 main 方法, 请将 main 方法定义为:
      //   public static void main(String[] args)
      // 否则 JavaFX 应用程序类必须扩展javafx.application.Application
      public static void main(String[] args) {
          System.out.println("xx");
      }
  }
  ```

  

![rt.jar](https://images.cnblogs.com/cnblogs_com/parzulpan/1899738/o_210515083956rt.jar.png)

观察 `rt.jar`，可以看到 `String`、`ArrayList`、甚至一些 `JDK` 提供的类和方法都在此包中定义好了，且直接被启动类加载器进行加载了。



## 本地方法栈 Native Method Stack

**本地方法接口**（Native Interface），其作用是融合不同的编程语言供 Java 使用，它的初衷是用来融合 C/C++ 程序的，Java 诞生的时候 C/C++ 非常流行，为了能调用 C/C++ 程序，Java 就在内存中专门开辟了一块区域处理标记为 **native** 的代码。

**本地方法栈**（Native Method Stack），就是在一个栈中登记这些 native 方法，然后在**执行引擎**（Execution Engine）执行时加载**本地方法库**（Native Libraries）。

```java
public synchronized void start() {
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. */
        group.add(this);

        boolean started = false;
        try {
            // 线程启动实际是调用了 start0()
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }

// 而 start0() 定义为 native 关键字修饰的一个方法
// 值得注意的是，它只有声明却没有具体的实现
// 实际上被 native 修饰的方法基本跟 Java 没什么关系了，它主要去调用底层操作系统或者第三方语言的库函数
private native void start0();
```



## 程序计数器 Program Counter Register

**程序计数器**（Program Counter Register），即 PC 寄存器。在每个线程启动的时候，都会创建一个 PC 寄存器。PC 寄存器里保存了当前正在执行的 JVM 指令的地址，每一个线程都有它自己的 PC 寄存器，在线程启动时创建，它是**线程私有的**。

PC 寄存器就是保存下一条将要执行的指令地址的寄存器，其内容总是指向下一条将被执行指令的地址，这里的地址可以是一个本地指针，也可以是在方法区中相对应于该方法起始指令的偏移量。PC 寄存器实际占用内存很小，几乎可以忽略，它一般用于完成分支、循环、跳转、异常处理、线程恢复等基础功能，它不会发生 OOM 错误。

值得注意的是，如果执行的是一个`native`方法，那这个计数器是空的。



## 方法区 Method Area

方法区（Method Area），存储了每一个**类的结构信息**（类模板），例如运行时常量池、属性和方法等字节码内容，它是**线程共享的**。

方法区是一种规范，在不同虚拟机中的实现可能不同，最典型的是 永久区（PermGen Space） 和 元空间（Mate Space）。

值得注意的是，**实例变量**存在堆内存中，和方法区无关。



## 栈 Stack

栈（Stack），即栈内存，它负责 Java 程序的运行，在线程启动时创建，其生命周期跟随着线程的生命周期，它是**线程私有的**。

**栈管运行，堆管存储**。对于栈来说，不存在垃圾回收的问题，只要线程结束则栈内存释放。

### 栈存储什么数据

栈主要存储**八种基本类型的变量**、**对象的引用变量**以及**实例方法**。

每个方法执行的同时都会创建一个**栈帧**，用于存储**局部变量表**、**操作数栈**、**动态链接**、**方法出口**等信息。每个方法从调用开始直到执行完毕的过程，就对应这个一个栈帧在虚拟机中入栈到出栈的过程。即栈帧是方法执行过程的一个**内存模型**。

栈帧主要保存的数据：

* **局部变量**：输入参数和输出参数，以及方法内的变量
* **栈操作**：记录出栈和入栈的操作
* **栈帧数据**：类文件、方法等

栈的实际大小跟 JVM 有关，一般在 1M 左右。

### 栈的运行原理

栈中的数据都是以栈帧（Stack Frame）的形式存在的，栈帧是一个内存区块，是一个数据集，是一个有关方法和运行时数据的数据集。当方法 A 被调用时就产生了一个栈帧 F1，并被压入栈中，如果方法 A 中又调用了方法 B，则产生栈帧 F2，也被压入栈中，.... 以此类推。遵循”先进先出，后进后出“的原则。

### 栈溢出 StackOverflowError

因为栈是内存块，是有实际大小限制的。如果一直进行入栈操作，而没有出栈操作，最后肯定会超过栈的大小，从而造成栈溢出的错误。



## 堆 Heap

理解栈、堆、方法区的交互关系：

```java
MyObject myObject = new MyObject();
```

等号左边 `MyObject myObject` 的 `myObject` 就是对象的引用变量，所以它在栈中。

等号右边的 `new MyObject()`，`new` 出来的 `MyObject` 就是实例对象，所以它在堆中。

而 `MyObject` 是对象类型数据，即类模板，所以它在方法区中。

简单的说，就是 **栈中** 的引用指向了 **堆中** 的实例对象，而实例对象是根据 **方法区中** 的类模板形成的。

### 堆体系结构

一个 JVM 实例只存在一个堆内存，其大小是可以调节的。当类加载器加载了类文件后，需要把类、方法等信息方法推内存中，保持其所有引用类型的真实信息，方便执行引擎执行。

堆内存主要分为三个部分：

* Young / New Generation Space 新生区 / 新生代
  * Eden Space 伊甸区
  * Survivor Space 幸存者区
    * Survivor 0 Space 幸存者 0 区 / from 区
    * Survivor 1 Space 幸存者 1 区 / to 区
* Old / Tenure Generation Space 老年区 / 老年代
* Permanent / Mate Space 永久区 / 元空间，Java 8 将永久区变成了元空间

### 对象在堆中的生命周期

对象在堆中的生命周期为：

* 首先，**新生区**是类的诞生、成长、消亡的区域。一个类在这里被创建并使用，最后被 GC（Garbage Collector） 回收结束生命。
* 其次，所有的类都是在新生区的 **Eden Space** 被 **new** 出来。当 **Eden Space** 的空间用完，但又需要创建对象时，JVM 的 GC 就会将 **Eden Space** 中不再被其他对象所引用的对象进行**垃圾回收（Minor GC）**。此时的 GC 称为**轻量级 GC**。
* 然后，**Eden Space** 中未被回收的对象，会被移动到 **Survivor 0 Space**，如果 **Survivor 0 Space** 的空间用完，JVM 的 GC 就会对其进行垃圾回收。 **Survivor 0 Space** 未被回收的对象，会被移动到 **Survivor 1 Space**，如果 **Survivor 1 Space** 的空间用完，会被移动到 **老年区**。
* 最后，如果  **老年区** 的空间用完，JVM 的 GC 会对其进行**垃圾回收（Major GC / Full GC）**。此时的 GC 称为**重量级 GC**。如果 **老年区** 被回收后还是处于空间用完的状态，则会产生 **OOM**。

### MinorGC 的过程

Survivor 0 Space 幸存者 0 区 / from 区，Survivor 1 Space 幸存者 1 区 / to 区。但是 from 区 和 to 区的区分不是固定的，时互相交换的，即每次 GC 之后，两者会进行交换，谁为空则谁是 to 区。

![MinorGC过程](https://images.cnblogs.com/cnblogs_com/parzulpan/1899738/o_210515095423MinorGC%E8%BF%87%E7%A8%8B.png)

MinorGC 的过程大致分为**复制**、**清空**、**互换**三个阶段：

* 首先，当 **Eden Space** 空间用完时，会触发第一次 GC，把”活着“的对象拷贝到 **from** 区。当  **Eden Space** 再次触发 GC 时，会扫描  **Eden Space**  和 **from** 区，把依然”活着“的对象直接拷贝到 to 区（年龄达到老年的标准，会移动到**老年区**），同时这些对象的年龄加一。
* 然后，清空  **Eden Space**  和 **from** 区，此时 **from** 区为空。
* 最后，**from** 区 和  **to** 区互换，部分对象会在这两个区中来回进行交换拷贝，如果交换次数达到 15 次（由 JVM 参数 `MaxTenuringThreshold` 决定，默认为 15），最终依旧存活的对象就会移动到 **老年区**。

总结：

* GC 之后有交换，谁为空则谁是 to 区。
* 这样是为了保证内存中没有碎片，所以 from 区 和 to 区 有一个要为空。

### HotSpot 虚拟机的内存管理

HotSpot 是 JVM 的名字：

```shell
> java -version
java version "11.0.11" 2021-04-20 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.11+9-LTS-194)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.11+9-LTS-194, mixed mode)
```

方法区和堆一样，是各个线程共享的内存区域，它用于存储虚拟机加载的：类信息、普通常量、静态常量、编译器编译后的代码等等。**虽然 JVM 规范将方法区描述为堆的一个逻辑部分，但它却还有一个别名叫做`Non-Heap`（非堆内存），目的就是要和堆区分开。**

对于 HotSpot JVM 而言，只是使用**永久代**来实现**方法区**而已，永久代是方法区（可以理解为一个接口`interface`）的一个实现。在 JDK1.7 中，将原本放在永久代的字符串常量池移走。字符串常量池，JDK1.6 在方法区，JDK1.7 在堆，JDK1.8 在元空间。

### 永久区

永久区是一个常驻内存区域，用于存放 JDK 自身携带的 Class、Interface 等元数据，即存储是运行环境必须的类信息。此区域的数据是不会被 GC 回收的，只有关闭 JVM 时才会释放此区域的数据。

JDK1.7 和 JDK1.8 关于永久区的区别：

JDK1.7 堆结构

![JDK1.7堆结构](https://images.cnblogs.com/cnblogs_com/parzulpan/1899738/o_210515101538JDK1.7%E5%A0%86%E7%BB%93%E6%9E%84.png)

JDK1.8 堆结构

![JDK1.8堆结构](https://images.cnblogs.com/cnblogs_com/parzulpan/1899738/o_210515101546JDK1.8%E5%A0%86%E7%BB%93%E6%9E%84.png)

可以看到，在 JDK1.8 中，永久代已被移除，被一个称为**元空间**的区域所取代。不过元空间的本质和永久代类似。

它们之间最大的区别在于： **永久代使用的 JVM 的堆内存，但是元空间并不在虚拟机中而是使用本机物理内存。**

因此，默认情况下，元空间的大小仅受本地内存限制。
类的元数据放入`native memory`，字符串池和类的静态变量放入 `Java 堆`，这样可以加载多少类的元数据就不再由`MaxPermSize`控制，而由系统的实际可用内存空间来控制。

### 堆参数调优

在进行堆参数调优前，可以通过下面的代码来获取虚拟机的相关内存信息：

```java
package cn.parzulpan;

/**
 * @author parzulpan
 *
 * 获取虚拟机的相关内存信息
 */

public class JVMMemory {
    public static void main(String[] args) {
        // 返回 Java 虚拟机试图使用的最大内存量
        long maxMemory = Runtime.getRuntime().maxMemory();
        // MAX_MEMORY = 3668967424（字节）、3499.0MB
        System.out.println("MAX_MEMORY = " + maxMemory + "（字节）、" + (maxMemory / (double) 1024 / 1024) + "MB");

        // 返回 Java 虚拟机中的内存总量
        long totalMemory = Runtime.getRuntime().totalMemory();
        // TOTAL_MEMORY = 247463936（字节）、236.0MB
        System.out.println("TOTAL_MEMORY = " + totalMemory + "（字节）、" + (totalMemory / (double) 1024 / 1024) + "MB");
    }
}
```

虚拟机最大的内存量，即 `-Xmx` 参数决定，它设置最大分配内存，默认为物理内存 1/4。

虚拟机的内存总量，即 `-Xms` 参数决定，它设置初始分配内存，默认为物理内存 1/64。

输出详细的 GC 处理日志，可以添加 `-XX:+PrintGCDetails` 参数。

**建议**：初始内存和最大内存设置为一样大，即 `-Xmx` 参数的值等于 `-Xms` 参数的值，理由是避免 GC 和应用程序争抢内存，进而导致内存忽高忽低产生停顿。

IDEA 中添加 JVM 参数：

```shell
-Xms1024m -Xmx1024m -XX:+PrintGCDetails
```

运行结果为：

```shell
MAX_MEMORY = 1029177344（字节）、981.5MB
TOTAL_MEMORY = 1029177344（字节）、981.5MB
Heap
 PSYoungGen      total 305664K, used 15729K [0x00000000eab00000, 0x0000000100000000, 0x0000000100000000)
  eden space 262144K, 6% used [0x00000000eab00000,0x00000000eba5c420,0x00000000fab00000)
  from space 43520K, 0% used [0x00000000fd580000,0x00000000fd580000,0x0000000100000000)
  to   space 43520K, 0% used [0x00000000fab00000,0x00000000fab00000,0x00000000fd580000)
 ParOldGen       total 699392K, used 0K [0x00000000c0000000, 0x00000000eab00000, 0x00000000eab00000)
  object space 699392K, 0% used [0x00000000c0000000,0x00000000c0000000,0x00000000eab00000)
 Metaspace       used 3272K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 352K, capacity 388K, committed 512K, reserved 1048576
```

可以发现，305664K / 1024 + 699392K / 1024 = 981.5MB，所以 元空间时不占 JVM 内存的。

### 堆溢出 OutOfMemoryError

演示一下 `OOM`，首先把堆内存调成 10M 后，再一直 new 对象，导致 FullGC 也无法处理，直至撑爆堆内存，进而导致`OOM` 堆溢出错误，程序及结果如下：

```java
// JVM 参数 -Xms10m -Xmx10m -XX:+PrintGCDetails
package cn.parzulpan;

import java.util.Random;

/**
 * @author parzulpan
 *
 * 堆溢出 OutOfMemoryError
 */

public class OOMTest {
    public static void main(String[] args) {
        String s = "OOM";
        while (true) {
            // 每执行下面语句，会在堆里创建新的对象
            s += s + new Random().nextInt(888888) + new Random().nextInt(999999);
        }
    }
}

// 输出
[GC (Allocation Failure) [PSYoungGen: 2036K->507K(2560K)] 2036K->775K(9728K), 0.0013745 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 2356K->512K(2560K)] 2624K->1050K(9728K), 0.0005619 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 2275K->512K(2560K)] 2814K->2036K(9728K), 0.0008101 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 2478K->512K(2560K)] 5910K->4452K(9728K), 0.0005135 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 2489K->512K(2560K)] 8338K->7346K(9728K), 0.0006025 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 512K->0K(2560K)] [ParOldGen: 6834K->3496K(7168K)] 7346K->3496K(9728K), [Metaspace: 3304K->3304K(1056768K)], 0.0043032 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] 3496K->3496K(8704K), 0.0003173 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] [ParOldGen: 3496K->3476K(7168K)] 3496K->3476K(8704K), [Metaspace: 3304K->3304K(1056768K)], 0.0041607 secs] [Times: user=0.00 sys=0.02, real=0.00 secs] 
Heap
 PSYoungGen      total 1536K, used 71K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
  eden space 1024K, 6% used [0x00000000ffd00000,0x00000000ffd11c30,0x00000000ffe00000)
  from space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 7168K, used 3476K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
  object space 7168K, 48% used [0x00000000ff600000,0x00000000ff9653e8,0x00000000ffd00000)
 Metaspace       used 3336K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 361K, capacity 388K, committed 512K, reserved 1048576K
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3332)
	at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:124)
	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:674)
	at java.lang.StringBuilder.append(StringBuilder.java:208)
	at cn.parzulpan.OOMTest.main(OOMTest.java:16)

```

可以看到，FullCG 也无法处理。

如果出现 `java.lang.OutOfMemoryError: Java heap space`，说明 JVM 的堆内存不够，造成堆内存溢出。原因有两点

* JVM 的堆内存设置太小，可以通过参数 `-Xms` 和 `-Xmx` 来调整。
* 代码中创建了大量对象，并且长时间不能被 GC 回收（存在被引用）。



## GC

### GC 垃圾收集机制

比较重要的结论：

* 频繁收集在 新生区
* 较少收集在 老年区
* 基本不动在 元空间

JVM 在进行 GC 时，并非时三个内存区域一起回收，大部分时候回收的都是新生区。GC 按回收区域分为两种类型：

* 轻量级 GC（MinorGC）：只针对于新生代区域的 GC，指发生在新生代的垃圾收集动作，因为大多数 Java 对象存活率都不高，因此 MinorGC 很频繁，一般回收速度也很快。
* 重量级 GC（MajorGC / FullGC）：指发生在老年代的垃圾收集动作，出现了 MajorGC，经常会伴随至少一次的MinorGC（但并不是绝对的）。MajorGC 的速度一般要比 MinorGC 慢上 10 倍以上。

### GC 日志信息详解

MinorGC 相关参数：

```shell
[GC (Allocation Failure) [PSYoungGen: 1973K->507K(2560K)] 1973K->783K(9728K), 0.0007314 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

* GC (Allocation Failure)  ：GC 类型
* PSYoungGen ：新生代
* 1973K：GC 前新生代内存占用
* 507K：GC 后新生代内存占用
* (2560K)：新生代总内存大小
* 1973K：GC 前 JVM 堆内存占用
* 783K：GC 后 JVM 堆内存占用
* (9728K)：JVM 堆总内存大小
* 0.0007314 secs：GC 耗时
* user=0.00：GC 用户耗时
* sys=0.00：GC 系统耗时
* real=0.00 secs：GC 实际耗时

FullGC 相关参数：

```shell
[Full GC (Ergonomics) [PSYoungGen: 512K->0K(2560K)] [ParOldGen: 6880K->3505K(7168K)] 7392K->3505K(9728K), [Metaspace: 3304K->3304K(1056768K)], 0.0044680 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
```

* Full GC (Ergonomics)：GC 类型
* PSYoungGen：新生代
* 512K：GC 前新生代内存占用
* 0K：GC 后新生代内存占用
* (2560K)：新生代总内存大小
* ParOldGen：老年代
* 6880K：GC 前老年代内存占用
* 3505K：GC 后老年代内存占用
* (7168K)：老年代总内存大小
* 7392K：GC 前 JVM 堆内存占用
* 3505K：GC 后 JVM 堆内存占用
* (9728K)：JVM 堆总内存大小
* Metaspace：元空间
* 其他同上



### GC 四大算法

#### 判断 Java 对象是否存活

引用计数法和根搜索法可以判断 Java 对象是否存活。

**引用计数法**是给每个对象设置一个计数器，当有地方引用这个对象的时候，计数器加一，当引用失效的时候，计数器减一。当计数器为零时，JVM 就认为该对象不再被使用。

优缺点：

* **优点**：实现简单，效率高
* **缺点**：每次对对象赋值时均要维护引用计数器，增加了额外开销；很难处理循环引用的问题

**根搜索法**是通过一些 `GCRoots` 对象作为起点，从这些节点开始往下搜索，搜索通过的路径成为引用链，当一个对象没有被 `GCRoots` 的引用链连接时，JVM 就认为该对象不再被使用。

 `GCRoots` 对象包括：

* 虚拟机栈（栈帧中的本地变量表）中引用的对象
* 方法区域中的类静态属性引用的对象
* 方法区域中的常量引用的对象
* 方法栈中 `Native 方法` 引用的对象

#### 复制算法

虚拟机把新生代分为了三部分：一个 Eden Space 和 两个 Survivor Space（分别叫 from、to 区），默认比例为 `8:1:1`。

一般情况下，新创建的对象都会被分配到 Eden Space ，这些对象经过第一次 MinorGC 后，如果依然存活，将会被移到 Survivor Space。对象在 Survivor Space 中每”熬“过一次 MinorGC，”年龄“就会增加一，当它的年龄增加到一定程度时（默认是 15 ，通过 `-XX:MaxTenuringThreshold` 来设定参数），就会被移动到老年代中。

因为新生代中的对象 GC 回收率很高，所以**新生代**的垃圾回收算法使用的**复制算法**。

复制算法的**基本思想**是将内存分为两块，每次只用其中一块（用 from 区），当一块内存用完，就将还活着的对象复制到另外一块上面。因为 Eden Space 区对象一般存活率较低，一般的，使用两块 10% 的内存作为空闲和活动区间，而另外 80% 的内存，则是用来给新建对象分配内存的。一旦发生 GC，将 10% 的 `from` 活动区间与另外 80% 中存活的  Eden Space 对象转移到 10% 的 `to` 空闲区间，接下来，将之前 90% 的内存全部释放，以此类推。

优缺点：

* **优点**：不会产生内存碎片，效率高

* **缺点**：耗费内存空间

如果对象的存活率很高，可以极端一点，假设是 100% 存活，那么我们需要将所有对象都复制一遍，并将所有引用地址重置一遍。复制这一工作所花费的时间，在对象存活率达到一定程度时，将会变的不可忽视。所以复制算法要想效率高，最起码对象的存活率要非常低才行，而且最重要的是，必须要克服 50% 内存的浪费。**总体来说，这种算法比较适合新生代。**

#### 标记清除算法

标记清除算法，主要分成**标记**和**清除**两个阶段，先标记出要回收的对象，然后统一回收这些对象。当程序运行期间，如果可以使用的内存被耗尽的时候，**GC 线程**就会被触发并将程序暂停，随后将要回收的对象标记一遍，最终统一回收这些对象，完成标记清理工作后接下来便让应用程序恢复运行。

两个阶段：

* **标记**：从引用根节点开始标记遍历所有的 `GCRoots` ，先标记处要回收的对象
* **清除**：遍历整个堆，把标记的对象清除

优缺点：

* **优点**：不需要额外的内存空间
* **缺点**：需要暂停整个应用，会产生内存碎片；两次扫描，耗时比较严重

简单来说，它的缺点就是**效率比较低**（递归与全堆对象遍历），而且在进行 GC 的时候，需要停止应用程序，这会导致用户体验非常差劲。由于**清理出来的空闲内存是不连续的**，为了应付这一点，JVM 就不得不维持一个内存的空闲列表，这又是一种**开销**。并且在分配数组对象的时候，需要去内存寻找连续的内存空间，但此时的内存空间太过零碎分散，因此资源耗费加大。**总体来说，这种算法比较适合老年代。**

#### 标记压缩算法

标记压缩算法，主要分成**标记**和**压缩（整理）**两个阶段，先标记出要回收的对象，然后再次扫描，并往一端滑动存活的对象，留出一块连续的空闲内存区域。总体来说，这种算法比较适合老年代。

优缺点：

* **优点**：不会产生内存碎片
* **缺点**：需要考虑移动对象的成本，效率也不高

#### 标记清除压缩算法

它是标记清除算法和标记压缩算法的结合。和标记清除算法一样，都是要进行多次 GC 后才进行压缩整理。

优缺点：

* **优点**：不会产生内存碎片，可以减少移动对象的成本
* **缺点**：耗费内存空间

#### 分代收集算法

现在主流 JVM 都是采用分代收集算法，它根据对象存活周期的不同将内存划分为几块，一般是把 Java 堆分为**新生代**和**老年代**，然后根据各个年代的特点采用最适当的垃圾收集算法。

在**新生代**中，每次垃圾收集都发现有大批对象死去，只有少量存活，就选用**复制算法**；而**老年代**中，因为对象存活率高，没有额外空间对它进行分配担保，就必须使用**标记清除**或者**标记压缩**算法来进行回收。



## 总结

**新生代和老年代的总结**：

对于**新生代**，特点是内存空间相对老年代较小，对象存活率低。**复制算法**的效率只和当前存活对象大小有关，因而很适用于新生代的回收。而复制算法的内存利用率不高的问题，可以通过 JVM 中的两个 `Survivor` 区设计得到缓解。

对于**老年代**，特点是内存空间较大，对象存活率高。复制算法明显变得不合适，一般是由标记清除或者是标记清除与标记整理的混合实现。具体阶段为：

* 标记阶段（Mark） ：此阶段的开销与存活对象的数量成正比。这点上说来，对于老年代，标记清除或者标记整理有一些不符，但可以通过多核/线程利用，以并发、并行的形式提高标记效率。
* 清除阶段（Sweep）：此阶段的开销与所管理内存空间大小成正相关。但由于 Sweep “就地处决”的特点，回收的过程没有对象的移动。使其相对其他有对象移动步骤的回收算法，仍然是效率最好的，但是需要解决内存碎片问题。
* 整理阶段（Compact）：此阶段的开销与存活对象的数量成正比。如上一条所描述，对于大量对象的移动是有很大开销的，做为老年代的第一选择并不合适。

---

**各个垃圾回收算法的优缺点**：

* **内存效率：** 复制算法 > 标记清除算法 > 标记整理算法（此处的效率只是简单的对比时间复杂度，实际情况不一定如此）。
* **内存整齐度：** 复制算法 = 标记整理算法 > 标记清除算法。
* **内存利用率：** 标记整理算法 = 标记清除算法 > 复制算法。

---

**MinorGC、MajorGC 和 FullGC 之间的区别和触发条件**：

**MinorGC** 用于回收新生代，当新生代无法为新生对象分配内存空间的时候，就会触发 MinorGC，因为新生代大多数对象的生命周期都很短，所以发生 MinorGC 的频率很高，虽然它会触发 `stop-the-world`，但是它的回收速度很快。

JVM 在进行 MinorGC 之前会判断**老年代最大的可用连续空间**是否大于**新生代的所有对象总空间**，如果

* 如果大于，则直接执行 MinorGC
* 如果小于，并且没有开启 **HandlerPromotionFailure**（相当于一种担保），则直接执行 FullGC
* 如果小于，并且开启 **HandlerPromotionFailure** ，JVM 判断 **老年代最大的可用连续空间**是否大于**历次晋级的平均值大小**，如果小于，则直接执行 FullGC



**MajorGC** 用于回收老年代，出现一次 MajorGC 通常至少会出现一次 MinorGC



**FullGC** 用于回收整个新生代、老年代和元空间，是全范围的 GC。FullGC 不等于 MajorGC，也不等于 MinorGC + MajorGC，发生 FullGC 需要看使用了什么垃圾收集器组合，才能解释是什么样的垃圾回收。

FullGC 如果

* 如果老年代空间不足，则直接执行 FullGC
* 如果 永久区/元空间 空间不足，则直接执行 FullGC
* 如果显式调用 System.gc，则会触发 FullGC，但不一定是立即触发执行
* 还有 MinorGC 时的两种情况也会触发 FullGC，详看上

---

