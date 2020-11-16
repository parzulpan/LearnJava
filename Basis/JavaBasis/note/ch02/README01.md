# Java 基本语法-程序流程控制

## 程序流程控制

流程控制语句是用来控制程序中各语句执行顺序的语句，可以把语句组合成能完成一定功能的小逻辑模块。

其流程控制方式采用结构化程序设计中规定的三种基本流程结构，即：

* 顺序结构
* 分支结构
* 循环结构

### 顺序结构

Java中定义成员变量时采用合法的**前向引用**。如：

```java
public class Test{
    int num1 = 12;
    int num2 = num1 + 2;
}
```

### 分支结构

```java
package com.parzulpan.java.ch02;

import java.util.Scanner;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : if语句例题1
 * 岳小鹏参加Java考试，他和父亲岳不群达成承诺：
 * 如果：
 * 成绩为100分时，奖励一辆BMW；
 * 成绩为(80，99]时，奖励一台iphone xs max；
 * 当成绩为[60,80]时，奖励一个 iPad；
 * 其它时，什么奖励也没有。
 * 请从键盘输入岳小鹏的期末成绩，并加以判断。
 */

public class IfTest1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请从键盘输入岳小鹏的期末成绩：");
        int score = scanner.nextInt();  // 从键盘输入一个整数
        if(score == 100) {
            System.out.println("奖励一辆BMW");
        } else if(score <= 90 && score > 80) {
            System.out.println("奖励一台iphone xs max");
        } else if(score <= 80 && score >= 60) {
            System.out.println("奖励一个 iPad");
        } else {
            System.out.println("什么奖励也没有");
        }
    }

}
```

```java
package com.parzulpan.java.ch02;

import java.util.Scanner;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : if语句例题2
 * 假设你想开发一个玩彩票的游戏，程序随机地产生一个两位数的彩票，提示用户输入
 * 一个两位数，然后按照下面的规则判定用户是否能赢。
 * 1)如果用户输入的数匹配彩票的实际顺序，奖金10 000美元。
 * 2)如果用户输入的所有数字匹配彩票的所有数字，但顺序不一致，奖金 3 000美元。
 * 3)如果用户输入的一个数字仅满足顺序情况下匹配彩票的一个数字，奖金1 000美元。
 * 4)如果用户输入的一个数字仅满足非顺序情况下匹配彩票的一个数字，奖金500美元。
 * 5)如果用户输入的数字没有匹配任何一个数字，则彩票作废。
 */

public class IfTest2 {
    public static void main(String[] args) {
        int randomNumber = (int)(Math.random() * 90 + 10);  //  [0.0, 1.0) * 90 -> [0, 90) + 10 -> [10, 100) -> [10, 99]
        System.out.print("请输入一个两位数：");
        Scanner scanner = new Scanner(System.in);
        int inputNumber = scanner.nextInt();

        int randomNumberA = randomNumber / 10;
        int randomNumberB = randomNumber % 10;
        int inputNumberA = inputNumber / 10;
        int inputNumberB = inputNumber % 10;

        if (randomNumber == inputNumber) {
            System.out.println("奖金 10 000 美元");
        } else if (randomNumberA == inputNumberB && randomNumberB == inputNumberA) {
            System.out.println("奖金 3 000 美元");
        } else if (randomNumberA == inputNumberA || randomNumberB == inputNumberB) {
            System.out.println("奖金 1 000 美元");
        } else if (randomNumberA == inputNumberB || randomNumberB == inputNumberA) {
            System.out.println("奖金 500 美元");
        } else {
            System.out.println("没中奖");
        }

        System.out.println("中奖号码是：" + randomNumber);

    }
}

```

switch 语句有关规则：

* switch(表达式) 中表达式的值**必须**是下述几种类型之一：**byte，short，char，int，枚举 (jdk 5.0)，String (jdk 7.0)**；
* case 子句中的值必须是常量，不能是变量名或不确定的表达式值；
* 同一个 switch 语句，所有 case 子句中的常量值互不相同；
* break 语句用来在执行完一个 case 分支后使程序跳出 switch 语句块；如果没有 break，程序会顺序执行到 switch 结尾；
* default 子句是可任选的。同时，位置也是灵活的。当没有匹配的 case 时，执行 default。

```java
package com.parzulpan.java.ch02;

import java.util.Scanner;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : switch语句例题1
 * 使用 switch 把小写类型的 char 型转为大写。只转换 a, b, c, d, e，其它的输出 “other”。
 */

public class SwitchTest1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String word = scanner.next();
        char c = word.charAt(0);

        switch (c) {
            case 'a':
                System.out.println('A');
                break;
            case 'b':
                System.out.println('B');
                break;
            case 'c':
                System.out.println('C');
                break;
            case 'd':
                System.out.println('D');
                break;
            case 'e':
                System.out.println('E');
                break;
            default:
                System.out.println("other");
        }

    }
}

```

if 和 switch 的使用场景：

* 如果判断的具体数值不多，而且符合 byte、short 、char、int、String、枚举等几种类型。虽然两个语句都可以使用，建议使用 swtich 语句。因为效率稍高。
* 其他情况：对区间判断，对结果为 **boolean** 类型判断，使用 if，if 的使用范围更广。也就是说，使用 switch-case 的，都可以改写为 if-else。反之不成立。

### 循环结构

循环结构：在某些条件满足的情况下，反复执行特定代码的功能。

循环语句分类：

* for 循环
* while 循环
* do-while 循环

for 循环语法格式：

```java
for (①初始化部分; ②循环条件部分; ④迭代部分)｛
    ③循环体部分;
｝
```

```java
package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : for语句例题1
 * 输入两个正整数 m 和 n，求其最大公约数和最小公倍数。
 * 比如：12 和 20 的最大公约数是 4，最小公倍数是 60。
 */

public class ForTest1 {
    public static void main(String[] args) {
        int m = 12, n = 20;
        int max = Math.max(m, n);
        int min = Math.min(m, n);

        for (int i = min; i >= 1; --i) {
            if (m % i == 0 && n % i == 0) {
                System.out.println("最大公约数：" + i);
                break;
            }
        }

        for (int i = max; i <= m * n; ++i) {
            if (i % m == 0 && i % n == 0) {
                System.out.println("最小公倍数：" + i);
                break;
            }
        }
    }
}
```

while 循环语法格式：

```java
①初始化部分;
while(②循环条件部分)｛
    ③循环体部分;
    ④迭代部分;
}
```

## 练习和总结

---

**实现九九乘法表？**

```java
package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention :
 */

public class NineTable {
    public static void main(String[] args) {
        for (int i = 1; i <= 9; ++i) {
            for (int j = 1; j <= i; ++j) {
                System.out.print(i + " * " + j + " = " + (i * j) + "  ");
            }
            System.out.println();
        }
    }
}

```

---

**实现 100000 以内的所有质数？**

```java
package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 质数：只能被 1 和它本身整除的自然数。
 */

public class GetPrimeNumber {
    public static void main(String[] args) {
        int primeNumberCnt = 0;
        long start = System.currentTimeMillis();
        for (int i = 2; i <= 100000; ++i) {
            boolean isFlag = true;

            for (int j = 2; j < Math.sqrt(i); ++j) {    // 优化2：只对本身是质数的自然数是有效的
                if(i % j == 0) {
                    isFlag = false;
                    break;  // 优化1：只对本身是非质数的自然数是有效的
                }
            }

            if (isFlag) {
                ++primeNumberCnt;
//                System.out.println(i);
            }
        }

        System.out.println("primeNumberCnt: " + primeNumberCnt);
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }
}

```

---
