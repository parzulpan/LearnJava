# Java 高频面试题（二）

## Java 基础



## JUC

### 谈谈对 volatile 关键字的理解？

#### 基本概念

volatile 是 JVM 提供的轻量级（不会引起线程上下文的切换和调度）同步机制，它**保证**可见性和禁止指令重排（保证有序性），**不保证**原子性。

值得一提的是，JMM（Java 内存模型，Java Memory Model）本身是一种抽象的概念，它并不真实存在，它描述的是一组规则或规范，通过这组规范定义了程序中各个变量（包括实例字段，静态字段和构成数组对象的元素）的访问方式。

JMM 关于同步的规定：

* 线程加锁前，必须读取主内存的最新值到自己的工作内存
* 线程解锁前，必须把共享变量的值刷新回主内存
* 加锁和解锁是同一把锁

![JMM关于同步的规定](https://images.cnblogs.com/cnblogs_com/parzulpan/1970814/o_210504062432JMM%E5%85%B3%E4%BA%8E%E5%90%8C%E6%AD%A5%E7%9A%84%E8%A7%84%E5%AE%9A.jpg)

#### 保证可见性说明

**可见性**指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。

当一个变量被 volatile 修饰后，表示着线程本地内存无效，当一个线程修改共享变量后它会立即被更新到主内存中，其他线程读取共享变量时，会直接从主内存中读取。当然 synchronize 和 Lock 都可以保证可见性，但是开销更大。

```java
package java_two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc volatile 保证可见性说明
 */

public class VolatileDemo1 {
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        MyData myData = new MyData();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " come in");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myData.addTo60();
                System.out.println(Thread.currentThread().getName() + " update number value is " + myData.number);
            }
        });

        while (myData.number == 0) { }

        System.out.println(Thread.currentThread().getName() + " end");
        executorService.shutdown();
    }

}

class MyData {
    /** 不用 volatile 修饰，main 线程一直卡住 */
    volatile int number = 0;
//    int number = 0;

    public void addTo60() {
        this.number = 60;
    }
}
```

#### 不保证原子性说明

**原子性**指一个操作或者多个操作要么全部执行并且执行的过程不会被任何因素打断，要么就都不执行。

Java 中的原子性操作包括：

* 基本类型的读取和赋值操作，且赋值必须是值赋给变量，变量之间的相互赋值不是原子性操作
* 所有引用 reference 的赋值操作
* `java.concurrent.Atomic.*` 包中所有类的一切操作

```java
package java_two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc volatile 不保证原子性说明
 */

public class VolatileDemo2 {
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        MyData2 myData2 = new MyData2();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    for (int i = 0; i < 1000; i++) {
                        myData2.addPlusPlus();
                    }
                }
            });
        }
        executorService.shutdown();
        // 等待上面的 10 个线程都计算完成
        // 默认是有两个线程的，一个 main 线程，一个 gc 线程
        while (Thread.activeCount() > 2) { }
        // 可以发现最后的结果总是小于 10000
        System.out.println(Thread.currentThread().getName() + " finally number value is " + myData2.number);
    }
}

class MyData2 {
    volatile int number = 0;
    public void addPlusPlus() {
        number ++;
    }
}
```

最后的结果总是小于 10000 的原因是因为 `number ++;` 在多线程下是非线程安全的。将代码编译成字节码，可以看出其被编译成 3 条指令：

```
getfield
iconst_1
iadd
putfield
```

解决这个不保证原子性的问题，有几种方法：

* 使用 synchronized 和 Lock，但性能较差
* 使用 Atomic 原子类

```java
class MyData2 {
    volatile int number = 0;
    public synchronized void addPlusPlus() {
        number ++;
    }
}

// 或者
class MyData2 {
    AtomicInteger number = new AtomicInteger();
    public void addPlusPlus() {
        number.getAndIncrement();
    }
}
```

#### 保证有序性说明

**有序性**指程序执行的顺序按照代码的先后顺序执行。

如果在本线程内观察，所有操作都是有序的；如果在一个线程中观察另一个线程，所有操作都是无序的。前半句是指“线程内表现为串行语义”，后半句是指“指令重排序”现象和“工作内存主主内存同步延迟”现象。

重排序是指编译器和处理器为了优化程序性能而对指令序列进行排序的一种手段。重排序需要遵守一定规则：

* 重排序操作不会对存在数据依赖关系的操作进行重排序
* 重排序是为了优化性能，但是不管怎么重排序，单线程下程序的执行结果不能被改变

但是在多线程环境下，可能发生重排序，影响结果。

```java
package java_two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc volatile 保证有序性说明
 */

public class VolatileDemo3 {
    int a = 0;
    boolean flag = false;

    public void method01(){
        //语句1
        a = 1;

        //语句2
        flag = true;
    }

    public void method02(){
        if(flag){
            //语句3
            a = a + 5;
        }
        
        // 多线程情况下，结果可能是6或1或5或0
        System.out.println("retValue: " + a);
    }
}
```

使用 volatile 关键字修饰共享变量便可以禁止这种重排序。若用 volatile 修饰共享变量，在编译时，会在指令序列中插入**内存屏障**来禁止特定类型的处理器重排序。**具体规则**为：执行到 volatile 变量时，其前面的所有语句都执行完，后面所有语句都未执行。且前面语句的结果对 volatile 变量及其后面语句可见。

#### volatile 原理分析

volatile 可以保证线程可见性并提供一定的有序性，但是无法保证原子性。这是因为 JVM 底层 volatile 是采用**内存屏障**来实现的。加了 volatile 关键字后，其汇编代码会多出一个 lock 前缀指令，lock 前缀指令实际上就相当于一个内存屏障，它提供三个功能：

* 它确保指令重排序时不会把其 <u>后面/前面</u> 的指令排到内存屏障 <u>之前/之后</u> 的位置，即在执行到内存屏障这句指令时，它前面的操作已经全部完成；
* 对 volatile 变量进行写操作时，会在写操作后加入一条 store 屏障指令，将工作内存中的共享变量值刷新回到主内存；
* 对 volatile 变量进行读操作时，会在读操作前加入一条 load 屏障指令，从主内存中读取共享变量到工作内存。

#### volatile 实际应用

1，状态量标记，```volatile boolean flag = false;``` 就保证了有序性。

2，单例模式的 DCL（Double Check Lock） 写法：

```java
package java_two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 单例模式双重共锁写法
 */

public class VolatileDCL {
    private static volatile VolatileDCL instance;

    public static VolatileDCL getInstance() {
        if (instance == null) {
            synchronized (VolatileDCL.class) {
                if (instance == null) {
                    // 3
                    instance = new VolatileDCL();
                }
            }
        }
        return instance;
    }
}
```

为什么要加 volatile ？

在多线程的情况下，`instance = new VolatileDCL();` 可以分解为

```java
memory = allocate() // 1. 分配内存
 
ctorInstanc(memory) // 2. 初始化对象
 
instance = memory // 3. 设置instance指向刚分配的地址
```

步骤 2 和步骤 3 不存在数据依赖关系，而且无论重排前还是重排后程序的执行结果在单线程中并没有改变，因此这种重排优化是允许的，这就可能造成有序性的问题。加了 volatile 就禁止了指令重排，解决了有序性的问题。



### 谈谈对 CAS(Compare And Swap) 的理解？

一个前导问题：原子类为什么能保证原子性？

回答：因为 CAS。

#### 基本概念

CAS 是指 Compare And Swap，比较并交换，是一种很重要的同步思想。如果主内存的值跟期望值一样，那就进行修改，否则一直重试，直到一致为止。**CAS 是一条 CPU 并发原语，不会造成所谓的数据不一致问题**。

```java
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        // true current value 999
        System.out.println(atomicInteger.compareAndSet(5, 999) + " current value " + atomicInteger.get());
        // false current value 999 修改失败
        System.out.println(atomicInteger.compareAndSet(5, 1024) + " current value " + atomicInteger.get());
    }
}
```

第一次修改，期望值为5，主内存也为5，修改成功，为999。第二次修改，期望值为5，主内存为999，修改失败。

#### CAS 底层原理

查看`AtomicInteger.getAndIncrement()`方法，发现其没有加`synchronized`**也实现了同步**。这是为什么？

`atomiclnteger.getAndIncrement()` 源码为：

```java
public class AtomicInteger extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 6214790243416807050L;

    // setup to use Unsafe.compareAndSwapInt for updates
    // Unsafe 是 CAS 核心类，由于 Java 方法无法直接访问底层系统，需要通过本地（native）方法来访问
    // Unsafe 相当于一个后门，基于该类可以直接操作特定内存的数据
    // Unsafe 类存在于 sun.misc 包中，其内部方法操作可以像 C 的指针一样直接操作内存
    // Unsafe 类中的方法都直接调用操作系统底层资源执行相应任务
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    
    // 表示该变量值在内存中的偏移地址，因为 Unsafe 就是根据内存偏移地址获取数据的
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }
    
	// 用 volatile 修饰，保证了多线程之间的内存可见性
    private volatile int value;
    
    /**
     * Creates a new AtomicInteger with the given initial value.
     *
     * @param initialValue the initial value
     */
    public AtomicInteger(int initialValue) {
        value = initialValue;
    }

    /**
     * Creates a new AtomicInteger with initial value {@code 0}.
     */
    public AtomicInteger() {
    }
            
    /**
     * Atomically increments by one the current value.
     *
     * @return the previous value
     */
    public final int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }
    
    // ...
}
    
```

`unsafe.getAndAddInt()` 源码为：

```java
// var1 指 AtomicInteger 对象本身
// var2 指 该对象值的引用地址
// var4 指 需要变动的数量，这里为 1
// var5 指 通过 var1 和 var2 找出的主内存中真实的值
// 用该对象当前的值与 var5 进行比较：
//   如果相同，更新值为 var5+var4 并返回 true
//   如果不同，继续取值然后再比较，直到更新完成
public final int getAndAddInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

        return var5;
    }
```

比如有 A、B 两个线程，一开始都从主内存中拷贝了原值为 3，A 线程执行到`var5=this.getIntVolatile`，即 `var5=3`。此时 A 线程挂起，B 修改原值为 4，B 线程执行完毕，由于加了 volatile，所以这个修改是立即可见的。A 线程被唤醒，执行`this.compareAndSwapInt()`方法，发现这个时候主内存的值不等于快照值 3，所以继续循环，**重新**从主内存获取。

#### CAS 缺点

CAS 实际上是一种自旋锁，有如下缺点：

* 一直循环，开销比较大
* 只能保证一个变量的原子操作，多个变量依然要加锁
* 引出了 ABA 问题



### 谈谈对原子类 AtomicInteger 的 ABA 问题的理解？原子引用知道吗？

#### ABA 问题

所谓 ABA 问题，就是比较并交换的循环过程中，存在一个时间差，而这个时间差可能带来意想不到的问题。

比如线程 T1 将值从 A 修改为 B，然后又从 B 修改为 A。线程 T2 看到的就是 A，但是却不知道这个 A 发生了改变。

尽管线程 T2 的 CAS 操作成功，但不代表就没有问题。 有的需求，比如CAS，**只注重头和尾**，只要首尾一致就接受。但是有的需求，还看重过程，中间不能发生任何修改，这就引出了`AtomicReference`原子引用。

#### 原子引用

`AtomicInteger` s是对整数进行原子操作，如果是一个 实体类 呢？可以用`AtomicReference`来包装这个实体类，使其操作原子化。

```java
package java_two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 原子引用
 */

public class AtomicReferenceDemo {
    public static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        User aa = new User("AA", 23);
        User bb = new User("BB", 24);
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(aa);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // true
                System.out.println(atomicReference.compareAndSet(aa, bb));
                // true
                System.out.println(atomicReference.compareAndSet(bb, aa));
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // 保证完成一次 ABA
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // true，但是业务要求它返回 false
                System.out.println(atomicReference.compareAndSet(aa, bb));
            }
        });

        executorService.shutdown();
    }
}

class User {
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


```

#### 时间戳原子引用

使用`AtomicStampedReference`类可以解决 ABA 问题。这个类维护了一个“**版本号**”Stamp，在进行 CAS 操作的时候，不仅要比较当前值，还要比较**版本号**。只有两者都相等，才执行更新操作。

```java
package java_two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 时间戳原子引用，解决 ABA 问题
 */

public class AtomicStampedReferenceDemo {
    public static ExecutorService executorService = Executors.newFixedThreadPool(2);
    public static void main(String[] args) {
        User cc = new User("CC", 23);
        User dd = new User("DD", 24);
        User ee = new User("EE", 25);
        AtomicStampedReference<User> atomicStampedReference = new AtomicStampedReference<>(cc, 1);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // true
                System.out.println(atomicStampedReference.compareAndSet(cc,
                        dd,
                        atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp() + 1));
                // true
                System.out.println(atomicStampedReference.compareAndSet(dd,
                        cc,
                        atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp() + 1));
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedReference.getStamp();
                // 保证完成一次 ABA
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // false
                System.out.println(atomicStampedReference.compareAndSet(cc,
                        ee,
                        stamp,
                        stamp + 1));
            }
        });

        executorService.shutdown();

    }
}
```



### ArrayList 是线程安全的吗？请编写一个线程不安全的 demo 并给出解决方案？

 ArrayList 不是线程安全的，在多线程同时写的情况下，会抛出并发修改异常 `java.util.ConcurrentModificationException`。

**故障现象**：

* 抛出 java.util.ConcurrentModificationException

**导致原因**：

* 并发争抢修改的资源

**解决方案**：

* 使用`Vector`（`ArrayList`所有方法加`synchronized`，太重）

* 使用`Collections.synchronizedList()`转换成线程安全类

* 使用`java.util.concurrent.CopyOnWriteArrayList`（推荐使用）

  * 这是 JUC 的类，它通过**写时复制**来实现**读写分离**

  * 比如其`add()`方法，就是先**复制**一个新数组，长度为原数组长度+1，然后将新数组最后一个元素设为添加的元素，源码为：

    ```java
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            //得到旧数组
            Object[] elements = getArray();
            int len = elements.length;
            //复制新数组
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            //设置新元素
            newElements[len] = e;
            //设置新数组
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
    ```

**优化建议**：

* 使用 JUC 相关类

总结：

* Collection 接口：单列集合，用来存储一个一个的对象
  * List 接口：存储有序的、可重复的对象
    * **ArrayList**：作为 List 接口的主要实现类；底层使用 `Object[] elementData` 存储，适用于频繁随机访问操作；`add(E e)` 默认情况下，扩容为原来的容量的 1.5 倍；**线程不安全的**，效率高；
    * **LinkedList**： 底层使用双向链表存储，适用于频繁插入、删除操作；**线程不安全的**，效率高；
    * **Vector**：作为 List 接口的古老实现类；底层使用 `Object[] elementData` 存储，适用于频繁随机访问操作；`add(E e)` 默认情况下，扩容为原来的容量的 2 倍；**线程安全的**，效率低；
  * Set 接口：存储无序的、不可重复的对象
    * **HashSet**：作为 Set 接口的主要实现类；底层使用 HashMap 存储（`HashMap.put()`需要传**两个参数**，而`HashSet.add()`只**传一个参数**的原因为：实际上`HashSet.add()`就是调用的`HashMap.put()`，只不过 **value** 被写死了，是一个`private static final Object`对象）；**线程不安全的**，效率高；
    * **LinkedHashSet**：作为 HashSet 的子类，遍历其内部数据时，可以按照添加的顺序遍历；**线程不安全的**，效率高；
    * **TreeSet**：作为 SortedSet 接口的实现类，底层使用红黑树存储，可以按照添加对象的指定属性进行排序；**线程不安全的**，效率高；
* Map 接口：双列集合，用来存储一对一对的对象
  * **HashMap**：作为 Map 接口的主要实现类；可以存储 `null` 的 key 和 value；底层使用 数组+链表+红黑树（JDK8，JDK7无红黑树）存储；**线程不安全的**，效率高；
  * **LinkedHashMap**：作为 HashMap 的子类，遍历其内部数据时，可以按照添加的顺序遍历；**线程不安全的**，效率高；
  * **TreeMap**：底层使用红黑树存储，可以按照添加对象的指定属性进行排序；**线程不安全的**，效率高；
  * **Hashtable**：作为 Map 接口的古老实现类；不可以存储 `null` 的 key 和 value；**线程安全的**，效率低；
  * **Properties**：作为 Hashtable 的子类；常用来处理配置文件，key 和 value 都是 String 类型；**线程安全的**，效率低；

```java
package java_two;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 集合线程安全性说明
 */

public class ContainerNotSafeDemo {
    public static void main(String[] args) {
//        arrayListNotSafe();
//        LinkedListNotSafe();
//        VectorSafe();
//        hashSetNotSafe();
//        linkedHashSetNotSafe();
//        treeSetNotSafe();
//        hashMapNotSafe();
//        linkedHashMapNotSafe();
//        treeMapSafe();
        hashTableNotSafe();
        
    }

    /**
     * ArrayList 线程不安全，使用 CopyOnWriteArrayList 解决线程安全问题
     */
    public static void arrayListNotSafe() {
//        List<String> list = new ArrayList<>();
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * LinkedList 线程不安全，使用 CopyOnWriteArrayList 解决线程安全问题
     */
    public static void LinkedListNotSafe() {
//        List<String> list = new LinkedList<>();
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * Vector 线程安全
     */
    public static void VectorSafe() {
        List<String> list = new Vector<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * HashSet 线程不安全，使用 CopyOnWriteArraySet 解决线程安全问题
     * CopyOnWriteArraySet 底层维护了一个CopyOnWriteArrayList数组。
     */
    public static void hashSetNotSafe() {
//        Set<String> set = new HashSet<>();
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * LinkedHashSet 线程不安全，使用 CopyOnWriteArraySet 解决线程安全问题
     */
    public static void linkedHashSetNotSafe() {
//        Set<String> set = new LinkedHashSet<>();
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * TreeSet 线程不安全，使用 CopyOnWriteArraySet 解决线程安全问题
     */
    public static void treeSetNotSafe() {
//        Set<String> set = new TreeSet<>();
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * HashMap 线程不安全，使用 ConcurrentHashMap 解决线程安全问题
     */
    public static void hashMapNotSafe() {
//        Map<String, String> map = new HashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * LinkedHashMap 线程不安全，使用 ConcurrentHashMap 解决线程安全问题
     */
    public static void linkedHashMapNotSafe() {
//        Map<String, String> map = new LinkedHashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * TreeMap 线程不安全，使用 ConcurrentHashMap 解决线程安全问题
     */
    public static void treeMapSafe() {
//        Map<String, String> map = new TreeMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * Hashtable 线程安全
     */
    public static void hashTableNotSafe() {
        Map<String, String> map = new Hashtable<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

}
```

推荐阅读：

* [【Java基础】集合](https://www.cnblogs.com/parzulpan/p/14131679.html)



### 谈谈对 ConcurrentHashMap 的理解？





### 谈谈对公平锁、非公平锁、可重入锁/递归锁、自旋锁的理解？请编写一个自旋锁 demo？

#### 公平锁和非公平锁

**概念**：

* 所谓**公平锁**，即多个线程按照申请锁的顺序来获取锁，类似排队，先到先得。

* 所谓**非公平锁**，即多个线程抢夺锁，它会导致优先级反转和饥饿现象。

**区别**：

* 公平锁在获取锁时会先查看此锁维护的**等待队列**，**为空**或者当前线程时等待队列的**队首**，则直接占有锁，否则插入到等待队列，按照先进先出的原则。
* 非公平锁会直接先尝试占有锁，失败则采用公平锁方式。它的优点是**吞吐量**比公平锁更大。

`synchronized` 和 `juc.ReentrantLock` 默认都是非公平锁，ReentrantLock 在构造的时候传入 `true` 则是公平锁。

#### 可重入锁/递归锁

**可重入锁**又称为**递归锁**，即同一个线程在**外层方法**获得锁时，进入**内层方法**会自动获取锁。也就是说，线程可以进入任何一个它已经拥有锁的代码块。比如有了家门口的锁，像卧室、书房、厨房等就可以自由进出了。

可重入锁可以**避免死锁**的问题。

`synchronized` 和 `juc.ReentrantLock` 是比较典型的可重入锁。

```java
package java_two;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 可重入锁
 */

public class ReentrantLockDemo {
    public static void main(String[] args) {
//        runInfo();
        runInfo2();
    }

    public static void runInfo() {
        Info info = new Info();
        new Thread(() -> info.getInfo(), "t1").start();
        new Thread(() -> info.getInfo(), "t2").start();
    }

    public static void runInfo2() {
        Info2 info2 = new Info2();
        new Thread(info2, "t3").start();
        new Thread(info2, "t4").start();
    }
}

class Info {
    public synchronized void getInfo() {
        System.out.println(Thread.currentThread().getName() + " invoked getInfo()");
        getInfoName();
    }

    public synchronized void getInfoName() {
        System.out.println(Thread.currentThread().getName() + " invoked getInfoName()");
    }
}

class Info2 implements Runnable {
    Lock lock = new ReentrantLock();

    public void getInfo() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoked getInfo()");
            getInfoName();
        } finally {
            lock.unlock();
        }
    }

    public void getInfoName() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoked getInfoName()");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        getInfo();
    }
}
```

值得注意的是，锁与锁之间要讲究配对，加了几把锁，最后就得解开几把锁。下面的代码编译和运行都没有任何问题，但锁的数量不匹配会导致死循环。

```java
lock.lock();
lock.lock();
try{
    someAction();
}finally{
    lock.unlock();
}
```

#### 自旋锁

所谓自旋锁，即在尝试获取锁的线程时不会立即阻塞，而是采用**循环的方式去尝试获取**。自己在哪儿一致循环获取，就好像自己在旋转一样。它的优点是**减少线程切换的上下文开销**，缺点是**消耗 CPU**。CAS 底层 的 `getAndAddInt` 就是自旋锁的思想，同时还存在 ABA 问题。

```java
package java_two;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 自旋锁
 */

public class SpinLockDemo {
    /** 原子引用线程 */
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        // 启动 t1 线程，开始操作
        new Thread(() -> {
            // 开始占有锁
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 开始释放锁
            spinLockDemo.myUnLock();

        }, "t1").start();

        // 让 main 线程暂停1秒，使得 t1 线程先执行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 1 秒后，启动 t2 线程，开始占用这个锁
        new Thread(() -> {
            // 开始占有锁
            spinLockDemo.myLock();
            // 开始释放锁
            spinLockDemo.myUnLock();
        }, "t2").start();
    }

    public void myLock() {
        // 获取当前进来的线程
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " come in");
        while (!atomicReference.compareAndSet(null, thread)) {
            // 开始自旋，期望值是 null，更新值是当前线程，如果是 null，则更新为当前线程，否则自旋
        }
        System.out.println(thread.getName() + " come out");
    }

    public void myUnLock() {
        // 获取当前进来的线程
        Thread thread = Thread.currentThread();
        // 使用完后，将其原子引用变为 null
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + " invoked myUnLock()");
    }
}
```

#### 读写锁/共享独占锁

读锁是共享的，写锁是独占的。

共享锁就是一个锁能被多个线程所持有。

独占锁就是一个锁只能被一个线程所持有。`synchronized` 和 `juc.ReentrantLock` 都是独占锁。

但是有的时候，需要读写分离，那么就要引入读写锁，即 `juc.ReentrantReadWriteLock`，其读锁是共享锁，其写锁是独占锁。以下例子，就避免了写被打断，但实现了多个线程同时读。

```java
package java_two;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 读写锁
 */

public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        // 线程操作资源类，5 个线程写
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt +  "");
            }, String.valueOf(i)).start();
        }

        // 线程操作资源类， 5 个线程读
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    /** 可以看到有些线程读取到 null，可用 ReentrantReadWriteLock 解决 */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String k, Object v) {
        // 创建一个写锁
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入");
            try {
                // 模拟网络拥堵，延迟 0.3s
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(k, v);
            System.out.println(Thread.currentThread().getName() + " 写入完成");
        } finally {
            lock.writeLock().unlock();
        }

    }

    public void get(String k) {
        // 创建一个读锁
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取");
            try {
                // 模拟网络拥堵，延迟 0.3s
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object o = map.get(k);
            System.out.println(Thread.currentThread().getName() + " 读取完成 " + o);
        } finally {
            lock.readLock().unlock();
        }
    }
}
```

#### synchronized 和 Lock 的区别

主要有以下几个方面的区别：

* **原始构成**：sync 是关键字，属于 JVM 层面的，而 Lock 是一个接口，属于 JDK API 层面的
* **使用方法**：sync 不需要手动释放锁，而 Lock 需要 在 finally 中手动释放
* **是否可中断**：sync 不可中断，除非抛出异常或者正常运行完成，而 Lock 通过 设置超时时间 或 调用 `interrupt()` 可被中断
* **是否为公平锁**：sync 只能为非公平锁，而 Lock 既可以为 公平锁，又可以为非公平锁
* **是否可绑定多个条件**：sync 不能，它只能随机唤醒，而 Lock 可以通过 Condition 来绑定多个条件，进行精确唤醒



### 谈谈对 CountDownLatch、CyclicBarrier、Semaphore 的理解？

#### CountDownLatch

`CountDownLatch` 内部维护了一个**计数器**，只有当**计数器等于 0** 时，某些线程才会停止阻塞，开始执行。

它主要有两个方法：

* `countDown()` 让计数器减 1
* `await()` 让线程阻塞
* 当**计数器等于 0** 时，阻塞线程会自动唤醒

```java
package java_two;

import java.util.concurrent.*;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc CountDownLatch 教室关门例子
 */

public class CountDownLatchDemo {
    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(6, 200, 10L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println("ThreadName: " + Thread.currentThread().getName() + "，离开教室");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("ThreadName: " + Thread.currentThread().getName() + "，学生全部离开，已关闭教室");
        threadPoolExecutor.shutdown();
    }
}
```



#### CyclicBarrier

`CyclicBarrier` 与 `CountDownLatch` 相反，只有当**计数器等于 指定值** 时，某些线程才会停止阻塞，开始执行。

`CyclicBarrier` 与 `CountDownLatch` 的主要区别是，前者可以复用，而后者不行。

它主要有一个方法：

* `await()` 线程进入屏障

```java
package java_two;

import java.util.concurrent.*;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc  CyclicBarrier 召唤神龙例子
 */

public class CyclicBarrierDemo {
    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(7, 200, 10L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        // 定义一个循环屏障，参数1 为需要累加的值，参数2 为需要执行的方法
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙");
        });
        for (int i = 0; i < 7; i++) {
            final int tempInt = i;
            threadPoolExecutor.execute(() -> {
                System.out.println("ThreadName: " + Thread.currentThread().getName() + "，收集到第 " + tempInt + " 颗龙珠");
                try {
                    // 先到的被阻塞，等全部线程完成后，才能执行方法
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolExecutor.shutdown();
    }
}
```

#### Semaphore

信号量主要用于两个目的，一个是用于**多个共享资源的互斥使用**，另一个用于**并发线程数的控制**。

常规的锁（例如 `synchronied` 和 `Lock`）在任何时刻都只允许一个任务访问一项资源，而 `Semaphore` 运行 n 个任务同时访问一项资源。

但 `CountDownLatch` 不能复用，而 `Semaphore` 完美的解决了这个问题，

它主要有两个方法：

* accquire() 抢占资源/锁
* release() 释放资源/锁

```java
package java_two;

import java.util.concurrent.*;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc Semaphore 抢车位例子
 */

public class SemaphoreDemo {
    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(6, 200, 10L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        // 初始化一个信号量为 3，非公平锁，模拟3个停车位
        Semaphore semaphore = new Semaphore(3, false);
        for (int i = 0; i < 6; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("ThreadName: " + Thread.currentThread().getName() + "，抢到车位 ");
                    // 停车 3s
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("ThreadName: " + Thread.currentThread().getName() + "，离开车位 ");
                    semaphore.release();
                }
            });
        }
        threadPoolExecutor.shutdown();
    }
}
```



### 阻塞队列知道吗？谈谈其理解？

我们知道**线程池的工作原理**：

线程池创建，准备好 corePoolSize 数量的核心线程，准备接受任务

* 如果核心线程已满，就会将任务放入**阻塞队列**中，空闲的核心线程就会自己去阻塞队列中获取任务
* 如果**阻塞队列**已满，就会直接开启新线程执行，但是最大不超过 maximumPoolSize 数
* 如果超过 maximumPoolSize 数，就会使用拒绝策略拒绝任务，当执行完成后，在指定的 keepAliveTime 时间以后释放 maximumPoolSize - corePoolSize 这些数量的线程





### 线程池用过吗？谈谈对 ThreadPoolExecutor 的理解？



### 生产过程中如何合理的设置线程池参数？拒绝策略怎么配置？



### 死锁编码问题以及如何定位分析？



## JVM



## MQ



## Redis



## Spring



## Netty&RPC



## 网络



## 数据库



## 微服务



## 项目



## 总结

### 蚂蚁花呗面试题

* Java 容器有哪些？哪些是同步容器，哪些是并发容器？

* ArrayList 和 LinkedList 的插入和访问的时间复杂度？

* Java 反射原理，注解原理？

* 新生代分为几个区？使用什么算法进行垃圾回收？为什么使用这个算法？

* HashMap 在什么情况下会扩容，或者有哪些操作会导致扩容？

* HashMap push() 的执行过程？

* HashMap 检测到 hash 冲突后，将元素插入在链表的末尾还是开头？

* JDK1.8 还采用了红黑树，讲讲红黑树的特性，为什么人家一定要用红黑树而不是 AVL、B 树之类的？

* Https 和 Http 区别，有没有用过其他安全传输手段？

* 线程池的工作原理，几个重要参数，然后给了具体几个参数分析线程池会怎么做？阻塞队列的作用是什么？

* Linux 怎么查看系统负载情况？

* 请详细描述 SpringMVC 处理请求的全流程？Spring 一个 bean 装配的过程？

* 讲一讲 AtomicInteger，为什么要用 CAS 而不是 synchronized？

* 自我介绍、工作经历、技术栈？

* 项目中你学到了什么技术？

* 微服务划分的粒度？

* 微服务的高可用怎么保证的？

* 常用的负载均衡，该怎么用，你能说下吗？

* 网关能够为后端服务带来哪些好处？

* Spring Bean 的生命周期

* HashSet 是不是线程安全的？为什么不是线程安全的？

* Java 中有哪些线程安全的 Map？

* Concurrenthashmap 是怎么做到线程安全的？

* HashTable 你了解过吗？

* 如何保证线程安全问题？

* synchronized、lock 异同？

* volatile 的原子性问题？为什么 i++ 这种不支持原子性？从计算机原理的设计来讲下不能保证原子性的原因？

* 谈谈 happens before 原则？

  **解析**：由 volatile 关键字引出的。Java 内存模型（JMM）的三个特征：原子性、可见性、有序性。volatile 和 synchronized 和 Lock 都可以保证有序性。但是 JMM 也具备一些先天的有序性，即不需要通过任何手动也可以保证有序性，这通常被称为 happens before 原则。主要有：

  * 程序顺序规则：一个线程中的每个操作，happens-before 于该线程中的任意后续操作。
  * 监视器锁规则：对一个线程的解锁，happens-before 于随后对这个线程的加锁。
  * **volatile 变量规则**：对一个 volatile 域的写，happens-before 于后续对这个 volatile 域的读。如果一个线程先去写一个变量，另外一个线程再去读，那么写入操作一定在读操作之前。
  * 传递性规则：如果 A happens-before B，且 B happens-before C，那么 A happens-before C。
  * start() 规则： 如果线程 A 中执行 start线程B 操作， 那么A线程的 start线程B happens-before 于 B 中的任意操作。
  * join() 规则：如果线程 A 执行 join线程B 操作并且成功返回，那么线程 B 中的任意操作 happens-before 于线程 A 从 join线程B 操作并且成功返回。
  * interrupt() 规则：
  * finalize() 规则：

* CAS 操作

* 公平锁和非公平锁

* Java 读写锁

* 读写锁设计主要解决什么问题？



### 美团面试题

* 最近做的比较熟悉的项目是哪个，画一下项目技术架构图？
* JVM 老年代和新生代的比例？
* YGC 和 FGC发生的具体场景？
* jstack，jmap，jutil 分别的意义？如何线上排查 JVM 的相关问题？
* 线程池的构造类的方法的 5 个参数的具体意义？
* 单机上一个线程池正在处理服务时如果忽然断电怎么办？正在处理和阻塞队列里的请求怎么处理？
* 使用无界阻塞队列会出现什么问题？接口如何处理重复请求？



### 百度面试题

* 介绍一下集合框架？
* hashmap hastable 底层实现什么区别？hashtable 和 concurrenthashtable 呢？
* hashmap 和 treemap 什么区别？底层数据结构是什么？
* 线程池用过吗？都有什么参数？底层如何实现的？
* synchronized 和 Lock 有什么区别？synchronized 什么情况是对象锁？什么情况是全局锁？为什么？
* ThreadLocal 是什么底层如何实现？写一个例子？
* volatile 的工作原理？
* CAS 知道吗？如何实现的？
* 请用至少四种写法写一个单例模式？
* 请介绍一下 JVM 内存模型？用过什么垃圾回收器？线上发送频繁 full gc 如何处理？CPU使用率过高怎么办？如何定位问题？如何解决？说一下解决思路和处理方法？
* 知道字节码吗？字节码都有哪些？`Integer x =5, int y =5`，比较 `x =y` 都经过哪些步骤？讲讲类加载机制？都有哪些类加载器？这些类加载器都加载哪些文件？
* 手写一下类加载 Demo？
* 知道 osgi 吗？它是如何实现的？
* 请问你做过哪些 JVM 优化？使用什么方法达到什么效果？
* `classforName(“java.lang.String”)` 和 `String classgetClassLoader() LoadClass(“java.lang.String”)` 有什么区别？



### 今日头条面试题

* HashMap 如果一直 put 元素会怎么样？ hashcode 全都相同如何？
* ApplicationContext 的初始化过程？
* GC 用什么收集器？收集的过程如何？哪些部分可以作为 GC Root？
* volatile 关键字，指令重排序有什么意义 ？synchronied 怎么用？
* Redis 数据结构有哪些？如何实现 sorted set？
* 并发包里的原子类有哪些？怎么实现？
* MySQL 索引是什么数据结构？ B tree 有什么特点？优点是什么？
* 慢查询怎么优化？



### 京东金融面试题

* Dubbo超时重试？Dubbo 超时时间设置？
* 如何保障请求执行顺序？
* 分布式事务与分布式锁(扣款不要出现负数)？
* 分布式 Session 设置？
* 执行某操作，前 50 次成功，第 51 次失败，a 全部回滚，b 前 50 次提交，第51次抛异常，a b 场景分别如何设计？
* Spring 的传播特性？
* Zookeeper 有那些作用？
* JVM 内存模型？
* 数据库的垂直和水平拆分？
* MyBatis 如何分页？如何设置缓存？MySQL 分页？

