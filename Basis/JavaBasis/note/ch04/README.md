# 面向对象上

这一章主要涉及 Java 类及类的成员，包括属性、方法、构造器；代码块、内部类。

## 面向过程与面向对象

* 面向过程（Procedure Oriented Programming，POP）与面向对象（Object Oriented Programming，OOP）：
  * 面向过程，强调的是功能行为，以函数为最小单位，考虑**怎么做**。面向对象，将功能封装进对象，强调具备了功能的对象，以类/对象为最小单位，考虑**谁来做**。
  * 面向对象更加强调运用人类在日常的思维逻辑中采用的思想方法与原则。

人把大象装进冰箱的例子：

```java
// 面向过程

1.打开冰箱
2.把大象装进冰箱
3.把冰箱门关住
```

```java
// 面向对象

人{
  打开（冰箱）{
    冰箱.开门();
  }

  操作(大象){
    大象.进入(冰箱);
  }

  关闭(冰箱){
    冰箱.关门();
  }
}

冰箱{
  开门(){ }

  关门(){ }
}

大象{
  进入(冰箱){ }
}
```

## 类和对象

面向对象的思想概述：

* 类(Class)和对象(Object)是面向对象的核心概念；
  * 类是对一类事物的描述，是抽象的、概念上的定义；
  * 对象是实际存在的该类事物的每个个体，因而也称为实例(instance)
* “万事万物皆对象”。

## 类和对象的创建和使用

使用步骤：

* 创建类，设计类的成员；
* 创建类的对象；
* 通过 “对象.属性” 或者 “对象.方法” 调用对象的结构。

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 对象的创建和使用
 */

public class AnimalTest {
    public static void main(String[] args) {
        Animal an = new Animal();   // 创建对象
        an.legs = 4;    // 访问属性
        System.out.println(an.legs);
        an.eat();   // 访问方法
        an.move();  // 访问方法
    }
}

// 设计类
class Animal {
    public int legs;

    public void eat() {
        System.out.println("Eating.");
    }

    public void move() {
        System.out.println("Move.");
    }

}
```

**内存解析：**

![对象的内存解析](../../resources/对象的内存解析.png)

* **堆（Heap）**，此内存区域的唯一目的就是**存放对象实例**，几乎所有的对象实例都在这里分配内存。这一点在 Java 虚拟机规范中的描述是：所有的对象实例以及数组都要在堆上分配。
* **栈（Stack）**，是指虚拟机栈，虚拟机栈用于**存放局部变量**等。局部变量表存放了编译期可知长度的各种基本数据类型（boolean、byte、char 、 short 、 int 、 float 、 long 、double）、对象引用（reference类型，它不等同于对象本身，是对象在堆内存的首地址）。 方法执行完，自动释放。
* **方法区（Method Area）**，用于存储已被虚拟机加载的**类信息**、**常量**、**静态变量**、**即时编译器编译后的代码**等数据。

**匿名对象：**

* 可以不定义对象的句柄，而直接调用这个对象的方法。这样的对象叫做匿名对象。
  * `new Person().eat();`;
* 使用情况：
  * 如果对一个对象只需要进行**一次方法**调用，那么就可以使用匿名对象；
  * 们经常将匿名对象作为实参传递给一个**方法调用**。

## 属性

属性即成员变量。

语法格式：`修饰符 数据类型 属性名 = 初始化值;`

* 说明1: 修饰符
  * 常用的权限修饰符有：private、缺省、protected、public；
  * 其他修饰符：static、final (暂不考虑)。
* 说明2：数据类型
  * 任何基本数据类型(如int、Boolean) 或 任何引用数据类型。
* 说明3：属性名
  * 属于标识符，符合命名规则和规范即可。

```java
public class Person{
  private int age; //声明private变量 age
  public String name = “Lila”; //声明public变量 name
}
```

变量的分类：

* 成员变量：在方法体外，类体内声明的变量
  * 实例变量（不以static修饰）；
  * 类变量（以static修饰）。
* 局部变量：在方法体内部声明的变量
  * 形参（方法、构造器中定义的变量）；
  * 方法局部变量（在方法内定义）；
  * 代码块局部变量（在代码块内定义）。

成员变量和局部变量的区别：

| 区别项 | 成员变量 | 局部变量 |
| :--- | :--- | :--- |
| 声明位置 | 直接声明在类中 | 方法形参或内部、代码块类、构造器内等 |
| 修饰符 | private、public、static、final等 | 不能用权限修饰符修饰，可以用final修饰 |
| 初始化值 | 有默认初始化值，同数组类似 | 没有默认初始化值，必须显式赋值，方可使用；特别的，形参在调用时赋值 |
| 内存加载位置 | 堆空间 或 静态域内 | 栈空间 |

## 方法

方法：它是类或对象行为特征的抽象，用来完成某个功能操作。在某些语言中也被成为函数或者过程。

* 将功能封装为方法的目的是，可以实现代码重用，简化代码；
* Java 里的**方法不能独立存在**，所有的方法必须定义在类里。

语法格式：

```java
修饰符 返回值类型 方法名（参数类型 形参1, 参数类型 形参2, ….）｛
  方法体程序代码
  return 返回值;
}
```

* 修饰符：public、缺省、private、protected 等
* 返回值类型：
  * 没有返回值：void
  * 有返回值，声明出返回值的类型。与方法体中 “return 返回值” 搭配使用
* 方法名：属于标识符，命名时遵循标识符命名规则和规范，“见名知意”
* 形参列表：可以包含零个，一个或多个参数。多个参数时，中间用 “,” 隔开
* 返回值：方法在执行完毕后返还给调用它的程序的数据。

## 理解“万事万物皆对象”

在 Java 语言范畴中，我们都将功能、结构等封装到类中，通过类的实例化，来调用具体的功能结构。

涉及到 Java 语言与前后端交互时，前后端的结构在 Java 层面交互时，都体现为类和对象。

## 方法的重载

重载：在同一个类中，允许存在一个以上的同名方法，只要它们的**参数个数或者参数类型不同**即可。

它与返回值类型无关，只看参数列表，且参数列表必须不同(参数个数或参数类型)。调用时，根据方法参数列表的不同来区别。

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 方法的重载
 */

public class OverrideTest {
    public static void main(String[] args) {
        System.out.println(max(1, 2));
        System.out.println(max(1.2, 2.1));
        System.out.println(max(1.2, 3.1, 0.1));

    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static double max(double a, double b) {
        return a > b ? a : b;
    }

    public static double max(double a, double b, double c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);
    }
}
```

## 方法的可变个数形参

JavaSE 5.0 中提供了 Varargs(variable number of arguments) 机制，允许直接定义能和多个实参相匹配的形参。从而，可以用一种更简单的方式，来传递个数可变的实参。

```java
//JDK 5.0以前：采用数组形参来定义方法，传入多个同一类型变量
public static void test(int a ,String[] books);

//JDK5.0：采用可变个数形参来定义方法，传入多个同一类型变量
public static void test(int a ,String … books);

// 二者不能共存
```

说明：

* 声明格式：`方法名(参数的类型名 ...参数名);`;
* 可变参数：方法参数部分指定类型的参数个数是可变多个：0个，1个或多个；
* 可变个数形参的方法与同名的方法之间，彼此构成重载；
* 可变参数方法的使用与方法参数部分使用数组是一致的；
* 方法的参数部分有可变形参，需要放在形参声明的最后；
* 在一个方法的形参位置，最多只能声明一个可变个数形参。

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 可变个数的参数
 */

public class VarargsTest {
    public static void main(String[] args) {
        Varargs varargs = new Varargs();
        varargs.test1();    // C
        varargs.test1("a", "b");    // C
        varargs.test1("cc");    // B
        varargs.test(new String[]{"aa"});  // A
    }
}

class Varargs {
    // A
    public void test(String[] msg) {
        System.out.println("含字符串数组参数的test方法!");
    }

    // B
    public void test1(String book) {
        System.out.println("与可变形参方法构成重载的test1方法!");
    }

    // C
    public void test1(String ... books) {
        System.out.println("形参长度可变的test1方法!");
    }
}

```

## 方法的参数传递

方法必须由其所在类或对象调用才有意义。若方法含有参数，则分为：

* 形参：方法声明时的参数；
* 实参：方法调用时实际传给形参的参数值。

**Java 里的方法的参数传递方式只有一种，即值传递**。将实际参数值的副本（复制品）传入方法内，而参数本身不受影响。

* 形参是**基本数据类型**：将实参基本数据类型变量的“**数据值**”传递给形参；
* 形参是**引用数据类型**：将实参引用数据类型变量的“**地址值（含变量的数据类型）**”传递给形参。

## 递归方法

## 练习和总结

---

**对象数组题目：
定义类Student，包含三个属性：学号number(int)，年级state(int)，成绩
score(int)。 创建20个学生对象，学号为1到20，年级和成绩都由随机数确定。
问题一：打印出3年级(state值为3）的学生信息。
问题二：使用冒泡排序按学生成绩排序，并遍历所有学生信息**

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 对象数组题目：
 * 定义类Student，包含三个属性：学号number(int)，年级state(int)，成绩
 * score(int)。 创建20个学生对象，学号为1到20，年级和成绩都由随机数确定。
 * 问题一：打印出3年级(state值为3）的学生信息。
 * 问题二：使用冒泡排序按学生成绩排序，并遍历所有学生信息
 */

public class Test {
    public static void main(String[] args) {
        Student[] students = new Student[20];

        //
        for (int i = 1; i < 21; ++i) {
            Student s = new Student();
            s.set(i, (int) (Math.random() * (6 - 1 + 1) + 1),(int) (Math.random() * (100 + 1)));
            students[i - 1] = s;
        }

        //
        for (Student student : students) {
            if (student.getState() == 3) {
                student.printInfo();
            }
        }

        System.out.println();

        //
        bubbleSort(students);
        for (Student student : students) {
                student.printInfo();
        }
    }

    public static void bubbleSort(Student[] s) {
        int len = s.length;
        for (int i = 0; i < len; ++i) {
            boolean flag = false;
            for (int j = 0; j < len - 1 - i; ++j) {
                if (s[j].getScore() > s[j + 1].getScore()) {
                    Student temp = s[j + 1];
                    s[j + 1] = s[j];
                    s[j] = temp;
                    flag = true;
                }
            }
            if(!flag) {
                break;
            }
        }
    }
}


class Student {

    private int number;
    private int state;
    private int score;

    public void set(int number, int state, int score) {
        this.number = number;
        this.state = state;
        this.score = score;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void printInfo() {
        System.out.println("学号：" + number +  " 年级：" + state + " 成绩：" + score);
    }
}
```

---

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 定义一个int型的数组：int[] arr = new int[]{12,3,3,34,56,77,432};
 * 让数组的每个位置上的值去除以首位置的元素，得到的结果，作为该位置上的新值。遍历新的数组。
 */

public class getArray {
    public static void main(String[] args) {
        int[] arr = new int[]{12, 3, 3, 34, 56, 77, 432};

        for (int i = arr.length  - 1; i >= 0; --i) {
            arr[i] /= arr[0];
        }

        for(int a : arr) {
            System.out.println(a);
        }
    }
}

```

---

**需要在 method 方法被调用之后，仅打印出 a = 100, b = 200，请写出 method 方法的代码？**

```java
package com.parzulpan.java.ch04;

import java.io.PrintStream;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : 某公司的笔试题，实际考察的是
 */

public class ChangeValue {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;
        method(a, b);   // 需要在 method 方法被调用之后，仅打印出 a = 100, b = 200，请写出 method 方法的代码
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }


    // 方法一：函数执行完退出
    public static void method(int a, int b) {
        a *= 10;
        b *= 20;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.exit(0);
    }

    // 方法二：重写
    public static void method(int a, int b) {
        PrintStream printStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                if ("a = 10".equals(x)) {
                    x = "a = 100";
                } else if ("b = 10".equals(x)) {
                    x = "b = 200";
                }
                super.println(x);
            }
        };

        System.setOut(printStream);
    }

}

```

---

```java

int[] arr = new int[10];
System.out.println(arr);  //地址值? 是

char[] arr1 = new char[10];
System.out.println(arr1); //地址值? 不是，调用的 println(char[])

```

---

**写出输出结果？**

```java
class Demo{
    public static void main(String[] args){
        show(0);
        show(1);
    }
    public static void show(int i){
        switch(i){
            default:
                i+=2;
            case 1:
                i+=1;
            case 4:
                i+=8;
            case 2:
                i+=4;
        }
        System.out.println("i=" + i);
    }
}
```

输出：

i = 15

i = 14

show(0) 从 default 开始，show(1) 从 case 1 开始。

---

**写出输出结果？**

```java
class Demo {
    public static void main(String[] args) {
        int x = 1;
        for (show('a'); show('b') && x < 3; show('c')) {
            show('d');
            x++;
        }
    }

    public static boolean show(char ch) {
        System.out.print(ch);
        return true;
    }
}
```

输出：

abdcbdcb

---

**说说 Java 的内存管理之垃圾回收？**

由 JVM 自动为其分配相应的内存空间，并由 JVM 提供垃圾回收机制自动释放内存空间。

将垃圾对象（不再被任何引用指向的对象）所占用的堆空间进行回收，Java 的垃圾回收机制是 JVM 提供的能力，由单独的系统垃圾回收线程在空闲时间以不定时的方式动态回收。

**在程序中是否可以通知垃圾回收机制过来回收垃圾？**

可以，通过 `System.gc();` 或者 `Runtime.getRuntime().gc();`

**调用后是否立即执行垃圾回收？**

不会，该调用并不会立刻启动垃圾回收机制，但会加快垃圾回收机制的运行。

---

**构造器是否可被 override ？**

构造器不能被继承，因此不能重写，但是可以被重载。

---
