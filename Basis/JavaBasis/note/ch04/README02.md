# 面向对象下

这一章主要涉及其他关键字，包括 this、super、static、final、abstract、interface、package、import 等。

## static

在 Java 类中，可用 static 修饰属性、方法、代码块、内部类。

特点：

* 随着类的加载而加载，由于类只会加载一次，则静态变量在内存中也只会存在一份，存在方法区的静态域中；
* 优先于对象存在；
* 修饰的成员，被所有对象所共享；
* 访问权限允许时，可不创建对象，直接被类调用。

**注意**：

* 在静态的方法中，不能使用 this 关键字和 super 关键字；
* 关于静态属性和静态方法的使用，要从生命周期的角度去理解。

### 单例模式

所谓的单例模式，就是采取一定的方法保证在整个系统中，对某个类只能存在一个对象实例。

单例模式的好处：

* 某些类创建比较频繁，对于一些大型的对象，这是一笔很大的系统开销；
* 省去了 new 操作符，降低了系统内存的使用频率，减轻了 GC 压力；
* 保证独立性等。

实现：

* [饿汉式](../../code/src/com/parzulpan/java/ch04/SingletonTest1.java)：`java.lang.Runtime` 就是一个这样的实现方式
* [懒汉式](../../code/src/com/parzulpan/java/ch04/SingletonTest2.java)

对比：

* 饿汉式的坏处是对象加载时间过长，好处是线程安全的；
* 懒汉式的好处是延迟对象的加载，坏处是线程不安全的；

应用：

* **网站的计数器**，一般也是单例模式实现，否则难以同步；
* **应用程序的日志应用**，一般都使用单例模式实现，这一般是由于共享的日志
文件一直处于打开状态，因为只能有一个实例去操作，否则内容不好追加；
* **数据库连接池**的设计一般也是采用单例模式，因为数据库连接是一种数据库
资源；
* 项目中，**读取配置文件的类**，一般也只有一个对象。没有必要每次使用配置
文件数据，都生成一个对象去读取；
* **Application** 也是单例的典型应用；
* Windows 的 **Task Manager （任务管理器）** 就是很典型的单例模式；
* Windows 的 **Recycle Bin （回收站）** 也是典型的单例应用。在整个系统运行过程中，回收站一直维护着仅有的一个实例。

## 理解 main 方法的语法

由于 JVM 需要调用类的 main() 方法，所以该方法的访问权限必须是
**public**，又因为 JVM 在执行 main() 方法时不必创建对象，所以该方法必须是 **static** 的，该方法接收一个 String 类型的数组参数，该数组中保存执行 Java命令时传递给所运行的类的**参数**。

又因为 main() 方法是静态的，我们不能直接访问该类中的非静态成员，必须创
建该类的一个实例对象后，才能通过这个对象去访问类中的非静态成员。

## 代码块

代码块（或初始化块）的作用：对 Java 类或对象进行初始化。

一个类中代码块若有修饰符，则只能被 static 修饰，称为静态代码块
（static block），没有使用 static 修饰的，称为非静态代码块。

**静态代码块**：用 static 修饰的代码块

* 可以有输出语句；
* 可以对类的属性、类的声明进行初始化操作；
* 不可以调用非静态的属性和方法；
* 若有多个静态代码块，那么按照从上到下的顺序依次执行；
* 静态代码块随着类的加载而加载，且只执行一次。静态代码块的执行要先于非静态代码块。

**非静态代码块**：没有 static 修饰的代码块

* 可以有输出语句；
* 可以对类的属性、类的声明进行初始化操作；
* 可以调用非静态和静态的属性和方法；
* 若有多个非静态代码块，那么按照从上到下的顺序依次执行；
* 每次创建对象的时候，都会执行一次。非静态代码块的执行要先于构造器。

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 代码块练习
 */

class Root{
    static{
        System.out.println("Root的静态初始化块");  // 1
    }

    {
        System.out.println("Root的普通初始化块");  // 4
    }

    public Root(){
        System.out.println("Root的无参数的构造器"); // 5
    }
}

class Mid extends Root{
    static{
        System.out.println("Mid的静态初始化块");   // 2
    }

    {
        System.out.println("Mid的普通初始化块");   // 6
    }

    public Mid(){
        System.out.println("Mid的无参数的构造器");  // 7
    }

    public Mid(String msg){
        //通过this调用同一类中重载的构造器
        this();
        System.out.println("Mid的带参数构造器，其参数值："
                + msg); // 8
    }
}

class Leaf extends Mid{
    static{
        System.out.println("Leaf的静态初始化块");  // 3
    }

    {
        System.out.println("Leaf的普通初始化块");  // 9
    }

    public Leaf(){
        //通过super调用父类中有一个字符串参数的构造器
        super("尚硅谷");
        System.out.println("Leaf的构造器"); // 10
    }
}

public class LeafTest{
    public static void main(String[] args){
        new Leaf(); // 输出结果顺序看注释
    }
}
```

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 代码块练习
 */


class Father {
    static {
        System.out.println("11111111111");  // 1
    }

    {
        System.out.println("22222222222");  // 2
    }

    public Father() {
        System.out.println("33333333333");  // 3

    }

}

public class Son extends Father {
    static {
        System.out.println("44444444444");  // 4
    }

    {
        System.out.println("55555555555");  // 5
    }

    public Son() {
        System.out.println("66666666666");  // 6
    }

    public static void main(String[] args) { // 由父及子 静态先行
        System.out.println("77777777777");  // 7
        System.out.println("************************"); // 8
        new Son();  // 输出结果：1 -> 4 -> 7 -> 8 -> 2 -> 3 -> 5 -> 6
        System.out.println("************************");

        new Son();  // 输出结果：2 -> 3 -> 5 -> 6
        System.out.println("************************");
        new Father();   // 输出结果：2 -> 3
    }

}
```

总结：由父及子，静态先行。

程序中成员变量赋值的执行顺序：

* 声明成员变量的默认初始化；
* 显式初始化、多个初始化块一次被执行（同级别下按先后顺序执行）；
* 构造器再对成员进行初始化操作；
* 通过“对象.属性”或“对象.方法”的方式，可多次给属性赋值。

## final

在 Java 中声明**类**、**方法**和**变量**时，可使用关键字 final 来修饰，表示“最终的”。

* final 标记的类不能被继承。比如 String类、System类、StringBuffer类；
* final 标记的方法不能被子类重写。比如 Object 类中的 `getClass()`；
* final 标记的变量（成员变量或局部变量）称为常量（名称大写，且只能被赋值一次）。比如 `final double MY_PI = 3.14;`。

**注意**：final 标记的**成员变量**必须 *在声明时* 或 *在每个构造器中* 或 *代码块中* **显式赋值**，然后才能使用。

static final 用来修饰属性，则称为全局常量。

## 抽象类与抽象方法

* 用 abstract 关键字修饰一个类，这个类叫做抽象类；
  * 抽象类不能被实例化；
  * 抽象类是用来被继承的，抽象类的子类必须重写父类的抽象方法，并提供方法体，此时才可实例化。若没有重写全部的抽象方法，仍为抽象类，需要使用 **abstract** 修饰；
  * 抽象类中一定有构造器，便于子类实例化调用（涉及子类对象实例化的全过程）。
* 用 abstract 关键字修饰一个方法，这个方法叫做抽象方法；
  * 抽象方法：只有方法的声明，没有方法的实现，以分号结束。比如：`public abstract void talk();`
  * 含有抽象方法的类必须被声明为抽象类。

**注意**：

* 不能用 abstract 修饰变量、代码块、构造器；
* 不能用 abstract 修饰私有方法、静态方法、final 的方法、final 的类。

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 抽象类举例
 */

public class AbstractTest {
    public static void main(String[] args) {
        AA aa = new BB();
        aa.m1();
        aa.m2();
    }
}

abstract class AA {
    abstract void m1();

    public void m2() {
        System.out.println("A类中定义的m2方法");
    }
}

class BB extends AA {
    void m1() {
        System.out.println("B类中定义的m1方法");
    }
}
```

### 模版方法模式

抽象类体现的就是一种模板模式的设计，抽象类作为多个子类的通用模板，子类在抽象类的基础上进行扩展、改造，但子类总体上会保留抽象类的行为方式。

解决的问题：

* 当功能内部一部分实现是确定的，一部分实现是不确定的。这时可以把不确定的部分暴露出去，让子类去实现。
* *换句话说，在软件开发中实现一个算法时，整体步骤很固定、通用，这些步骤已经在父类中写好了。但是某些部分易变，易变部分可以抽象出来，供不同子类实现。这就是一种模板模式。

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 抽象类的应用：模版方法模式
 */

public class TemplateMethodTest2 {
    public static void main(String[] args) {
        BankTemplateMethod bankTemplateMethod = new DrewMoney();
        bankTemplateMethod.process();
        BankTemplateMethod bankTemplateMethod1 = new ManageMoney();
        bankTemplateMethod1.process();
    }

}

abstract class BankTemplateMethod {

    private int id;
    private static int init = 0;

    BankTemplateMethod() {
        super();
        id = init++;
    }

    // 具体方法
    public void takeNumber() {
        System.out.println("取号排队 " + this.id);
    }
    public void evaluate() {
        System.out.println("反馈评分\n");
    }

    // 钩子方法，办理具体的业务
    public abstract void transact();

    // 模版方法，把基本操作组合到一起，子类一般不能重写
    public final void process() {
        this.takeNumber();
        this.transact();
        this.evaluate();
    }
}

class DrewMoney extends BankTemplateMethod {
    @Override
    public void transact() {
        System.out.println("我要取款");
    }
}

class ManageMoney extends BankTemplateMethod {
    @Override
    public void transact() {
        System.out.println("我要理财");
    }
}
```

模板方法设计模式是编程中经常用得到的模式。各个框架、类库中都有他的影子，比如常见的有：

* 数据库访问的封装；
* Junit 单元测试；
* JavaWeb 的 Servlet 中关于 doGet/doPost 方法调用；
* Hibernate 中模板程序；
* Spring 中 JDBCTemlate、HibernateTemplate 等。

## interface

为什么要有接口：

* 一方面，有时必须从几个类中派生出一个子类，继承它们所有的属性和方法。但是，Java 不支持多重继承。有了接口，就可以得到多重继承的效果。
* 另一方面，有时必须从几个类中抽取出一些共同的行为特征，而它们之间又没有 is-a 的关系，仅仅是具有相同的行为特征而已。例如：鼠标、键盘、打印机、扫描仪、摄像头、充电器、MP3机、手机、数码相机、移动硬盘等都
支持USB连接。

接口就是规范，定义的是一组规则，体现了现实世界中“如果你是/要...则必须能...”的思想。继承是一个 "是不是" 的关系，而接口实现则是 "能不能"
的关系。

接口（interface）是**抽象方法**和**常量值**定义的集合。

接口的特点：

* 用 interface 来定义，和 class 是并列关系；
* 接口中的所有成员变量都默认是由 public static final 修饰的；
* 接口中的所有抽象方法都默认是由 public abstract 修饰的；
* **接口中没有构造器**，意味着接口不能实例化；
* 接口采用多继承机制，接口让类去**实现**（implements）的方式来使用：
  * 如果实现**覆盖**了接口的所有抽象方法，则此实现类就可以实例化；
  * 如果实现类没有覆盖接口中的所有抽象方法，则此实现类仍未一个抽象类。
* 一个类可以实现多个接口。**语法**：`class SubClass extends SuperClass implements InterfaceA, InterfaceB {}`；
* 接口也可以继承其它接口，并且可以多继承。**语法**：`interface AA extends BB, CC {}`；
* 与继承关系类似，接口与实现类之间存在**多态性**。

抽象类和接口的异同：

| 区别点 | 抽象类 | 接口 |
| :--- | :--- | :--- |
| 定义 | 包含抽象方法的类 | 主要是抽象方法和全局常量的集合 |
| 组成 | 构造器、抽象方法、普通方法、常量、变量 | 抽象方法、常量 |
| 使用 | 子类继承抽象类（extends） | 子类实现接口（implements） |
| 关系 | 抽象类可以实现多个接口 | 接口不能继承抽象类，但允许继承多个接口 |
| 常用设计模式 | 模版方法 | 简单工厂、工厂方法、代理|
| 对象 | 都不能直接实例化，都必须通过对象的多态性产生实例化对象 |
| 局限 | 抽象类有单继承的局限 | 接口没有此局限 |
| 实际 | 作为一个模板 | 是作为一个标准或是表示一种能力 |
| 选择 | 如果抽象类和接口都可以使用的话，优先使用接口，因为避免单继承的局限 |

```java
// 如何定义接口：JDK7及以前，只能定义全局常量和抽象方法。
// 全局常量：public static final，书写时可省略；
// 抽象方法：public abstract，书写时可省略；

public interface Flyable {
    public static final int MAX_SPEED = 7900;
    init MIN_SPEED = 1;

    public abstract void fly();
    void stop();
}

class Plane implements Flyable {
    public void fly() {
        System.out.println("通过引擎起飞");
    }

    punlic void stop() {
        System.out.println("通过减速停止");
    }
}

// 如何定义接口：JDK8，除了能定义全局常量和抽象方法外，还能定义静态方法、默认方法。
```

### Java8 接口新特性

JDK8，除了能定义全局常量和抽象方法外，还能定义静态方法、默认方法。

**静态方法**：使用 static 关键字修饰。**只能通过接口直接调用静态方法，并执行其方法体**。我们经常在相互一起使用的类中使用静态方法。你可以在标准库中找到像 Collection/Collections 或者 Path/Paths 这样成对的接口和类。

**默认方法**：默认方法使用 default 关键字修饰。**可以通过实现类对象来调用**。我们在已有的接口中提供新方法的同时，还保持了与旧版本代码的兼容性。比如：java 8 API 中对 Collection、List、Comparator 等接口提供了丰富的默认方法。

```java
public interface AA {
    double PI = 3.14;

    public default void method() {
        System.out.println("北京");
    }

    default String method1() {
        return "上海";
    }

    public static void method2() {
        System.out.println(“hello lambda!");
    }
}
```

若一个接口中定义了一个默认方法，而另外一个接口中也定义了一个同名同
参数的方法（不管此方法是否是默认方法），在实现类同时实现了这两个接
口时，会出现**接口冲突**。解决办法：实现类必须覆盖接口中同名同参数的方法，来解决冲突。

若一个接口中定义了一个默认方法，而父类中也定义了一个同名同参数的非
抽象方法，则不会出现冲突问题。因为此时遵守**类优先原则**。接口中具有相同名称和参数的默认方法会被忽略。

### 代理模式

代理模式是 Java 开发中使用较多的一种设计模式。代理设计就是为其他对象提供一种代理以控制对这个对象的访问。

信用卡是银行账户的代理， 银行账户则是一大捆现金的代理。 它们都实现了同样的接口， 均可用于进行支付。 消费者会非常满意， 因为不必随身携带大量现金； 商店老板同样会十分高兴， 因为交易收入能以电子化的方式进入商店的银行账户中， 无需担心存款时出现现金丢失或被抢劫的情况。

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 接口的应用：代理模式
 */

public class ProxyTest {

    public static void main(String[] args) {
        Star s = new Proxy(new RealStar());
        s.confer();
        s.signContract();
        s.bookTicket();
        s.sing();
        s.collectMoney();
    }
}

interface Star {
    void confer();// 面谈

    void signContract();// 签合同

    void bookTicket();// 订票

    void sing();// 唱歌

    void collectMoney();// 收钱
}

// 代理类
class RealStar implements Star {

    public void confer() {
    }

    public void signContract() {
    }

    public void bookTicket() {
    }

    public void sing() {
        System.out.println("明星：歌唱~~~");
    }

    public void collectMoney() {
    }
}

// 被代理类
class Proxy implements Star {
    private Star real;

    public Proxy(Star real) {
        this.real = real;
    }

    public void confer() {
        System.out.println("经纪人面谈");
    }

    public void signContract() {
        System.out.println("经纪人签合同");
    }

    public void bookTicket() {
        System.out.println("经纪人订票");
    }

    public void sing() {
        real.sing();
    }

    public void collectMoney() {
        System.out.println("经纪人收钱");
    }
}
```

应用场景：

* 安全代理：屏蔽对真实角色的直接访问；
* 远程代理：通过代理类处理远程方法调用（RMI）；
* 延迟加载：先加载轻量级的代理对象，真正需要再加载真实对象。

代理分类：

* 静态代理（静态定义代理类）；
* 动态代理（动态生成代理类）。

## 内部类

当一个事物的内部，还有一个部分需要一个完整的结构进行描述。而这个内部的完整的结构又只为外部事物提供服务，那么整个内部的完整结构最好使用内部类。

在 Java 中，允许一个类的定义位于另一个类的内部，前者称为内部类，后者称为外部类。

内部类的分类：

* 成员内部类（static 成员内部类 和 非 static 成员内部类）；
* 局部内部类（不谈修饰符，方法内、代码块内、构造器内）、匿名内部类。

**成员内部类作为类的成员**：

* 可以调用外部类的结构；
* 可以被 static 修饰；
* 可以被 4 中权限修饰符修饰。

**成员内部类作为类**：

* 可以在内部定义属性、方法、构造器等结构；
* 可以声明为 abstract 的 ，因此可以被其它的内部类继承；
* 可以声明为 final 的，即不能被其它类继承；
* 编译以后生成 OuterClass$InnerClass.class 字节码文件。

```java
class Person {
    // 静态成员内部类
    static class A {

    }

    // 非静态成员内部类
    class B {

    }

    Person() {
        class E {

        }
    }

    public void method() {
        class C {

        }
    }

    {
        class D {

        }
    }
}
```

**注意**：

* 如何实例化成员内部类的对象？[InnerClassTest1](../../code/src/com/parzulpan/java/ch04/InnerClassTest1.java)
* 如何在成员内部类中区分调用外部类的结构？[InnerClassTest1](../../code/src/com/parzulpan/java/ch04/InnerClassTest1.java)
* 开发中局部内部类的使用？[InnerClassTest2](../../code/src/com/parzulpan/java/ch04/InnerClassTest2.java)

## 练习和总结

---

**以下代码的运行情况？**

```java
interface A {
    int x = 0;
}
class B {
    int x = 1;
}
class C extends B implements A {
    public void pX() {
        // System.out.println(x);
        System.out.println(super.x);    // 更改 1
        System.out.println(A.x);    // 更改 0
    }
    public static void main(String[] args) {
        new C().pX();
    }
}
```

编译出错，x 属性不明确。

---

**以下代码的运行情况？**

```java
interface Playable {
    void play();
}

interface Bounceable {
    void play();
}

interface Rollable extends Playable, Bounceable {
    Ball ball = new Ball("PingPang");
}

class Ball implements Rollable {
    private String name;
    public String getName() {
        return name;
    }
    public Ball(String name) {
        this.name = name;
    }
    public void play() {
        ball = new Ball("Football");
        System.out.println(ball.getName());
    }
```

interface Rollable 里的 ball 是 全局常量 public static final，Ball 中的 play() 重新给 ball 赋值了。

---
