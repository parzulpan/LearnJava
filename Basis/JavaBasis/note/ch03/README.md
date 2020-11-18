# Eclipse 和数组

## Eclipse 安装和使用

...

## 数组的概述

数组（Array）：是多个相同类型数据按一定顺序排列的集合，并使用一个名字命名，并通过编号的方式对这些数据进行统一管理。

数组相关的概念：

* 数组名；
* 元素；
* 下标或索引；
* 数组的长度。

数组的特点：

* 数组是有序排列的；
* 数组属于引用数据类型的变量，但是数组的元素可以是任何数组类型；
* 创建数组对象会在内存中开辟一整块连续的空间；
* 数组的长度一旦确定，就不能修改。

数组的分类：

* 按维数：一维、二维、...；
* 按类型：基本数据类型的数组、引用数据类型的数组。

## 一维数组的使用

声明：

* 声明方式：`type var[];`、`type[] var;`；
* 声明数组时不能指定其长度，例如 `int a[5];` 就非法。

初始化：

* 动态初始化：数组声明且为数组元素分配空间 与 赋值的操作 分开进行；

  ```java
  String[] names = new String[5];
  names[0] = "A1";
  names[1] = "A2";
  names[2] = "A3";
  ```

* 静态初始化：数组声明且为数组元素分配空间 与 赋值的操作 同时进行；

  ```java
  String[] names = {"AA", "BB", "CC"};
  // 或者
  String[] names = new String[]{"AA", "BB", "CC"};
  ```

数组元素的引用：

* 定义并用运算符 new 为之分配空间后，才可以引用数组中的每个元素；
* 数组元素的引用方式：`数组名[数组元素下标]`；
* 每个数组都有一个属性 length 指明它的长度，数组一旦初始化，其长度是不可变的。

数组元素的默认初始值：

* 因为数组是**引用类型**，它的元素相当于类的成员变量，因此数组一经分配空间，其中的每个也被按照成员变量同样的方式被**隐式初始化**；

  ```java
  public class Test{
      public static void main(String[] args) {
          int[] a= new int[5];
          for (int i = 0; i < a.length; ++i) {
              System.out.println(a[i]); // 0 0 0 0 0
          }
      }
  }

  ```

* 对于基本数据类型而言，byte、short、int、long、float、double、char 均为 0，boolean 为 false；
* 对于引用数据类型而言，默认初始化值为 null。

## 一维数组的内存解析

内存的典型结构：

* 栈（stack）：局部变量；
* 堆（heap）：new 出来的结构；
* 方法区：
  * 常量池；
  * 静态域。

```java
package com.parzulpan.java.ch03;

import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : 从键盘读入学生成绩，找出最高分，并输出学生成绩等级。
 * 成绩>=最高分-10 等级为’A’
 * 成绩>=最高分-20 等级为’B’
 * 成绩>=最高分-30 等级为’C’
 * 其余 等级为’D’
 */

public class ArrayTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入学生人数：");
        int studentNumber = scanner.nextInt();
        int[] studentScores = new int[studentNumber];
        int maxScore = 0;
        int temp;
        System.out.println("请输入 " + studentNumber + " 个成绩：");
        for (int i = 0; i < studentScores.length; ++i) {
            temp = scanner.nextInt();
            studentScores[i] = temp;
            if (maxScore < temp) {
                maxScore = temp;
            }
        }

        System.out.println("最高分是：" + maxScore);

        char level;
        for (int i = 0; i < studentScores.length; ++i) {
            if (maxScore - 10 <= studentScores[i]) {
                level = 'A';
            } else if (maxScore - 20 <= studentScores[i]) {
                level = 'B';
            } else if (maxScore - 30 <= studentScores[i]) {
                level = 'C';
            } else {
                level = 'D';
            }

            System.out.println("student " + i + " , score is " + studentScores[i] + " , grade is " + level);
        }

    }
}

```

## 二维数组的使用

多维数组：

* Java 语言里提供了支持多维数组的语法；
* 可以把一维数组当成几何中的线性图形，那么二维数组就相当于是一个表格；
* 可以看成是一维数组 array1 又作为另一个一维数组 array2 的元素而存在。其实，**从数组底层的运行机制来看，其实没有多维数组**。

初始化：

* 动态初始化：

  ```java
  int[][] arr = new int[3][2];  // 二维数组中有 3 个一维数组，每一个一维数组中有 2 个元素

  int[][] arr = new int[3][];   // 二维数组中有 3 个一维数组，每一个一维数组都是默认初始值 null
  ```

* 静态初始化：
  
  ```java
  int[][] arr = new int[][]{{3,8,2},{2,7},{9,0,1,6}};
  // 或者
  int[][] arr = {{3,8,2},{2,7},{9,0,1,6}};  // 类型推断
  ```

* 特殊的，`int[] x, y[];` x 是一维数组，y 是二维数组；
* Java 中多维数组不必都是**规则矩阵**形式。

数组元素的默认初始值：

* 看程序得结论：
  
  ```java
  public class Test {
    public static void main(String[] args) {
        int[][] arr = new int[4][3];
        System.out.println(arr);    // 地址：[[I@2ff4f00f
        System.out.println(arr[0]);   // 地址：[I@2ff4f00f
        System.out.println(arr[0][0]);    // 0

        String[][] arr2 = new String[4][2];
        System.out.println(arr2);    // 地址：[[Ljava.lang.String;@3f0ee7cb
        System.out.println(arr2[0]);   // 地址：[Ljava.lang.String;@75bd9247
        System.out.println(arr2[0][0]);    // null

        String[][] arr3 = new String[4][];
        System.out.println(arr3);    // 地址：[[Ljava.lang.String;@7d417077
        System.out.println(arr3[0]);   // null
        System.out.println(arr3[0][0]);    // Error
    }
  }
  ```

## 二维数组的内存解析

...

## 练习和总结

---

**创建一个长度为 6 的 int 型数组，要求数组元素的值都在 1-30 之间，且是随机赋值。同时，要求元素的值各不相同。**

```java
package com.parzulpan.java.ch03;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : 创建一个长度为 6 的 int 型数组，要求数组元素的值都在 1-30 之间，且是随机赋值。同时，要求元素的值各不相同。
 */

public class genArray {
    public static void main(String[] args) {
        int[] arr = new int[6];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int)(Math.random() * 30) + 1;

            for (int j = 0; j < i; ++j) {
                if (arr[i] == arr[j]) {
                    --i;
                    break;
                }
            }
         }

        for (int value : arr) {
            System.out.println(value);
        }
    }
}
```

---

