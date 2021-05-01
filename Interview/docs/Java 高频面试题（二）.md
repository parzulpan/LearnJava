# Java 高频面试题（二）

## Java 基础



## JUC

### 谈谈对 volatile 关键字的理解？



### 谈谈对 CAS 的理解？



### 谈谈对原子类 AtomicInteger 的 ABA 问题的理解？原子更新引用知道吗？



### ArrayList 是线程安全的吗？请编写一个线程不安全的 demo 并给出解决方案？



### 谈谈对公平锁、非公平锁、可重入锁、递归锁、自旋锁的理解？请编写一个自旋锁 demo？



### 谈谈对 CountDownLatch、CyclicBarrier、Semaphore 的理解？



### 阻塞队列知道吗？谈谈其理解？



### 线程池用过吗？谈谈对 ThreadPoolExecutor 的理解？生产过程中如何合理的设置线程池参数？



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

  * 程序顺序规则：
  * 监视器锁规则：
  * volatile 变量规则：
  * 传递性规则：
  * start() 规则：
  * join() 规则：
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
* volitile 的工作原理？
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
* Volatile 关键字，指令重排序有什么意义 ？synchronied 怎么用？
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

