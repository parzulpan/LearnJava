# 枚举类与注解

## 枚举类的使用

当需要定义**一组常量**时，强烈建议使用枚举类。

枚举类的理解：类的对象只有有限个，确定的。

若枚举只有一个对象, 则可以作为一种单例模式的实现方式。

枚举类的属性：

* 枚举类对象的属性不应允许被改动, 所以应该使用 private final 修饰；
* 枚举类的使用 private final 修饰的属性应该在构造器中为其赋值；
* 若枚举类显式的定义了带参数的构造器, 则在列出枚举值时也必须对应的
传入参数。

枚举类的实现：

* JDK5 之前需要自定义枚举类
* JDK5 新增的 enum 关键字用于定义枚举类

### 自定义枚举类

```java
package parzulpan.com.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 自定义枚举类
 */

public class SeasonTest {
    public static void main(String[] args) {
        Season spring = Season.SPRING;
        System.out.println(spring);
    }
}

class Season {
    // 1. 对象如果有实例变量，应该声明为private final，并在构造器中初始化
    private final String SEASON_NAME;
    private final String SEASON_DESC;

    // 2. 私有化类的构造器，保证不能在类的外部创建其对象
    private Season(String SEASON_NAME, String SEASON_DESC) {
        this.SEASON_NAME = SEASON_NAME;
        this.SEASON_DESC = SEASON_DESC;
    }

    // 3. 在类的内部创建枚举类的实例，声明为：public static final
    public static final Season SPRING = new Season("春天", "春暖花开");
    public static final Season SUMMER = new Season("夏天", "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天", "秋高气爽");
    public static final Season WINTER = new Season("冬天", "白雪皑皑");

    // 获取枚举类对象的属性
    public String getSEASON_NAME() {
        return SEASON_NAME;
    }

    public String getSEASON_DESC() {
        return SEASON_DESC;
    }

    @Override
    public String toString() {
        return "Season{" +
                "SEASON_NAME='" + SEASON_NAME + '\'' +
                ", SEASON_DESC='" + SEASON_DESC + '\'' +
                '}';
    }
}
```

### 使用关键字 enum 定义枚举类

使用说明：

* 使用 enum 定义的枚举类默认继承了 java.lang.Enum类，因此不能再继承其他类；
* 枚举类的构造器只能使用 private 权限修饰符；
* 枚举类的所有实例必须在枚举类中显式列出（`, 分隔` 、`; 结尾`）。列出的实例系统会**自动添加** `public static final` 修饰；
* **必须在枚举类的第一行声明枚举类对象**。

主要方法：

* `values()`：返回枚举类型的对象数组。该方法可以很方便地遍历所有的
枚举值；
* `valueOf(String str)`：可以把一个字符串转为对应的枚举类对象。要求字符串必须是枚举类对象的“名字”。如不是，会有运行时异常：
 IllegalArgumentException；
* `toString()`：返回当前枚举类对象常量的名称。

实现接口的枚举类：

* 和普通 Java 类一样，枚举类可以实现一个或多个接口；
* 若每个枚举值在调用实现的接口方法呈现**相同**的行为方式，则只要统一实现该方法即可；
* 若需要每个枚举值在调用实现的接口方法呈现出**不同**的行为方式，则可以让每个枚举值分别来实现该方法

```java
package parzulpan.com.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc :
 */

public class SeasonEnumTest {
    public static void main(String[] args) {
        Season1 summer = Season1.SUMMER;
        System.out.println(summer); // SUMMER

        System.out.println(Season1.class.getSuperclass());  // class java.lang.Enum，说明枚举类默认继承 class java.lang.Enum

        System.out.println();
        Season1[] values = Season1.values();
        for (Season1 value : values) {
            System.out.println(value);
            value.show();
        }

        System.out.println();
        Thread.State[] values1 = Thread.State.values();
        for (Thread.State value: values1) {
            System.out.println(value);
        }

        System.out.println();
        try {
            Season1 winter = Season1.valueOf("WINTER");
            System.out.println(winter); // WINTER
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println();
        summer.show();
    }
}

interface Info {
    void show();
}

enum  Season1 implements Info{
    SPRING("春天", "春暖花开") {
        @Override
        public void show() {
            System.out.println("春天在哪里？");
        }
    },
    SUMMER("夏天", "夏日炎炎") {
        @Override
        public void show() {
            System.out.println("夏天在哪里？");
        }
    },
    AUTUMN("秋天", "秋高气爽") {
        @Override
        public void show() {
            System.out.println("秋天在哪里？");
        }
    },
    WINTER("冬天", "白雪皑皑") {
        @Override
        public void show() {
            System.out.println("冬天在哪里？");
        }
    };

    private final String SEASON_NAME;
    private final String SEASON_DESC;

    private Season1(String SEASON_NAME, String SEASON_DESC) {
        this.SEASON_NAME = SEASON_NAME;
        this.SEASON_DESC = SEASON_DESC;
    }

    public String getSEASON_NAME() {
        return SEASON_NAME;
    }

    public String getSEASON_DESC() {
        return SEASON_DESC;
    }

//    @Override
//    public void show() {
//        System.out.println("四季在哪里？");
//    }

//    @Override
//    public String toString() {
//        return "Season{" +
//                "SEASON_NAME='" + SEASON_NAME + '\'' +
//                ", SEASON_DESC='" + SEASON_DESC + '\'' +
//                '}';
//    }
}
```

## 注解的使用

### 注解的概述

从 JDK5 开始，Java 增加了对**元数据**（MetaData）的支持, 也就是**注解**（Annotation）。

Annotation 其实就是代码里的**特殊标记**，这些标记可以在**编译**、**类加载**、**运行时**被读取, 并执行相应的处理。

通过使用 Annotation，开发者可以在不改变原有逻辑的情况下, 在源文件中嵌入一些补充信息。代码分析工具、开发工具和部署工具可以通过这些补充信息进行验证或者进行部署。

Annotation 可以像**修饰符**一样被使用, 可用于修饰**包**、**类**、**构造器**、**方法**、**成员变量**、**参数**、**局部变量**的声明, 这些信息被保存在 Annotation 的 `name=value` 对中。

在 JavaSE 中，注解的使用目的比较简单，例如标记过时的功能、忽略警告等。在 JavaEE/Android 中注解占据了更重要的角色，例如用来配置应用程序的任何切面，代替 JavaEE 旧版中所遗留的繁冗代码和 XML 配置等。

**未来的开发模式都是基于注解的**。注解是一种趋势，一定程度上可以说：**框架 = 注解 + 反射 + 设计模式**。

### 常见的注解

使用 Annotation 时要在其前面增加 @ 符号, 并把该 Annotation 当成一个修饰符使用，用于修饰它支持的程序元素。

示例一：**生成文档相关的注解**

* `@author` 标明开发该类模块的作者，多个作者之间使用,分割
* `@version` 标明该类模块的版本
* `@see` 参考转向，也就是相关主题
* `@since` 从哪个版本开始增加的
* `@param` 对方法中某参数的说明，如果没有参数就不能写
* `@return` 对方法返回值的说明，如果方法的返回值类型是void就不能写
* `@exception` 对方法可能抛出的异常进行说明 ，如果方法没有用throws显式抛出的异常就不能写

示例二：**在编译时进行格式检查**

* `@Override` 限定重写父类方法, 该注解只能用于方法
* `@Deprecated` 用于表示所修饰的元素（类, 方法等）已过时。通常是因为所修饰的结构危险或存在更好的选择
* `@SuppressWarnings` 抑制编译器警告

示例三：**跟踪代码依赖性，实现替代配置文件功能**

* Servlet3.0 提供了注解，使得不再需要在 web.xml 文件中进行 Servlet 的部署
* Spring 框架中关于“事务”的管理

### 自定义注解

自定义注解参考 `@SuppressWarnings` 的实现。

实现步骤：

* 注解声明为 `@interface`，自动继承 java.lang.annotation.Annotataion 接口；
* Annotation 的成员变量在 Annotation 定义中以无参数方法的形式来声明。其方法名和返回值定义了该成员的名字和类型，称为**配置参数**；
* 可以在定义 Annotation 的成员变量时为其指定初始值, 指定成员变量的初始
值可使用 default 关键字；
* 没有成员定义的 Annotation 称为标记；包含成员变量的 Annotation 称为元数据 Annotation。

使用注解：

* 如果注解有成员，在使用注解时，需要指明成员的值；
* 自定义注解必须配上注解的信息处理流程（使用反射）才有意义。

```java

```

### JDK 中的元注解

JDK 的元 Annotation 用于修饰其他 Annotation 定义。

JDK5 提供了 4 个标准的 meta-annotation 类型，分别是：

* **@Retention** 只能用于修饰一个 Annotation 定义，用于指定该 Annotation 的**生命周期**，@Rentention 包含一个 **RetentionPolicy** 类型的成员变量，使用 @Rentention 时必须为该 value 成员变量指定值：
  * `RetentionPolicy.SOURCE`：在源文件中有效（即源文件保留），编译器直接丢弃这种策略的注释；
  * `RetentionPolicy.CLASS`：在 class 文件中有效（即 class 保留） ，当运行 Java 程序时, JVM 不会保留注解，这是**默认值**；
  * `RetentionPolicy.RUNTIME`：在运行时有效（即运行时保留），当运行 Java 程序时, JVM 会保留注释。程序可以**通过反射获取该注释**。
* **@Target** 用于修饰 Annotation 定义，用于指定被修饰的 Annotation 能用于修饰哪些程序元素。 @Target 也包含一个名为 value 的成员变量：
  * `TYPE`：用于描述；类、接口（包括注解类型）、enum
  * `PARAMETER`：用于描述参数
  * `PACKAGE`：用于描述包
  * `METHOD`：用于描述方法
  * `LOCAL_VARIABLE`：用于描述局部变量
  * `FIELD`：用于描述域
  * `CONSTRUCTOR`：用于描述构造器
* **@Documented** 用于指定被该元 Annotation 修饰的 Annotation 类将被 javadoc 工具提取成文档。默认情况下，javadoc是不包括注解的。注意：定义为 Documented 的注解**必须设置** Retention 值为 RUNTIME
* **@Inherited** 被它修饰的 Annotation 将具有继承性。如果某个类使用了被 @Inherited 修饰的 Annotation, 则其子类将自动具有该注解。

### 利用反射获取注解信息

JDK5 在 `java.lang.reflect` 包下新增了 AnnotatedElement 接口, 该接口代表程序中可以接受注解的程序元素。

当一个 Annotation 类型被定义为运行时 Annotation 后, 该注解才是运行时
可见, 当 class 文件被载入时保存在 class 文件中的 Annotation 才会被虚拟机读取。

程序可以调用 AnnotatedElement 对象的如下方法来访问 Annotation 信息：

* `getAnnotation()`
* `getAnnotations()`
* `getDeclareAnnotations()`
* `isAnnotationPresent()`

### JDK8 注解的新特性

Java8 对注解处理提供了两点改进：**可重复的注解**及**可用于类型的注解**。此外，反射也得到了加强，在 Java8 中能够得到方法参数的名称。这会简化标注在方法参数上的注解。

可重复注解：

* 在 MyAnnotataion 上 声明 `@Repeatabe`，成员值为 MyAnnotation.class；
* MyAnnotataion 的 Target 和 Retention 等元注解 和 MyAnnotations 相同。

类型注解：

* `ElementType.TYPE_PARAMETER` 表示该注解能写在类型变量的声明语
句中（如：泛型声明）；
* `ElementType.TYPE_USE` 表示该注解能写在使用类型的任何语句中。

## 练习和总结
