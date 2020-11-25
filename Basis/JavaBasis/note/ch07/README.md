# 常用类

## 字符串相关的类

String类：代表字符串，使用一对 `""` 引起来表示。

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];

    /** Cache the hash code for the string */
    private int hash; // Default to 0

    // ...
}
```

说明：

* String 声明为 final 的，不可被继承；
* String 实现了 Serializable 接口，表示字符串是支持序列化的；
* String 实现了 Comparable 接口，表示字符串是可以比较大小的；
* String 内部定义了 `final char[] value` 用于存储字符串数据；
* String 代表不可变的字符序列，简称**不可变**性。

```java
package com.parzulpan.java;

import org.junit.Test;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : String 的使用
 */

public class StringTest {
    public static void main(String[] args) {

    }

    // 理解不可变性
    @Test
    public void test1() {
        String s1 = "abc";  //  字面量的定义方式
        String s2 = "abc";

        System.out.println(s1 == s2);   // true，比较地址值，
        // 说明 通过字面量的方式（区别于 new）给一个字符串赋值，
        // 此时的字符串值声明在方法区的字符串常量池中，而常量池不会存储相同内容的字符串。

        s1 = "hello";
        System.out.println(s1 == s2);   // false，
        // 体现了不可变性的第一点，对字符串重新赋值时，需要重新指定内存区域赋值

        String s3 = s2 + "def";
        System.out.println(s3 == s2);   // false，
        // 体现了不可变性的第二点，对现有字符串进行连接操作时，需要重新指定内存区域赋值

        String s4 = "abc";
        String s5 = s4.replace('a', 'c');
        System.out.println(s4);
        System.out.println(s5);
        // 体现了不可变性的第三点，对现有字符串进行修改操作时，需要重新指定内存区域赋值
    }

    // String 对象的创建
    @Test
    public void test2() {
        String s1 = "Java"; // 字面量的定义方式，在方法区的字符串常量池中
        String s2 = "Java";

        String s3 = new String("Java"); // new + 构造器的定义方式，在堆空间中
        String s4 = new String("Java");

        System.out.println(s1 == s2);   // true
        System.out.println(s1 == s3);   // false
        System.out.println(s1 == s4);   // false
        System.out.println(s3 == s4);   // false

        System.out.println("---------");

        Person p1 = new Person("Tom", 12);
        Person p2 = new Person("Tom", 12);

        System.out.println(p1.name.equals(p2.name));    // true
        System.out.println(p1.name == p2.name); // true

        p1.name = "Jerry";
        System.out.println(p2.name);    // Tom，不可变性
    }

    // String 的特性
    @Test
    public void test3() {

        String s1 = "hello";
        String s2 = "world";
        String s3 = "hello" + "world";
        String s4 = s1 + "world";
        String s5 = s1 + s2;
        String s6 = (s1 + s2).intern();

        System.out.println(s3 == s4);   // false
        System.out.println(s3 == s5);   // false
        System.out.println(s4 == s5);   // false
        System.out.println(s3 == s6);   // true

        // 1. 常量和常量的拼接结果在常量池，且常量池不会存在相同内容的常量
        // 2. 只要其中有一个是变量，结果就在堆中
        // 3. 如果拼接的结果调用 intern() 方法，返回值就在常量池中

    }
}
```

### String 常用方法

常用方法：

* `int length()`：返回字符串的长度 `return value.length`
* `char charAt(int index)`： 返回某索引处的字符 `return value[index]`
* `boolean isEmpty()`：判断是否是空字符串 `return value.length == 0`
* `String toLowerCase()`：使用默认语言环境，将 String 中的所有字符转换为小写
* `String toUpperCase()`：使用默认语言环境，将 String 中的所有字符转换为大写
* `String trim()`：返回字符串的副本，忽略前导空白和尾部空白
* `boolean equals(Object obj)`：比较字符串的内容是否相同
* `boolean equalsIgnoreCase(String anotherString)`：与equals方法类似，忽略大小写
* `String concat(String str)`：将指定字符串连接到此字符串的结尾。 等价于用 “+”
* `int compareTo(String anotherString)`：比较两个字符串的大小
* `String substring(int beginIndex)`：返回一个新的字符串，它是此字符串的从 beginIndex 开始截取到最后的一个子字符串
* `String substring(int beginIndex, int endIndex)`：返回一个新字符串，它是此字符串从 beginIndex 开始截取到 endIndex（不包含）的一个子字符串
* `boolean endsWith(String suffix)`：测试此字符串是否以指定的后缀结束
* `boolean startsWith(String prefix)`：测试此字符串是否以指定的前缀开始
* `boolean startsWith(String prefix, int toffset)`：测试此字符串从指定索引开始的子字符串是否以指定前缀开始
* `boolean contains(CharSequence s)`：当且仅当此字符串包含指定的 char 值序列时，返回 true
* `int indexOf(String str)`：返回指定子字符串在此字符串中第一次出现处的索引，如果未找到则返回 -1
* `int indexOf(String str, int fromIndex)`：返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始
* `int lastIndexOf(String str)`：返回指定子字符串在此字符串中最右边出现处的索引，如果未找到则返回 -1
* `int lastIndexOf(String str, int fromIndex)`：返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索
* `String replace(char oldChar, char newChar)`：返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 得到的
* `String replace(CharSequence target, CharSequence replacement)`：使用指定的字面值替换序列替换此字符串所有匹配字面值目标序列的子字符串
* `String replaceAll(String regex, String replacement)`： 使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串
* `String replaceFirst(String regex, String replacement)`： 使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串
* `boolean matches(String regex)`：告知此字符串是否匹配给定的正则表达式
* `String[] split(String regex)`：根据给定正则表达式的匹配拆分此字符串
* `String[] split(String regex, int limit)`：根据匹配给定的正则表达式来拆分此字符串，最多不超过 limit 个，如果超过了，剩下的全部都放到最后一个元素中

```java
package com.parzulpan.java;

import org.junit.Test;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : String 常用方法
 */

public class StringMethodTest {

    @Test
    public void test1() {
        String s1 = "HelloWorld";
        System.out.println(s1.length());
        System.out.println(s1.charAt(0));
//        System.out.println(s1.charAt(10));  // StringIndexOutOfBoundsException

        System.out.println(s1.isEmpty());

        String s2 = s1.toLowerCase();
        String s3 = s1.toUpperCase();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

        String s4 = "  Hello World   ";
        String s5 = s4.trim();
        System.out.println("---" + s4 + "---");
        System.out.println("---" + s5 + "---");

        String s6 = "HelloWorld";
        String s7 = "helloworld";
        System.out.println(s6.equals(s7));
        System.out.println(s6.equalsIgnoreCase(s7));

        String s8 = "Hello";
        String s9 = s8.concat(" World");
        System.out.println(s9);

        String s10 = "abc";
        String s11 = new String("abe");
        System.out.println(s10.compareTo(s11)); // -2

        String s12 = "Beijing China";
        String s13 = s12.substring(1);
        String s14 = s12.substring(8, 13);
        System.out.println(s13);    // eijing China
        System.out.println(s14);    // China
    }

    @Test
    public void test2() {
        String s1 = "HelloWorld@!";

        boolean b1 = s1.endsWith("@!");
        System.out.println(b1);

        boolean b2 = s1.startsWith("He");
        System.out.println(b2);

        boolean b3 = s1.startsWith("ll", 2);
        System.out.println(b3);


        String s3 = "HelloWorldHelloWorld";
        System.out.println(s3.contains("ll"));

        System.out.println(s3.indexOf("ll"));   // 2
        System.out.println(s3.indexOf("AF"));   // -1
        System.out.println(s3.indexOf("ll",4));   // 12

        System.out.println(s3.lastIndexOf("ll"));   // 12
        System.out.println(s3.lastIndexOf("AF"));   // -1
        System.out.println(s3.lastIndexOf("ll", 14));   // 12
        System.out.println(s3.lastIndexOf("ll", 10));   // 2

        String s4 = s3.replace('W', '@');
        System.out.println(s4);

        String s5 = s3.replace("He", "@@");
        System.out.println(s5);
    }

    @Test
    public void test3() {
        String str = "12hello34world5java7891mysql456";
        String string = str.replaceAll("\\d+", ",").replaceAll("^,|,$", "");
        System.out.println(string);

        String str1 = "12345";
        boolean matches = str1.matches("\\d+");
        System.out.println(matches);

        String tel = "0571-4534289";
        boolean result = tel.matches("0571-\\d{7,8}");
        System.out.println(result);

        String str2 = "hello|world|java";
        String[] str4 = str2.split("\\|");
        for (int i = 0; i < str4.length; i++) {
            System.out.println(str4[i]);
        }

        System.out.println();

        String str3 = "hello.world.java";
        String[] strs3 = str3.split("\\.");
        for (int i = 0; i < strs3.length; i++) {
            System.out.println(strs3[i]);
        }

    }
}
```

### String 与 其他数据类型的相互转换

示例：

```java
package com.parzulpan.java;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : String 与 其他数据类型的相互转换
 */

public class StringTest1 {

    // String -> 基本数据类型、包装类
    @Test
    public void test1() {
        String s1 = "123";
//        int num = (int) s1; // ERROR
        int num = Integer.parseInt(s1);
        System.out.println(num);

        double num1 = Double.parseDouble(s1);
        System.out.println(num1);
    }

    // 基本数据类型、包装类 -> String
    @Test
    public void test2() {
        String s= "123";
        int num = 123;

        String s1 = String.valueOf(num);
        System.out.println(s1);
        String s2 = num + "";
        System.out.println(s2);

        System.out.println(s == s1);    // false
        System.out.println(s == s2);    // false
    }

    // String -> char[]
    @Test
    public void test3() {
        String s1 = "123abc";

        char[] charA = s1.toCharArray();
        for (int i = 0; i < charA.length; i++) {
            System.out.print(charA[i] + " ");   // 1 2 3 a b c
        }

        System.out.println();

        char[] charD = new char[10];
        s1.getChars(1, 4, charD, 3);
        for (int i = 0; i < charD.length; i++) {
            System.out.print(charD[i] + " ");   //       2 3 a        
        }
    }

    // char[] -> String
    @Test
    public void test4() {
        char[] chars = new char[] {'1', '2', '3', 'a', 'b', 'c'};

        String s1 = new String(chars);
        System.out.println(s1);

        String s2 = new String(chars,1, chars.length - 1 - 1);  // 23ab
        System.out.println(s2);
    }

    // String -> byte[]
    @Test
    public void test5() {
        String s1 = "123abc";

        byte[] bytes = s1.getBytes();
        System.out.println(Arrays.toString(bytes)); // [49, 50, 51, 97, 98, 99]

        System.out.println(Arrays.toString("中国".getBytes()));   // [-28, -72, -83, -27, -101, -67] UTF-8
        try {
            System.out.println(Arrays.toString("GBK".getBytes("GBK")));   // [71, 66, 75] GBK
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    // byte[] -> String
    @Test
    public void test6() {
        byte[] bytes = new byte[] {49, 50, 51, 97, 98, 99};

        String s1 = new String(bytes);
        System.out.println(s1); // 123abc

        byte[] bytes1 = new byte[] {-28, -72, -83, -27, -101, -67};
        String s2 = new String(bytes1, StandardCharsets.UTF_8);
        System.out.println(s2);    // 中国

        String s3 = new String(bytes1, 0, 3, StandardCharsets.UTF_8);
        System.out.println(s3);    // 中
    }
}
```

### StringBuffer & StringBuilder

源码分析：

```java
String str = new String();  // char[] value = new char[0];
String str1 = new String("abc");    // char[] value = new char[]{'a', 'b', 'c'}

StringBuffer sb1 = new StringBuffer();  // char[] value = new char[16]  底层创建了一个长度为 16 的字符数组
sb1.append('a');    // value[0] = 'a'
sb1.append('b');    // value[1] = 'b'

StringBuilder sb2 = new StringBuilder("abc");    // char[] value = new char["abc".length() + 16]

// 扩容问题
// 如果要添加的数据底层数组放不下了，那就需要扩容底层数组
// 默认情况下，扩容为原来容量的 2 倍 + 2，同时将原有数组的元素复制到新的数组中。

// 指导意义
// 开发中建议构造时就指定容量的字符串缓冲区
```

常用方法:

* `StringBuffer append(xxx)`：提供了很多的 append() 方法，用于进行字符串拼接
* `StringBuffer delete(int start,int end)`：删除指定位置的内容
* `StringBuffer replace(int start, int end, String str)`：把 `[start,end)` 位置替换为 str
* `StringBuffer insert(int offset, xxx)`：在指定位置插入 xxx
* `StringBuffer reverse()` ：把当前字符序列逆转

```java
package com.parzulpan.java;

import org.junit.Test;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc :
 */

public class StringBufferBuilderTest {
    public static void main(String[] args) {
        long startTime = 0L;
        long endTime = 0L;
        String text = "";
        StringBuffer buffer = new StringBuffer("");
        StringBuilder builder = new StringBuilder("");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            buffer.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuffer的执行时间：" + (endTime - startTime));   // 17

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            builder.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder的执行时间：" + (endTime - startTime));  // 9

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            text = text + i;
        }
        endTime = System.currentTimeMillis();
        System.out.println("String的执行时间：" + (endTime - startTime)); // 1873

        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.home"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.version"));
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void test1() {

        StringBuffer sb1 = new StringBuffer();  // char[] value = new char[16]  底层创建了一个长度为 16 的字符数组
        System.out.println(sb1.length());   // 0
        sb1.append('a');    // value[0] = 'a'
        sb1.append('b');    // value[1] = 'b'
        System.out.println(sb1.length());   // 2

        StringBuilder sb2 = new StringBuilder("abc");    // char[] value = new char["abc".length() + 16]
        System.out.println(sb2.length());   // 3
    }
}
```

## JDK8 之前的日期时间 API

**`java.lang.System` 类**：

* System 类提供的 `public static long currentTimeMillis()` 用来返回当前时间与 1970年1月1日0时0分0秒 之间以毫秒为单位的时间差。**此方法适于计算时间差**。

**`java.util.Date` 类**：

* 构造器：
  * `Date()`：使用无参构造器创建的对象可以获取本地当前时间。
  * `Date(long date)`：创建指定毫秒数的对象。
* 方法：
  * `getTime()`：返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
  * `toString()`：把此 Date 对象转换为以下形式的 String：`dow mon dd hh:mm:ss zzz yyyy` 其中，dow 是一周中的某一天 `(Sun, Mon, Tue, Wed, Thu, Fri, Sat)`，zzz 是时间标准。

**`java.spl.Date` 类**：

* 对应数据库中的日期类型的变量

**`java.text.SimpleDateFormat` 类**：

* 它是一个不与语言环境有关的方式来格式化和解析日期的具体类；
* 格式化：日期 -> 字符串：
  * `SimpleDateFormat()`：默认的模式和语言环境创建对象。
  * `public SimpleDateFormat(String pattern)`：该构造方法可以用参数 pattern。
  * `public String format(Date date)`：方法格式化时间对象。
指定的格式创建一个对象
* 解析：字符串 -> 日期：
  * `public Date parse(String source)`：从给定字符串的开始解析文本，以生成一个日期。

```java
package com.parzulpan.exer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 三天打鱼，两天晒网
 */

public class GetFish {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入年：");
            int year = scanner.nextInt();
            System.out.print("请输入月：");
            int month = scanner.nextInt();
            System.out.print("请输入日：");
            int day = scanner.nextInt();
            long dayDistance = getDayDistance("1990/1/1", year + "/" + month + "/" + day);
            if (dayDistance % 5 == 0 || dayDistance % 5 == 4) {
                System.out.println("晒网");
            } else {
                System.out.println("打鱼");
            }
        }

    }

    public static long getDayDistance(String time1, String time2) {
        long dayDistance = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date1 = simpleDateFormat.parse(time1);
            Date date2 = simpleDateFormat.parse(time2);
            dayDistance = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayDistance;
    }
}
```

**`java.util.Calendar` 类**：

* Calendar 是一个抽象基类，主用用于完成日期字段之间相互操作的功能。
* 获取 Calendar 实例的方法：
  * 使用 Calendar.getInstance() 方法。
  * 调用它的子类 GregorianCalendar 的构造器。
* **注意**：
  * 获取月份时：一月是 0，二月是 1，以此类推，十二月是 11
  * 获取星期时：周日是 1，周二是 2，以此类推，周六是 7

```java
package com.parzulpan.java;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : java.util.Calendar(日历)类
 */

public class CalenderTest {

    @Test
    public void test1() {
        // 实例化
        // 方式一
        Calendar calendar = Calendar.getInstance();
        // 方式二
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        // get()
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(dayOfMonth); // 24

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear);  // 329

        // set()
        calendar.set(Calendar.DAY_OF_YEAR, 330);
        int dayOfYear1 = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear1); //330

        // add()
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int dayOfYear2 = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear2); //331

        // getTime()
        Date date = calendar.getTime();
        System.out.println(date);   // Thu Nov 26 17:45:30 CST 2020

        // setTime()
        Date date1 = new Date();
        calendar.setTime(date1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(day);  // 24

    }
}
```

## JDK8 的日期时间 API

JDK1.0 中包含了一个 java.util.Date 类，但是它的大多数方法已经在 JDK1.1 引入 Calendar 类之后被弃用了。而 Calendar 并不比 Date 好多少。它们面临的**问题**是：

* 可变性：像日期和时间这样的类应该是不可变的。
* 偏移性：Date 中的年份是从 1900 开始的，而月份都从 0 开始。
* 格式化：格式化只对 Date 有用，Calendar 则不行。
* 它们也不是线程安全的，不能处理闰秒等。

Java 8 吸收了 **Joda-Time(开源项目)** 的精华，以一个新的开始为 Java 创建优秀的 API。新的 java.time 中包含了所有关于本地日期（LocalDate）、本地时间（LocalTime）、本地日期时间（LocalDateTime）、时区（ZonedDateTime）和持续时间（Duration）的类。

新时间日期 API：

* `java.time` 包含值对象的基础包
* `java.time.chrono` 提供对不同的日历系统的访问
* `java.time.format` 格式化和解析时间和日期
* `java.time.temporal` 包括底层框架和扩展特性
* `java.time.zone` 包含时区支持的类

**LocalDate、LocalTime、LocalDateTime** 类是其中较重要的几个类，它们的实例是**不可变的对象**，分别表示使用 ISO-8601 日历系统的日期、时间、日期和时间。它们提供了简单的本地日期或时间，并不包含当前的时间信息，也不包含与时区相关的信息。

**注**：ISO-8601日历系统是国际标准化组织制定的现代公民的日期和时间的表示法，也就是**公历**。

常用方法：

* `now()/now(ZoneId zone)` 静态方法，根据当前时间创建对象/指定时区的对象
* `of()` 静态方法，根据指定日期/时间创建对象
* `getDayOfMonth()/getDayOfYear()` 获得月份天数(1-31) /获得年份天数(1-366)
* `getDayOfWeek()` 获得星期几(返回一个 DayOfWeek 枚举值)
* `getMonth()` 获得月份, 返回一个 Month 枚举值
* `getMonthValue()/getYear()` 获得月份(1-12) /获得年份
* `getHour()/getMinute()/getSecond()` 获得当前对象对应的小时、分钟、秒
* `withDayOfMonth()/withDayOfYear()/withMonth()/withYear()` 将月份天数、年份天数、月份、年份修改为指定的值并返回新的对象
* `plusDays()/plusWeeks()/plusMonths()/plusYears()/plusHours()` 向当前对象添加几天、几周、几个月、几年、几小时
* `minusMonths()/minusWeeks()/minusDays()/minusYears()/minusHours()` 从当前对象减去几月、几周、几天、几年、几小时

### Instant 类

Instant：时间线上的一个瞬时点。 这可能被用来记录应用程序中的事件时间
戳。

在 UNIX 中，这个数从 1970 年开始，以秒为的单位；同样的，在 Java 中，
也是从 1970 年开始，但以毫秒为单位。

**时间戳是指格林威治时间 1970年01月01日00时00分00秒 （北京时间1970年01月01日08时00分00秒）起至现在的总秒数。**

常用方法：

* `now()` 静态方法，返回默认UTC时区的Instant类的对象
* `ofEpochMilli(long epochMilli)` 静态方法，返回在 1970-01-01 00:00:00 基础上加上指定毫秒数之后的 Instant 类的对象
* `atOffset(ZoneOffset offset)` 结合即时的偏移来创建一个  OffsetDateTime
* `toEpochMilli()` 返回 1970-01-01 00:00:00 到当前时间的毫秒数，即为时间戳

### DateTimeFormatter 类

java.time.format.DateTimeFormatter 类，该类提供了三种格式化方法：

* 预定义的标准格式。如：`ISO_LOCAL_DATE_TIME`; `ISO_LOCAL_DATE`; `ISO_LOCAL_TIME`
* 本地化相关的格式。如：`ofLocalizedDateTime(FormatStyle.LONG)`
* 自定义的格式。如：`ofPattern("yyyy-MM-dd hh:mm:ss")`

常用方法：

* `ofPattern(String pattern)` 静态方法， 返回一个指定字符串格式的 DateTimeFormatter
* `format(TemporalAccessor t)` 格式化一个日期、时间，返回字符串
* `parse(CharSequence text)` 将指定格式的字符序列解析为一个日期、时间

## Java 比较器

在 Java 中的对象，正常情况下，只能进行 `==` 或 `!=` 比较，不能使用 `<` 或者 `>`。但是实际开发中又需要！

Java 实现对象排序的方式有两种：

* 自然排序：`java.lang.Comparable` 接口
* 定制排序：`java.util.Comparator` 接口

### Comparable

像 String、包装类实现了 Comparable 接口，重写了 compareTo() 方法，可以进行两个对象大小的比较。

Comparable 接口强行对实现它的每个类的对象进行整体排序。这种排序被称
为类的**自然排序**。

实现 Comparable 的类必须实现 `compareTo(Object obj)` 方法，两个对象即通过 `compareTo(Object obj)` 方法的返回值来比较大小。

* 如果当前对象 this 大于形参对象 obj，则返回正整数，
* 如果当前对象 this 小于形参对象 obj，则返回负整数，
* 如果当前对象 this 等于形参对象 obj，则返回零。

```java
package com.parzulpan.java;

import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 自然排序：java.lang.Comparable
 */

public class ComparableTest {
    public static void main(String[] args) {
        Commodity[] commodities = new Commodity[5];
        commodities[0] = new Commodity("dellMouse", 45);
        commodities[1] = new Commodity("xiaomiMouse", 23);
        commodities[2] = new Commodity("huaweiMouse", 65);
        commodities[3] = new Commodity("snakeMouse", 25);
        commodities[4] = new Commodity("xxMouse", 25);
        Arrays.sort(commodities);
        System.out.println(Arrays.toString(commodities));

    }
}

class Commodity implements Comparable {
    private String name;
    private double price;

    public Commodity(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    // 指明商品比较大小的方式
    @Override
    public int compareTo(Object o) {
        if (o instanceof Commodity) {
            Commodity commodity = (Commodity) o;
            if (this.price > commodity.price) {
                return 1;
            } else if (this.price < commodity.price) {
                return -1;
            } else {
                return this.name.compareTo(commodity.name);
            }
//            return Double.compare(this.price, commodity.price);
        }

        throw new RuntimeException("传入的数据类型不一致");
    }
}
```

Comparable 的典型实现（默认都是从小到大排列的）：

* String：按照字符串中字符的 Unicode 值进行比较
* Character：按照字符的 Unicode 值来进行比较
* 数值类型对应的包装类以及 BigInteger、BigDecimal：按照它们对应的数值
大小进行比较
* Boolean：true 对应的包装类实例大于 false 对应的包装类实例
* Date、Time 等：后面的日期时间比前面的日期时间大

### Comparator

当元素的类型没有实现 java.lang.Comparable 接口而又不方便修改代码，
或者实现了 java.lang.Comparable 接口的排序规则不适合当前的操作，那
么可以考虑使用 Comparator 的对象来排序，强行对多个对象进行整体排
序的比较。

重写 `compare(Object o1,Object o2)` 方法，比较 o1 和 o2 的大小：

* 如果方法返回正整数，则表示 o1 大于 o2；
* 如果返回 0，表示相等；
* 返回负整数，表示 o1 小于 o2。

```java
package com.parzulpan.java;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc :
 */

public class ComparatorTest {
    public static void main(String[] args) {
        Commodity[] commodities = new Commodity[5];
        commodities[0] = new Commodity("dellMouse", 45);
        commodities[1] = new Commodity("xiaomiMouse", 23);
        commodities[2] = new Commodity("huaweiMouse", 65);
        commodities[3] = new Commodity("snakeMouse", 25);
        commodities[4] = new Commodity("xxMouse", 25);
        Arrays.sort(commodities, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Commodity && o2 instanceof Commodity) {
                    Commodity c1 = (Commodity)  o1;
                    Commodity c2 = (Commodity)  o2;
                    return -c1.compareTo(c2);
                }
                throw new RuntimeException("输入的数据类型不一致");
            }
        });
        System.out.println(Arrays.toString(commodities));

    }
}
```

## System 类

System 类代表系统，系统级的很多属性和控制方法都放置在该类的内部。该类位于java.lang 包（即不需要导包）。

由于该类的构造器是 private 的，所以无法创建该类的对象，也就是无法实
例化该类。其内部的成员变量和成员方法都是 static 的，所以也可以很方便
的进行调用。

成员变量：

* System 类内部包含 in、out 和 err 三个成员变量，分别代表标准输入流
(键盘输入)，标准输出流(显示器)和标准错误输出流(显示器)。

成员方法：

* `native long currentTimeMillis()`：该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和 GMT 时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数。
* `void exit(int status)`：该方法的作用是退出程序。其中 status 的值为 0 代表正常退出，非零代表异常退出。使用该方法可以在图形界面编程中实现程序的退出功能等。
* `void gc()`：该方法的作用是请求系统进行垃圾回收。至于系统是否立刻回收，则取决于系统中垃圾回收算法的实现以及系统执行时的情况。
* `String getProperty(String key)`：该方法的作用是获得系统中属性名为 key 的属性对应的值。

## Math 类

java.lang.Math 提供了一系列静态方法用于科学计算。其方法的参数和返回
值类型一般为 double 型。

成员方法：

* `abs` 绝对值
* `acos,asin,atan,cos,sin,tan` 三角函数
* `sqrt` 平方根
* `pow(double a,doble b)` a的b次幂
* `log` 自然对数
* `exp` e为底指数
* `max(double a,double b)`
* `min(double a,double b)`
* `random()` **返回 0.0 到 1.0 的随机数**
* `long round(double a)` double 型数据 a 转换为 long 型（四舍五入）
* `toDegrees(double angrad)` 弧度—>角度
* `toRadians(double angdeg)` 角度—>弧度

## BigInteger&BigDecimal

Integer 类作为 int 的包装类，能存储的最大整型值为 2^31-1，Long 类也是有限的，最大为 2^63-1。

`java.math.BigInteger` 类可以表示**不可变的任意精度的整数**。BigInteger 提供所有 Java 的基本整数操作符的对应物，并提供 java.lang.Math 的所有相关方法。另外，BigInteger 还提供以下运算：模算术、GCD 计算、质数测试、素数生成、位操作以及一些其他操作。

构造器:

* `BigInteger(String val)`：根据字符串构建 BigInteger 对象

常用方法：

* `public BigInteger abs()`：返回此 BigInteger 的绝对值的 BigInteger。
* `BigInteger add(BigInteger val)`：返回其值为 (this + val) 的 BigInteger
* `BigInteger subtract(BigInteger val)`：返回其值为 (this - val) 的 BigInteger
* `BigInteger multiply(BigInteger val)`：返回其值为 (this * val) 的 BigInteger
* `BigInteger divide(BigInteger val)`：返回其值为 (this / val) 的 BigInteger。整数相除只保留整数部分。
* `BigInteger remainder(BigInteger val)`：返回其值为 (this % val) 的 BigInteger。
* `BigInteger[] divideAndRemainder(BigInteger val)`：返回包含 (this / val) 后跟 (this % val) 的两个 BigInteger 的数组。
* `BigInteger pow(int exponent)`：返回其值为 (this^exponent) 的 BigInteger。

一般的 Float 类和 Double 类可以用来做科学计算或工程计算，但在商业计算中，**要求数字精度比较高**，故用到 java.math.BigDecimal 类。BigDecimal 类支持不可变的、任意精度的有符号十进制定点数。

构造器：

* public BigDecimal(double val)
* public BigDecimal(String val)

常用方法：

* `public BigDecimal add(BigDecimal augend)`
* `public BigDecimal subtract(BigDecimal subtrahend)`
* `public BigDecimal multiply(BigDecimal multiplicand)`
* `public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode)`

```java
public class BigIntegerBigDecimalTest {
    public static void main(String[] args) {
        BigInteger bigInteger1 = new BigInteger("1258917859013868315336326263475437543423623754374574547");
        BigInteger bigInteger2 = new BigInteger("8315336326263475437543423623754");
        System.out.println(bigInteger1);
        System.out.println(bigInteger2);
        System.out.println(bigInteger1.add(bigInteger2));

        BigDecimal bigDecimal1 = new BigDecimal("3264871935828081953801312516.325216432765437");
        BigDecimal bigDecimal2 = new BigDecimal("081953801312516.32");
        System.out.println(bigDecimal1);
        System.out.println(bigDecimal2);
        System.out.println(bigDecimal1.divide(bigDecimal2, BigDecimal.ROUND_HALF_UP));
        System.out.println(bigDecimal1.divide(bigDecimal2, 3, BigDecimal.ROUND_DOWN));
    }
}
```

## 练习和总结

---

**下列程序运行的结果？**

```java
package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 下列程序运行的结果？
 */

public class StringTest {
    String str = new String("good");
    char[] ch = { 't', 'e', 's', 't' };

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
    }

    public static void main(String[] args) {
        StringTest ex = new StringTest();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str + " and ");
        System.out.println(ex.ch);
    }
}
```

打印 `good and best`

---

**模拟一个 trim 方法，去除字符串两端的空格?**

```java
package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 模拟一个 trim 方法，去除字符串两端的空格?
 */

public class StringAlgorithm {
    public static void main(String[] args) {
        System.out.println(StringTrim(" fas kf sf  "));
    }

    public static String StringTrim(String s) {
        int start = 0;
        int end = s.length() - 1;

        while (start < end && s.charAt(start) == ' ') {
            ++start;
        }
        while (start < end && s.charAt(end) == ' ') {
            --end;
        }

        return s.substring(start, end + 1);
    }
}
```

---

**将一个字符串进行反转。将字符串中指定部分进行反转。比如 “abcdefg” 反转为 ”abfedcg”？
**

```java
package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 将一个字符串进行反转。将字符串中指定部分进行反转。比如 “abcdefg” 反转为 ”abfedcg”？
 */

public class StringAlgorithm1 {
    public static void main(String[] args) {
        System.out.println(subReverse("abcdefg", 2, 5));

    }

    public static String subReverse(String s, int start, int end) {
        char[] chars = s.toCharArray();
        for (int i = start, j = end; i < j;i++, j--) {
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }
}
```

---

**获取一个字符串在另一个字符串中出现的次数。比如：获取 “ab” 在 “abkkcadkabkebfkabkskab” 中出现的次数？**

```java
package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 获取一个字符串在另一个字符串中出现的次数。比如：获取 “ab” 在 “abkkcadkabkebfkabkskab” 中出现的次数？
 */

public class StringAlgorithm2 {
    public static void main(String[] args) {
        System.out.println(getCount("abkkcadkabkebfkabkskab", "ab"));
    }

    public static int getCount(String mainStr, String subStr) {
        if (mainStr.length() >= subStr.length()) {
            int count = 0;
            int index = 0;

            while ((index = mainStr.indexOf(subStr, index)) != -1) {
                count++;
                index += subStr.length();
            }

            return count;
        } else {
            return 0;
        }

    }
}
```

---

**获取两个字符串中最大相同子串。比如：str1 = "abcwerthelloyuiodef“; str2 = "cvhellobnm"; ？**

```java
package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 获取两个字符串中最大相同子串。比如：str1 = "abcwerthelloyuiodef“; str2 = "cvhellobnm"; ？
 */

public class StringAlgorithm3 {
    public static void main(String[] args) {
        System.out.println(getMaxSameSubStr("abcwerthelloyuiodef", "rthesfalloy"));
    }

    public static String getMaxSameSubStr(String s1, String s2) {
        if (s1.isEmpty() || s2.isEmpty()) {
            return null;
        }

        String maxStr = (s1.length() > s2.length()) ? s1 : s2;
        String minStr = (s1.length() > s2.length()) ? s2 : s1;

        int len = minStr.length();
        for (int i = 0; i < len; i++) {
            for (int x = 0, y = len - i; y < len; x++, y++) {
                if (maxStr.contains(minStr.substring(x, y + 1))) {
                    return minStr.substring(x, y + 1);
                }
            }
        }

        return null;
    }
}

```

---

**对字符串中字符进行自然顺序排序？**

```java
package com.parzulpan.exer;

import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 对字符串中字符进行自然顺序排序？
 */

public class StringAlgorithm4 {
    public static void main(String[] args) {
        System.out.println(sortStr("5432shtesdrhJGFIEj"));
    }

    public static String sortStr(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}

```

---

**对比String、StringBuffer、StringBuilder？**

* String（JDK1）：不可变字符序列；
* StringBuffer（JDK1）：可变字符序列，效率低，线程安全；
* StringBuilder（JDK5）：可变字符序列，效率高，线程不安全；
* 底层都是用 `char[] value` 存储。

---
