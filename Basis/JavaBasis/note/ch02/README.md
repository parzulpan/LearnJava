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
* 变量名、方法名：多单词组成时，第一个单词首字母小写，第二个单词开始每个
单词首字母大写：xxxYyyZzz；
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

## 运算符

## 程序流程控制
