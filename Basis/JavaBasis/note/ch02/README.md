# Java 基本语法-变量与运算符

## 关键字和保留字

关键字定义：被 Java 语言赋予了特殊含义，用做专门用途的字符串（单词）。

关键字特点：关键字中所有字母都为小写。

* 用于定义数据类型：class、interface、enum、byte、short
、int、long、float、double、char、boolean、void
* 用于定义流程控制：if、else、switch、case、default
、while、do、for、break、continue、return
* 用于定义访问权限修饰符：private、protected、public
* 用于定义类，函数，变量修饰符：abstract、final、static、synchronized
* 用于定义类与类之间关系：extends、implements
* 用于定义建立实例及引用实例，判断实例：new、this、super、instanceof
* 用于异常处理：try、catch、finally、throw、throws
* 用于包：package、import
* 其他修饰符：native、strictfp、transient、volatile、assert
* *用于定义数据类型值的字面值：true、false、null

保留字：现有 Java 版本尚未使用，但以后版本可能会作为关键字使
用。自己命名标识符时要避免使用这些保留字。例如：goto 、const。

## 标识符

标识符：Java 对各种变量、方法和类等要素命名时使用的字符序列称为标识符。即**凡是自己可以起名字的地方都叫标识符**。

标识符规则：

* 由 26 个英文字母大小写，0-9 ，_ 或 $ 组成；
* 数字不可以开头；
* 不可以使用关键字和保留字，但能包含关键字和保留字；
* Java 中严格区分大小写，长度无限制；
* 标识符不能包含空格。

命名规范：

* 包名：多单词组成时，所有字母都小写：xxxyyyzzz；
* 类名、接口名：多单词组成时，所有单词的首字母大写：XxxYyyZzz；
* 变量名、方法名：多单词组成时，第一个单词首字母小写，第二个单词开始每个单词首字母大写：xxxYyyZzz；
* 常量名：所有字母都大写。多单词时，每个单词用下划线连接：XXX_YYY_ZZZ。

## 变量

变量的概念：

* 内存中的一块存储区域；
* 该区域的数据可以在同一类型范围内不断变化；
* 变量是程序中**最基本的存储单元**。包含**变量类型（即强类型）**、**变量名**和**存储的值**。

变量的作用：

* 用于在内存中保存数据

使用变量注意：

* Java 中每个变量必须**先声明（需赋值才能使用）**，**后使用**；
* 使用变量名来访问这块区域的数据；
* 变量的作用域：其定义所在的一对 { } 内；
* 变量只有在其作用域内才有效；
* 同一个作用域内，不能定义重名的变量。

对于每一种数据都定义了明确的具体数据类型（强类型语言），在内存中分
配了不同大小的内存空间。

* 数据类型：
  * 基本数据类型：
    * 整数类型：byte、short、int、long
    * 浮点类型：float、double
    * 字符类型：char
    * 布尔类型：boolean
  * 引用数据类型：
    * 类：class、String
    * 接口：interface
    * 数组：array

### 整数类型

| 类型 | 占用存储空间 | 表数范围 |
| :--- | :--- | :--- |
| byte | 1 byte(字节) = 8bit(位) | -128 ~ 127 |
| short | 2 字节 | -2^15 ~ 2^15-1 |
| int | 4 字节 | -2^31 ~ 2^31-1 (约21亿) |
| long | 8 字节 | -2^63 ~ 2^63-1 |

bit: 计算机中的最小存储单位。

byte:计算机中基本存储单元。

**Java 的整型常量默认为 int 型，声明 long 型常量须后加 ‘l’ 或 ‘L’**。

### 浮点类型

| 类型 | 占用存储空间 | 表数范围 |
| :--- | :--- | :--- |
| 单精度 float | 4 字节 | -3.403E38 ~ 3.403E38 |
| 双精度 double | 8 字节 | -1.798E308 ~ 1.798E308 |

**Java 的浮点型常量默认为 double 型，声明 float 型常量须后加 ‘f’ 或 ‘F’**。

### 字符类型

* char 型数据用来表示通常意义上“字符”(2字节)；
* 字符型变量的三种表现形式：
  * 字符常量是用单引号(‘ ’)括起来的单个字符。例如：char c1 = 'a'; char c2
= '中'; char c3 = '9';
  * Java中还允许使用转义字符（‘\’）来将其后的字符转变为特殊字符型常量。
例如：char c3 = ‘\n’; // '\n'表示换行符；
  * 直接使用 Unicode 值来表示字符型常量：‘\uXXXX’。其中，XXXX代表
一个十六进制整数。如：\u000a 表示 \n。
* char 类型是可以进行运算的，因为它都对应有 Unicode 码。

编码：

* ASCII：美国制定了一套字符编码，对英语字符与二进制位之间的关系，做了统一规定。它的缺点是：不能表示所有字符；相同的编码表示的字符不一样。
* Unicode：将世界上所有的符号都纳入其中。每一个符号都给予一个独一无二的编码，使用  Unicode 没有乱码的问题。它的缺点是：只规定了符号的二进制代码，却没有规定这个二进制代码
应该如何存储；并且是对于存储空间来说是极大的浪费。
* UTF-8：是一种变长的编码方式，它可以使用 1-6 个字节表示一个符号，根据
不同的符号而变化字节长度。

### 布尔类型

* boolean 类型用来判断逻辑条件，一般用于程序流程控制：
  * if 条件控制语句；
  * while 循环控制语句；
  * do-while 循环控制语句；
  * for 循环控制语句。
* boolean 类型数据只允许取值 true 和 false，无 null：
  * 不可以使用 0 或 非 0 的整数替代 false 和 true，这点和 C 语言不同。
  * Java 虚拟机中没有任何供 boolean 值专用的字节码指令，Java 语言表达所操作的
 boolean 值，在编译之后都使用 JVM 中的 int 数据类型来代替，即 true 用 1 表示，false 用 0 表示。

### 基本数据类型转换

自动类型转换：容量小的类型自动转换为容量大的数据类型。

数据类型按容量大小（**表示数的范围大小**）排序为：**char / byte / short -> int -> long -> float -> double**。

转换规则：

* 有多种类型的数据混合运算时，系统首先自动将所有数据转换成容量最大的那种数据类型，然后再进行计算；
* byte、short、char 之间不会相互转换，他们三者在计算时首先转换为 **int** 类型；
* boolean 类型不能与其它数据类型运算；
* 当把任何基本数据类型的值和字符串（String）进行连接运算时（+），基本数据类型的值将自动转化为字符串(String)类型。

### 强制类型转换

转换规则：

* 自动类型转换的逆过程，将容量大的数据类型转换为容量小的数据类型。使用时要加上强制转换符：()，但可能造成精度降低或溢出，格外要注意；
* 通常，字符串不能直接转换为基本类型，但通过基本类型对应的**包装类**则可以实现把字符串转换成基本类型；`String a = “43”; int i = Integer.parseInt(a);`
* boolean 类型不可以转换为其它的数据类型。

## 运算符

运算符是一种特殊的符号，用以表示数据的运算、赋值和比较等。包括：

* 算术运算符；
* 赋值运算符；
* 比较运算符（关系运算符）；
* 逻辑运算符；
* 位运算符；
* 三元运算符。

### 算术运算符

* 注意问题：
  * 如果对负数取模，可以把模数负号忽略不记，**但被模数是负数则不可忽略**。此外，取模运算的结果不一定总是整数。
  * 对于除号“/”，它的整数除和小数除是有区别的：整数之间做除法时，只保留整数部分而舍弃小数部分。
  * “+” 除字符串相加功能外，还能把非字符串转换成字符串。

### 赋值运算符

* 符号：=
  * 当“=”两侧数据类型不一致时，可以使用自动类型转换或使用强制类型转换原则进行处理。
  * 支持连续赋值。
* 扩展赋值运算符：+=, -=, *=, /=, %=。**它们不会改变数据类型**。

### 比较运算符

* 比较运算符的结果都是boolean型，也就是要么是true，要么是false。
* 比较运算符“==”不能误写成“=” 。

### 逻辑运算符

* `&` 逻辑与
* `|` 逻辑或
* `!` 逻辑非
* `&&` 短路与
* `||` 短路或
* `^` 逻辑异或

### 位运算符

位运算是直接对整数的二进制进行的运算

| 运算符 | 运算 | 范例 |
| :--- | :--- | :--- |
| `<<` | 左移。空位补0，被移除的高位丢弃，空缺位补0。 | `3 << 2 = 12 --> 3*2*2=12` |
| `>>` | 右移。被移位的二进制最高位是0，右移后，空缺位补0；最高位是1，空缺位补1。 | `3 >> 1 = 1 --> 3/2=1` |
| `>>>` | **无符号右移**。被移位二进制最高位无论是0或者是1，空缺位都用0补。 | `3 >>> 1 = 1 --> 3/2=1` |
| `&` | 与运算。二进制位进行&运算，只有1&1时结果是1，否则是0。 | `6 & 3 = 2` |
| `|` | 或运算 | `6 | 3 = 7` |
| `^` | 异或运算 | `6 ^ 3 = 5` |
| `~` | 取反运算 | `~6 = -7` |

### 三元运算符

**只有单目运算符、三元运算符、赋值运算符是从右向左运算的。**

## 练习和总结

---

**标识符的命名规则需要注意哪几点？**

标识符规则：

* 由 26 个英文字母大小写，0-9 ，_ 或 $ 组成；
* 数字不可以开头；
* 不可以使用关键字和保留字，但能包含关键字和保留字；
* Java 中严格区分大小写，长度无限制；
* 标识符不能包含空格。

---

**基本数据类型有哪几类？包含 String 吗？**

* 基本数据类型：
  * 整数类型：byte、short、int、long
  * 浮点类型：float、double
  * 字符类型：char
  * 布尔类型：boolean

String 是 引用数据类型。

---

**写出基本数据类型自动转化的流程图？**

char / short / byte -> int -> long -> float -> double

---

**整型默认的是什么类型，浮点型（实数型）默认的是什么类型？**

int; double;

---

**对于包名，类名接口名，变量名和函数名，常量名我们习惯如何格式来命名？**

命名规范：

* 包名：多单词组成时，所有字母都小写：xxxyyyzzz；
* 类名、接口名：多单词组成时，所有单词的首字母大写：XxxYyyZzz；
* 变量名、方法名：多单词组成时，第一个单词首字母小写，第二个单词开始每个单词首字母大写：xxxYyyZzz；
* 常量名：所有字母都大写。多单词时，每个单词用下划线连接：XXX_YYY_ZZZ。

---

**强制类型转化可能出现的问题？**

精度损失。

---

**char 型变量中能不能存储一个中文汉字？为什么？**

可以，因为 Java 一个字符是 2 个字节，每一个字符使用 Unicode 编码表示。

---

**定义 `float f = 3.4;` 是否正确？表达式 15/2*2 的值是多少？**

错误，应该为 `float f = 3.4F;`。14

---

**根据运算符的功能，我们把运算符分成哪几类？**

* 算术运算符；
* 赋值运算符；
* 比较运算符（关系运算符）；
* 逻辑运算符；
* 位运算符；
* 三元运算符。

---

**Java 有没有 goto？**

是 Java 中的保留字，现在没有在 Java 中使用。

---

**用最有效的的方法算出 2 称以 8 等于几？**

位运算，`2<<3`

---

**以下代码的运行结果**：

```java
public static void main(String[] args) {
    char x = 'x';
    int i = 10;
    System.out.println(true? x : i);    // 120
    System.out.println(true? 'x' : 10); // x

    int a = 8, b = 3;
    System.out.println(a>>>b);  // 
    System.out.println(a>>>b | 2);  // 
}
```

---

**为抵抗洪水，战士连续作战 89 小时，编程计算共多少天零多少小时？**

```java
package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 为抵抗洪水，战士连续作战 89 小时，编程计算共多少天零多少小时？
 */

public class GetDay {
    public static void main(String[] args) {
        int all = 89;
        int day = all / 24;
        int h = all % 24;
        System.out.println("共 " + day + " 天零 " + h + " 小时");
    }
}
```

---

**今天是周二，100 天以后是周几？**

```java
package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 今天是周二，100 天以后是周几？
 */

public class GetWeek {
    public static void main(String[] args) {
        int all = 100 + 2;
        int week = all % 7;
        System.out.println("今天是周二，100 天以后是周 " + week);
    }
}

```

---

**编写代码实现两个变量值交换？**

```java
package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 实现两个变量值交换？
 */

public class SwapTwoNum {
    public static void main(String[] args) {
        swapV1(324, 953);
        swapV2(324, 953);
        swapV3(324, 953);
    }

    public static void swapV1(int num1, int num2) {
        int temp = num1;
        num1 = num2;
        num2 = temp;
        System.out.println("num1 =  " + num1 + " num2 = " + num2);
    }

    public static void swapV2(int num1, int num2) {
        num1 = num1 + num2;
        num2 = num1 - num2;
        num1 = num1 - num2;
        System.out.println("num1 =  " + num1 + " num2 = " + num2);
    }

    public static void swapV3(int num1, int num2) {
        num1 = num1 ^ num2;
        num2 = num1 ^ num2;
        num1 = num1 ^ num2;
        System.out.println("num1 =  " + num1 + " num2 = " + num2);
    }
}
```

---

**如何求一个 0~255 范围内的整数的十六进制值，例如 60 的十六进制表示形式 3C ？**

```java
package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 如何求一个 0~255 范围内的整数的十六进制值，例如 60 的十六进制表示形式 3C ？
 */

public class GetHex {
    public static void main(String[] args) {
        //方式一：自动实现
        String str1 = Integer.toBinaryString(60);
        System.out.println(str1);   // 111100
        String str2 = Integer.toHexString(60);
        System.out.println(str2);   // 3c

        //方式二：手动实现
        int i1 = 60;
        int i2 = i1&15;
        String j = (i2 > 9)? (char)(i2-10 + 'A')+"" : i2+"";
        int temp = i1 >>> 4;
        i2 = temp & 15;
        String k = (i2 > 9)? (char)(i2-10 + 'A')+"" : i2+"";
        System.out.println(k+""+j); //3C
    }
}
```

---

**比较 + 与 += ？**

```java
short s1 = 1; s1 = s1 + 1;  // 编译失败，需要强制类型转换
short s1 = 1; s1 += 1;  // 正确，等价于自增操作（++s1），不会改变数据类型
```

---
