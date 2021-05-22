# JavaInterviewAll

## 简介

Java学习+面试指南，[GitHub](https://github.com/Snailclimb/JavaGuide)，[在线阅读](https://snailclimb.gitee.io/javaguide)

## Java

### 基础

#### String 为什么是不可变的？String 和 StringBuffer、StringBuilder 的区别是什么？

String 类中使用字符数组保存字符串，并且 使用 final 关键字修饰，所以 String 对象是不可变的。

它们之间**区别**：

* 从可变性来讲：String 不可变；而 StringBuffer 和 StringBuilder 继承自 AbstractStringBuilder，都是可变的
* 从线程安全来讲：String 线程安全的；StringBuffer 对方法加了同步锁，是线程安全的；StringBuilder 没有对方法加同步锁，不是线程安全的
* 从性能来讲：String 改变时都会生成新的 String 对象；StringBuffer 改变时是对本身操作；StringBuilder 改变时同样是对本身操作，并且性能比 StringBuffer 好点；

**总结**：

* 操作少量的数据，使用 String
* 单线程操作大量的数据，使用 StringBuilder
* 多线程操作大量的数据，使用 StringBuffer



#### try-catch-finally 返回值问题

当 try 或者 finally 语句中都有 return 语句时，在方法返回之前，finally 语句的内容将被执行，并且 finally 语句的返回值将会覆盖原始的返回值。

```java
public class Test {
    public static int f(int value) {
        try {
            return value * value;
        } finally {
            if (value == 2) {
                return 0;
            }
        }
    }
}
```

如果调用 f(2)，返回值将是 0，因为 finally 语句块的返回值覆盖了 try 语句块的返回值。



#### 使用 try-with-resources 来代替 try-catch-finally

面对必须要关闭的资源，总是应该优先使用 `try-with-resources` 而不是 `try-finally`，它产生的代码更简短，更清晰，产生的异常对我们也更有用。

两个关键点

* **适用范围**：任何实现 `java.lang.AutoCloseable` 或者 `java.io.Closeable` 的对象，均可以使用它

* **执行顺序**：先关闭资源，再运行 catch 或者 finally 语句块代码

```java
// 传统做法
Scanner scanner = null;
try {
    scanner = new Scanner(new File("read.txt"));
    while(scanner.hasNext()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException e) {
    e.printStackTrace();
} finally {
    if (scanner != null) {
        scanner.close();
    }
}
```

```java
// Java7 之后
try(Scanner scanner = new Scanner(new File("read.txt"))) {
    while(scanner.hasNext()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException e) {
    e.printStackTrace();
}
```

当多个资源需要关闭的时候，通过使用分号分隔，可以在 `try-with-resources` 块中声明多个资源。



#### Java 序列化中如果使某些字段不进行序列化

对于不想进行序列化的变量，可以使用 `transient` 关键字修饰。它的作用是：阻止实例中那些用此关键字修饰的变量序列化，不过它只能修饰变量，不能修饰类和方法。



#### BigDecimal 的用处和注意事项

我们知道，对于浮点数之间的等值判断，基本数据类型不能用 `==` 来比较，包装数据类型不能用 `equals` 来判断。这是因为和浮点数的编码方式有关。

```java
float a = 1.0f - 0.9f;
float b = 0.9f - 0.8f;
System.out.println(a);// 0.100000024
System.out.println(b);// 0.099999964
System.out.println(a == b);// false
```

可以看到，上面的使用存在**精度丢失**，可以使用 BigDecimal 来定义浮点数的值，再进行浮点数的运算操作：

```java
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("0.9");
BigDecimal c = new BigDecimal("0.8");
BigDecimal x = a.subtract(b);
BigDecimal y = b.subtract(c);
System.out.println(x);// 0.1
System.out.println(y);// 0.1
System.out.println(x == y);// true
```

BigDecimal 的方法使用：

* `a.compareTo(b)` 返回 -1 表示 `a` 小于 `b`，0 表示 `a` 等于 `b` ， 1表示 `a` 大于 `b`。
* `a.add(b)` 加
* `a.subtract(b)` 减
* `a.multiply(b)` 乘
* `a.divide(b)` 除
* `a.setScale(int newScale, roundingMode)` 设置保留几位小数以及保留规则

**注意事项**：

* 在使用 BigDecimal 时，为了防止精度丢失，推荐使用它的 **BigDecimal(String) 构造方法** 或者 **BigDecimal.valueOf(double) 方法** 来创建对象
* 因为 BigDecimal(double) 存在精度丢失风险，在精确计算或者值比较的场景可能会导致业务逻辑异常



#### 基本数据类型和其包装类的使用场景

实际开发中推荐：

* 所有的 POJO 类属性必须使用包装数据类型
  * 理由：比如如果自定义了一个 Student 类，其中有一个属性是成绩 score ，如果用 int 而不用 Integer 定义，则一次考试中，学生可能没考，值是 0，也可能考了，但考了 0 分，值也是 0，显然这两种情况不同，但是结果相同。
* RPC 方法的返回值和参数必须使用包装数据类型
  * 理由：比如显示成交总额涨跌情况，即正负 x%，x 为基本数据类型，调用的 RPC 服务，调用不成功时，返回的是默认值，页面显示为 0%，这是不合理的，应该显示成中划线。所以包装数据类型的 null 值，能够表示额外的信息，例如远程调用失败，异常退出。
* 所有的局部变量使用基本数据类型



#### Arrays.asList() 使用的注意事项

`Arrays.asList()` 可以将一个数组转换为一个 List 集合。

```java
String[] myArray = { "AA", "BB", "CC" };
// 等价于 List<String> myList = Arrays.asList("Apple", "Banana", "Orange");
List<String> myList = Arrays.asList(myArray);

// AA
System.out.println(myList.get(0));

// java.lang.UnsupportedOperationException
// myList.add("DD");

myArray[0] = "EE";

// EE
System.out.println(myList.get(0)); 


```

值得注意的是，它将数组转换为集合后，返回对象是一个 Arrays 内部类。它体现的是适配器模式，只是一个转换接口，底层其实还是一个数组。所以，并没有实现集合的修改方法，**不能使用其修改集合相关的方法**。

这就解释了上面程序：

* `myList.add("DD");` 会出现运行时异常
* 赋值 `myArray[0] = "EE";` 后，`myList.get(0)` 也会随之改变

还有一个需要注意的地方，**传递的数组必须是对象数组，而不能是基本类型数组**。

```java
int[] myArray = {1, 2, 3};
List<int[]> asList = Arrays.asList(myArray);

// 1
System.out.println(asList.size());
// 数组地址值
System.out.println(asList.get(0));
// java.lang.ArrayIndexOutOfBoundsException
System.out.println(asList.get(1));

int[] array = (int[]) asList.get(0);
// 1
System.out.println(array[0]);
```

当传递一个基本类型数组时，`Arrays.asList(T... a)` 真正得到的参数不是数组中的元素，而是数组对象本身，此时 List 的唯一元素就是这个数组。使用包装类数组可以解决这个问题：`int[] myArray = {1, 2, 3}; => Integer[] myArray = {1, 2, 3};`

**正确的将数组转换为 ArrayList**：

* 直接强转（推荐）

  ```java
  List<String> arrayList = new ArrayList<>(Arrays.asList("a", "b", "c"));
  System.out.println(arrayList.size());
  arrayList.add("DD");
  System.out.println(arrayList.size());
  ```

* 使用 Java8 的 Stream（推荐）

  ```java
  Integer[] array = {1, 2, 3};
  List<Integer> collect = Arrays.stream(array).collect(Collectors.toList());
  System.out.println(collect.size());
  collect.add(4);
  System.out.println(collect.size());
  
  // 依赖 boxed 的装箱操作，基本类型数组也可以实现转换
  int[] array2 = {1, 2, 3};
  List<Integer> collect2 = Arrays.stream(array2).boxed().collect(Collectors.toList());
  System.out.println(collect2.size());
  collect2.add(4);
  System.out.println(collect2.size());
  ```

* 使用 Apache Commons Collections

  ```java
  List<String> list = new ArrayList<String>();
  CollectionUtils.addAll(list, str);
  ```



#### Collection.toArray() 使用的注意事项

该方法是一个泛型方法：`<T> T[] toArray(T[] a);`   返回一个包含此集合中所有元素的数组； 返回数组的运行时类型是指定数组的运行时类型。如果`toArray`方法中没有传递任何参数的话返回的是`Object`类型数组。

```java
String[] str = {"aa", "bb", "cc"};
ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(str));
System.out.println(arrayList);

Collections.reverse(arrayList);
System.out.println(arrayList);

// 没有指定类型的话会报错
str = arrayList.toArray(new String[0]);
for (String s : str) {
    System.out.println(s);
}
```

由于 JVM 优化，`new String[0]` 作为 `Collection.toArray()` 方法的参数现在使用更好，`new String[0]` 就是起一个模板的作用，指定了返回数组的类型，0 是为了节省空间，因为它只是为了说明返回的类型。



#### 不要在 foreach 循环中进行元素的 remove/add 操作

如果要进行 remove 操作，可以调用 Iterator 的 remove() 而不是集合类的 remove()，因为如果列表在任何时间从结构上修改创建迭代器之后，以任何方式除非通过迭代器自身`remove/add`方法，迭代器都将抛出一个`ConcurrentModificationException`。这就是单线程状态下产生的 **fail-fast 机制**。

`java.util` 包下面的所有的集合类都是 **fail-fast** 的，而 `java.util.concurrent` 包下面的所有的类都是 **fail-safe** 的。

```java
List<Integer> list = new ArrayList<>();
for (int i = 0; i < 11; i++) {
    list.add(i);
}

// 源码实际也是用的迭代器
list.removeIf(filter -> filter % 2 == 0);
System.out.println(list);

// 错误使用，java.util.ConcurrentModificationException
for (Integer integer : list) {
    if (1 == integer) {
        list.remove(integer);
    }
}
System.out.println(list);
```

**总结**：不要在 foreach 循环里进行元素的 remove/add 操作。remove 元素请使用 Iterator 方式或者集合类的 removeIf()，如果是并发操作，需要对 Iterator 对象加锁。



### 容器



### 并发

#### sleep() 方法和 wait() 方法的异同

两者最主要的区别：`sleep()` 方法没有释放锁，而 `wait()` 方法释放了锁。

两者的相同点：都可以暂停线程的执行。

`wait()` 通常被用于线程间交互/通信，`sleep()` 通常被用于暂停执行。

`wait()` 方法被调用后，线程不会自动苏醒，需要别的线程调用同一个对象上的 `notify()` 或者 `notifyAll()` 。`sleep() `执行后，线程会自动苏醒。**但是**使用 `wait(long timeout)` 超时后线程会自动苏醒。



#### 调用 start() 方法时会执行 run() 方法，为什么不直接调用 run() 方法

`new` 一个线程，线程就进入了新建状态，调用 `start()` 方法，会启动一个线程并使线程进入就绪状态，当分配到时间片后就可以开始运行了，`start()` 方法会执行线程的相应准备工作，然后自动执行 `run()` 方法的内容，这是真正的多线程工作。

如果直接调用 `run()` 方法，会把它当成一个 `main` 线程下的普通方法去执行，并不会在某个线程中执行它，这不是真正的多线程工作。



#### 谈谈对 synchronized 关键字的理解

**synchronized 关键字的作用？**

`synchronized` 关键字解决的是多个线程之间访问同一个资源的同步性，`synchronized` 关键字可以保证被它修饰的方法或者代码块在任意时刻只能由一个线程执行。

**synchronized 关键字的使用方式？**

* 修饰实例方法：作用于当前对象实例，进入同步代码之前要获得当前对象实例的锁

  ```java
  synchronized void method() {
      // your code
  }
  ```

* 修饰静态方法：作用于类（所有对象实例），进入同步代码之前要获取当前类的锁

  ```java
  synchronized static void method() {
      // your code
  }
  ```

* 修饰代码块：作用于指定对象实例或者类，进入同步代码之前要获取给定对象的锁

  ```java
  synchronized(this/类.class) {
      // your code
  }
  ```

* **总结**：

  * `synchronized` 关键字加到 `static` 静态方法和 `synchronized(class)` 代码块上都是是给 Class 类上锁。
  * `synchronized` 关键字加到实例方法上是给对象实例上锁。
  * 尽量不要使用 `synchronized(String a)` 因为 JVM 中，字符串常量池具有缓存功能。
  * 构造方法不能使用 `synchronized` 关键字。

* 应用：单例模式-线程安全双重校验锁

  ```java
  
  /**
   * @author parzulpan
   *
   * synchronized 关键字 应用：单例模式-线程安全双重校验锁
   */
  
  public class Singleton {
      /** valatile 防止指令重拍 */
      private volatile static Singleton instance;
  
      private Singleton() {
  
      }
  
      public static Singleton getInstance() {
          if (instance == null) {
              synchronized(Singleton.class) {
                  if (instance == null) {
                      instance = new Singleton();
                  }
              }
          }
          return instance;
      }
  }
  ```

**synchronized 关键字的底层原理？**

因为 `synchronized` 关键字底层属于 JVM 层面，分为以下情况：

* 同步代码块：观察以下代码

  ```java
  /**
   * @author parzulpan
   *
   * synchronized 关键字的底层原理 - 同步代码块
   * 使用 javap 查看相关字节码信息
   * java SynchronizedDemo.java
   * javap -c -s -v -l SynchronizedDemo.class
   * 
   */
  
  public class SynchronizedDemo {
      SynchronizedDemo() {
          System.out.println("SynchronizedDemo()");
      }
  
      public void method() {
          synchronized(SynchronizedDemo.class) {
              System.out.println("synchronized method()");
          }
      }
      
  }
  ```

  ```shell
    public void method();
      descriptor: ()V
      flags: (0x0001) ACC_PUBLIC
      Code:
        stack=2, locals=2, args_size=1
           0: ldc           #1                  // class SynchronizedDemo
           2: dup
           3: astore_1
           4: monitorenter
           5: getstatic     #10                 // Field java/lang/System.out:Ljava/io/PrintStream;
           8: ldc           #29                 // String synchronized method()
          10: invokevirtual #18                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
          13: aload_1
          14: monitorexit
          15: goto          21
          18: aload_1
          19: monitorexit
          20: athrow
          21: return
        Exception table:
           from    to  target type
               5    15    18   any
              18    20    18   any
        LineNumberTable:
          line 17: 0
          line 18: 5
          line 17: 13
          line 20: 21
        LocalVariableTable:
          Start  Length  Slot  Name   Signature
              0      22     0  this   LSynchronizedDemo;
        StackMapTable: number_of_entries = 2
          frame_type = 255 /* full_frame */
            offset_delta = 18
            locals = [ class SynchronizedDemo, class java/lang/Class ]
            stack = [ class java/lang/Throwable ]
          frame_type = 250 /* chop */
            offset_delta = 2
  ```

  解读：从字节码文件可以看出，同步代码块使用的 monitorenter 和 monitorexit 指令，其中 monitorenter 指向同步代码块的开始位置，monitorexit 则指向同步代码块的结束位置。

  在 JVM 中，每个对象中都内置了一个 ObjectMonitor 对象。`wait/notify` 等方法也依赖于 `monitor` 对象，这就是为什么只有在同步代码块或者方法中才能调用 `wait/notify` 等方法，否则会抛出`java.lang.IllegalMonitorStateException` 的异常的原因。

  当执行 monitorenter 指令时，线程会尝试获取对象的锁（即对象监视器monitor 的持有权），如果锁的计数器为 0 表示锁可以被获取，获取锁后将其计数器设为 1。

  当执行 monitorexit 指令时，将锁的计数器设为 0，表示锁被释放。如果获取对象锁失败，那当前线程就要阻塞等待，直到锁被另外一个线程释放为止。

* 同步方法：观察以下代码

  ```java
  /**
   * @author parzulpan
   *
   * synchronized 关键字的底层原理 - 同步方法
   */
  
  public class SynchronizedDemo2 {
      SynchronizedDemo2() {
          System.out.println("SynchronizedDemo2()");
      }
  
      public synchronized void method() {
          System.out.println("synchronized method()");
      }    
  }
  ```

  ```shell
    public synchronized void method();
      descriptor: ()V
      flags: (0x0021) ACC_PUBLIC, ACC_SYNCHRONIZED
      Code:
        stack=2, locals=1, args_size=1
           0: getstatic     #10                 // Field java/lang/System.out:Ljava/io/PrintStream;
           3: ldc           #29                 // String synchronized method()
           5: invokevirtual #18                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
           8: return
        LineNumberTable:
          line 14: 0
          line 15: 8
        LocalVariableTable:
          Start  Length  Slot  Name   Signature
              0       9     0  this   LSynchronizedDemo2;
  ```

  解读：从字节码文件可以看出，同步方法使用的 `ACC_SYNCHRONIZED` 标识符，该标识指明该方法是一个同步方法，JVM 通过该 `ACC_SYNCHRONIZED` 访问标志来辨别一个方法是否声明为同步方法，从而执行相应的同步调用。

**synchronized 和 ReentrantLock 的区别？**





#### 谈谈对 ThreadLocal 的理解





### JVM



### 新特性







## 网络



## 操作系统



## 数据结构和算法

### 基础算法

#### 手写快速排序

```java

public void quickSort(int[] arr, int start, int end) {
    // 从哪个位置开始分割数组
    int mid;
    if (start < end) {
        mid = partition(arr, start, end);
        quickSort(arr, start, mid - 1);
        quickSort(arr, mid + 1, end);
    }
}

public int partition(int[] arr, int start, int end) {
    // 参考值
    int temp = arr[start];
    while(start < end) {
        // 从数组的右边开始向左遍历，直到找到小于参考值的元素
        while(end > start && arr[end] >= temp) {
            --end;
        }
        arr[start] = arr[end];
        
        // 从数组的左边开始向右遍历，直到找到大于参考值的元素
        while(end > start && arr[start] <= temp) {
            ++start;
        }
        arr[end] = arr[start];
        
        return end;
    }
    arr[start] = temp;
    
    return start;
}
```







## 数据库



## 系统设计









