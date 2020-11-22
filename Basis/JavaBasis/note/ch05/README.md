# 异常处理

## 异常概述

在 Java 语言中，将程序执行中发生的不正常情况称为“异常”，但是开发过程中的语法错误和逻辑错误不是异常。

在执行过程中所发生的异常事件可分为两类：

* Error：JVM 无法解决的严重问题，一般不编写针对性的代码进行处理。例如：
  * StackOverflowError；
  * OutOfMemoryError
* Exception：它因编程错误或偶然的外在因素导致的一般性问题，可以使
用针对性的代码进行处理。例如：
  * 空指针访问；
  * 试图读取不存在的文件；
  * 网络连接中断；
  * 数组下标越界。

捕获错误最理想的是在**编译期间**，但有的错误只有在**运行时**才会发生。所以异常分为**编译时异常**和**运行时异常**。

编译时时异常：

* 是指编译器要求必须处置的异常。即程序在运行时由于外界因素造成的一般性异常。编译器要求Java程序必须捕获或声明所有编译时异常。
* 对于这类异常，如果程序不处理，可能会带来意想不到的结果。

运行时异常：

* 是指编译器不要求强制处置的异常。一般是指编程时的逻辑错误，是程序员应该积极避免其出现的异常。**`java.lang.RuntimeException`** 类及它的子类都是运行时异常。
* 对于这类异常，可以不作处理，因为这类异常很普遍，若全处理可能会对程序的可读性和运行效率产生影响。

## 常见异常

常见异常：

* **`java.lang.RuntimeException`**
  * ClassCastException；
  * ArrayIndexOutOfBoundsException；
  * NullPointerException；
  * ArithmeticException；
  * NumberFormatException；
  * InputMismatchException；
  * ...
* **`java.io.IOExeption`**
  * FileNotFoundException；
  * EOFException；
* **`java.lang.ClassNotFoundException`**；
* **`java.lang.InterruptedException`**；
* **`java.io.FileNotFoundException`**；
* **`java.sql.SQLException`**；

## try-catch-finally

在编写程序时，经常要在可能出现错误的地方加上检测的代码，可能出现过多的 if-else 分支会导致程序的代码加长、臃肿，可读性差。因此采用异常处理机制。

Java 采用的异常处理机制，是将异常处理的程序代码集中在一起，与正常的程序代码分开，使得程序简洁、优雅，并易于维护。

Java 异常处理的方式：

* try-catch-finally;
* throws + 异常类型。

Java 提供的是异常处理的**抓抛模型**。Java程序的执行过程中如果出现异常，会生成一个**异常类对象**，该异常对象将被提交给 Java 运行时系统，这个过程称为**抛出（throw）异常**。如果一个方法内抛出异常，该异常对象会被抛给调用者方法中处理。如果异常没有在调用者方法中处理，它继续被抛给这个调用方法的上层方法。这个过程将一直继续下去，直到异常被处理。这个过程称为**捕获（catch）异常**。

其中，“抛”，可以理解为异常对象的产生，有 **1. 系统自动生成的异常对象** 和 **2. 手动的生成的一个异常对象，并 throw**。

其中，“抓”，可以理解为异常的处理方式，有 **1. try-catch-finally** 和 **2. throws** 。

异常对象的生成：

* 由虚拟机**自动生成**：程序运行过程中，虚拟机检测到程序发生了问题，如果在当前代码中没有找到相应的处理程序，就会在后台自动创建一个对应异常类的实例对象并抛出 —— **自动抛出**。
* 由开发人员**手动创建**：`Exception exception = new ClassCastException();` —— 创建好的异常对象不抛出对程序没有任何影响，和创建一个普通对象一样。

与其它对象一样，可以访问一个异常对象的成员变量或调用它的方法：

* `getMessage()` 获取异常信息，返回字符串；
* `printStackTrace()` 获取异常类名和异常信息，以及异常出现在程序中的位置，返回值 void。

语法格式：

```java
try {
    // 可能产生异常的代码
} catch (ExceptionName1 e) {
    // 可选的，处理ExceptionName1型异常
} catch (ExceptionName2 e) {
    // 可选的，处理ExceptionName2型异常
} [ finally {
    // 可选的，无论是否发生异常，都无条件执行的语句
}]
```

**注意**：

* catch 中的异常类型如果没有子父类关系，则谁声明在上，谁声明在下无所谓。如果有子父类关系，则要求子类一定声明在父类的上面，否则报错。
* 在 try 结构中声明的变量，在出了 try 结构以后，就不能再被调用。
* 像数据库连接、输入输出流、网络编程 Socket 等资源，JVM 是不能自动回收的，需要自己手动的进行资源的释放，此时的资源释放，就需要声明在 finally 中;

```java
package com.parzulpan.java.ch04;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 异常处理机制：try-catch-finally
 */

public class ExceptionTest {
    public static void main(String[] args) {
        // 最初版
//        try {
//            File file = new File("hello.txt");
//            FileInputStream fileInputStream = new FileInputStream(file);
//
//            int data = fileInputStream.read();
//            while (data != -1) {
//                System.out.println((char) data);
//                data = fileInputStream.read();
//            }
//            fileInputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 改进版
        FileInputStream fileInputStream = null;
        try {
            File file = new File("hello.txt");
            fileInputStream = new FileInputStream(file);

            int data = fileInputStream.read();
            while (data != -1) {
                System.out.println((char) data);
                data = fileInputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

```

**不捕获异常时的情况**：

* 对于运行时异常，例如 RuntimeException 类或是它的子类等，这些类的异常的特点是：即使没有使用 try 和 catch 捕获，Java 自己也能捕获，并且编译通过，但运行时会发生异常使得程序运行终止。
* 对于编译时异常，例如 是IOException 等，则必须捕获，否则编译错误。
* 必须处理编译时异常，将异常进行捕捉，转化为运行时异常。

## throws

如果一个方法（中的语句执行时）可能生成某种异常，但是并不能确定如何处理这
种异常，则此方法应**显式地**声明抛出异常，表明该方法将不对这些异常进行处理，而由该方法的**调用者**负责处理。

在方法声明中用 throws 语句可以声明抛出异常的列表，throws 后面的异常类型可以是方法中产生的异常类型，也可以是它的父类。

```java
package com.parzulpan.java.ch04;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 异常处理机制：throws + 异常类型
 */

public class ExceptionTest1 {
    public static void main(String[] args) {

        try {
            method();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void method() throws IOException {
        File file = new File("hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        int data = fileInputStream.read();
        while (data != -1) {
            System.out.println((char) data);
            data = fileInputStream.read();
        }

        fileInputStream.close();
    }
}
```

**注意**：try-catch-finally 是真正的将异常给处理掉了，而 throws 的方式只是将异常抛给了方法的调用者，并没有真正的将异常处理掉。

**重写方法声明抛出异常的原则**：

* 重写方法不能抛出比被重写方法范围更大的异常类型。这是因为多态性存在的缘故。

**try-catch-finally 和 throws 的选择**：

* 如果父类中被重写的方法没有 throws 处理异常，则子类重写的方法也不能使用 throws。意味着如果子类重写的方法中有异常，必须使用 try-catch-finally 处理异常。
* 执行的方法 A 中，先后又调用另外几个方法，这几个方法是递进关系执行的，建议这几个方法使用 throws 处理异常，而 方法 A 可以使用 try-catch-finally 处理异常。

## throw

Java 异常类对象除在程序执行过程中出现异常时由系统自动生成并抛出，也可根据需要使用人工创建并抛出。

首先要生成异常类对象，然后通过 throw 语句实现抛出操作（提交给 Java 运行环境），可以抛出的异常必须是 Throwable 或其子类的实例：

  ```java
  IOException e = new IOException();
  throw e;
  ```

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 手动抛出异常
 */

public class ExceptionTest2 {
    public static void main(String[] args) {
//        StudentAA studentAA = new StudentAA();
//        studentAA.add(-10);
//        System.out.println(studentAA);  // 请输入的数据非法！StudentAA{id=0}
        try {
            StudentAA studentAA = new StudentAA();
            studentAA.add(-10);
            System.out.println(studentAA);  // 请输入的数据非法！
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

class StudentAA {
    private int id;

    public void add(int id) throws Exception{
        if (id > 0) {
            this.id = id;
        } else {
//            System.out.println("请输入的数据非法！");
            // 手动抛出异常
            throw new Exception("请输入的数据非法！");
        }
    }

    @Override
    public String toString() {
        return "StudentAA{" +
                "id=" + id +
                '}';
    }
}
```

## 自定义异常类

**注意**：

* 一般地，用户自定义异常类都是 RuntimeException/Exception 的子类；
* 自定义异常类通常需要编写几个重载的构造器；
* 自定义异常需要提供 serialVersionUID；
* 自定义的异常通过 throw 抛出；
* 自定义异常最重要的是异常类的名字，当异常出现时，可以根据名字判断异常类型。

```java
package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 用户自定义异常类
 */

public class MyExceptionTest {
    public static void main(String[] args) {
        MyExceptionTest myExceptionTest = new MyExceptionTest();
        myExceptionTest.manager();
    }

    public void add(int num) throws MyException{
        if (num < 0) {
            throw new MyException("人数为负值，不合理", 3);
        } else {
            System.out.println("登记人数 " + num);
        }
    }

    public void manager() {
        try {
            add(-10);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            System.out.println("登记失败，出错种类 " + e.getId());
        }

        System.out.println("本次登记操作结束");
    }
}


class MyException extends Exception {
    static final long serialVersionUID = 2165326351631265L;
    private int id;

    public MyException(String msg, int id) {
        super(msg);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
```

## 练习和总结

---

**判断程序的输出结果？**

```java
public class ReturnExceptionDemo {
    static void methodA() {
        try {
            System.out.println("进入方法A");    // 1
            throw new RuntimeException("制造异常"); // 2
        } finally {
            System.out.println("用A方法的finally");     // 3
        }
    }

    static void methodB() {
        try {
            System.out.println("进入方法B");    // 4
            return;
        } finally {
            System.out.println("调用B方法的finally");   // 5
        }
    }

    public static void main(String[] args) {
        try {
            methodA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        methodB();
    }
}
```

输出：1 -> 3 -> 2 -> 4 -> 5

---
