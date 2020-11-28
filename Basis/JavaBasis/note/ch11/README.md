# IO 流

## File 类

java.io.File 类是文件和文件目录路径的抽象表示形式，与平台无关。

File 能新建、删除、重命名文件和目录，但 File 不能访问文件内容本身。
如果需要访问文件内容本身，则需要使用输入输出流。**File 对象可以作为参数传递给流的构造器**。

Java 程序支持跨平台运行，因此**路径分隔符**要慎用，为了解决这个隐患，File 类提供了一个常量，即 `public static final String separator`，它能根据操作系统，动态的提供分隔符。

常用构造器：

* `public File(String pathname)` 以 pathname 为路径创建 File 对象，可以是绝对路径或者相对路径，如果 pathname 是相对路径，则相对于当前 module；
* `public File(String parent,String child)` 以 parent 为父路径，child 为子路径创建 File 对象；
* `public File(File parent,String child)` 根据一个父 File 对象和子文件路径创建 File 对象；

常用方法：

* 获取功能：
  * `public String getAbsolutePath()`：获取绝对路径
  * `public String getPath()`：获取路径
  * `public String getName()`：获取名称
  * `public String getParent()`：获取上层文件目录路径。若无，返回 null
  * `public long length()`：获取文件长度，即字节数，不能获取目录的长度
  * `public long lastModified()`：获取最后一次的修改时间，毫秒值
* 重命名功能：
  * `public boolean renameTo(File dest)`：把文件重命名为指定的文件路径，要求 srcFile 存在且 destFile 不能存在
* 判断功能
  * `public boolean isDirectory()`：判断是否是文件目录
  * `public boolean isFile()`：判断是否是文件
  * `public boolean exists()`：判断文件是否存在
  * `public boolean canRead()`：判断文件是否可读
  * `public boolean canWrite()`：判断文件是否可写
  * `public boolean isHidden()`：判断文件是否隐藏
* 创建功能
  * `public boolean createNewFile()`：创建文件。若文件存在，则不创建，返回 false。
  * `public boolean mkdir()`：创建文件目录。如果此文件目录存在，则不创建。如果此文件目录的上层目录不存在，也不创建。
  * `public boolean mkdirs()`：创建文件目录。如果上层文件目录不存在，一并创建。
* 删除功能
  * `public boolean delete()`：删除文件或者文件夹，要删除一个文件目录，要求该文件目录内不能包含文件或者文件目录。

```java
package parzulpan.com.java;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : File 类的使用
 */

public class FileTest {

    @Test
    public void test1(){

        File file1 = new File("hello.txt");  // 相对于当前 module
        File file2 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code/ch11" + File.separator + "hi.txt");

        System.out.println(file1.getAbsolutePath());
        System.out.println(file1.getPath());
        System.out.println(file1.getName());
        System.out.println(file1.length());
        System.out.println(new Date(file1.lastModified()));

        System.out.println();

        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.getPath());
        System.out.println(file2.getName());
        System.out.println(file2.length());
        System.out.println(file2.lastModified());

        System.out.println();

        File file3 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code");
        String[] list = file3.list();
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println();

        File[] files = file3.listFiles();
        for (File f: files) {
            System.out.println(f);
        }
    }

    @Test
    public void test2() {
        File file1 = new File("hello.txt");  // 相对于当前 module
        File file2 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code" + File.separator + "hi.txt");
        File file3 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code");

        boolean b = file1.renameTo(file2);
        System.out.println(b);

        System.out.println();
        System.out.println(file3.isDirectory());
        System.out.println(file1.isFile());
        System.out.println(file1.exists());
        System.out.println(file3.exists());
        System.out.println(file1.canRead());
        System.out.println(file1.canWrite());
        System.out.println(file1.canExecute());
        System.out.println(file1.isHidden());
    }

    @Test
    public void test3() {
        File file1 = new File("helloW.txt");  // 相对于当前 module
        if (!file1.exists()) {
            try {
                boolean newFile = file1.createNewFile();
                System.out.println("创建文件 " + newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            boolean delete = file1.delete();
            System.out.println("删除文件 " + delete);
        }

        File file2 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code/newDir");
        if (!file2.exists()) {
            boolean mkdirs = file2.mkdirs();
            System.out.println("创建文件夹 " + mkdirs);
        } else {
            boolean delete = file2.delete();
            System.out.println("删除文件夹 " + delete);
        }
    }
}
```

## IO 流原理及流的分类

I/O 是 Input/Output 的缩写， I/O 技术是非常实用的技术，用于
处理设备之间的数据传输。例如读/写文件，网络通讯等。

Java 程序中，对于数据的输入/输出操作以“**流（stream）**” 的方式进行。

**流的分类**：

* 按**操作数据单位**不同分为：字节流（8 bit），字符流（16 bit）；
* 按**数据流的流向**不同分为：输入流，输出流；
* 按**流的角色**的不同分为：节点流，处理流。

**抽象基类**：

* `InputStream` 字节输入流；
* `OutputStream` 字节输出流；
* `Reader` 字符输入流；
* `Writer` 字符输出流；
* Java 的 IO 流共涉及 40 多个类，实际上非常规则，都是从如下 4 个抽象基类派生的；
* 由这四个类派生出来的子类名称都是以其父类名作为子类名后缀。

**编码流程**：

* 创建 File 类对象，指明读入或写入的文件；
* 创建输入流或输出流的对象；
* 数据的读入或输出操作；
* 关闭流资源；

## 文件流（节点流）

注意：

* 在**写入**一个文件时，如果使用构造器 `FileOutputStream(file)` ，则目录下有同名文件将被覆盖。
* 如果使用构造器 `FileOutputStream(file, true)` ，则目录下的同名文件不会被覆盖，在文件内容末尾追加内容。
* 在**读取**文件时，必须保证该文件**已存在**，否则报异常。
* **字节流操作字节**，比如：.mp3，.avi，.rmvb，mp4，.jpg，.doc，.ppt
* **字符流操作字符**，只能操作**普通文本**文件。最常见的文本文件：.txt，.java，.c，.cpp 等语言的源代码。尤其注意 .doc，.excel，.ppt 这些不是文本文件。

```java
package parzulpan.com.java;

import org.junit.Test;

import java.io.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 文件字符流操作
 */

public class FileReaderWriter {
    public static void main(String[] args) {
        File file = new File("hello.txt");  // 相较于当前工程
    }

    // 将 hello.txt 读入到内存程序中，并输出
    @Test
    public void testFileReader(){
        // 1.
        File file = new File("hello.txt");  // 相较于当前Module
        FileReader fileReader = null;

        try {
            // 2.
            fileReader = new FileReader(file);

            char[] chars = new char[5];
            int data;

            // 3.
            while (true) {
//                // int read()
//                if ((data = fileReader.read()) == -1) break;
//                System.out.print((char) data);

                // int read(char[] buf)
                if ((data = fileReader.read(chars)) == -1) break;
                for (int i = 0; i < data; ++i) {    // 注意这里是 data
                    System.out.print(chars[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4.
        try {
            if (fileReader != null) {
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 将内存程序数据 写入 hello1.txt
    @Test
    public void testFileWrite() {
        // 1.
        File file = new File("hello1.txt");
        FileWriter fileWriter = null;

        try {
            // 2.
            fileWriter = new FileWriter(file, true);    // append 为 true 则追加，否则覆盖

            // 3.
            fileWriter.write("hello world!!!!!");
            fileWriter.write("yes!!".toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    // 4.
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
```

```java
package parzulpan.com.java;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 文件字节流操作
 */

public class FileInputOutputStreamTest {

    // 实现对 excel 文件的复制
    @Test
    public void testFileFileInputOutputStream() {
        File srcFile = new File("test.xlsx");
        File destFile = new File("test1.xlsx");
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            int data;

//            // 读取单个字符
//            while ((data = fis.read()) != -1) {
//                fos.write(data);
//            }

            // 将字符读入数组
            byte[] buffer = new byte[1024];
            while ((data = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
```

## 缓冲流

为了**提高数据读写**的速度，Java API 提供了带缓冲功能的流类，在使用这些流类时，会创建一个**内部缓冲区数组**，缺省使用 8192 个字节（**8 Kb**）的缓冲区。

创建缓冲流对象，它是处理流，是对节点流的包装。

当读取数据时，数据按块读入缓冲区，其后的读操作则直接访问缓冲区，直到缓冲区装满了，才重新从文件中读取下一个 8Kb 数组。

向流中写入字节时，不会直接写到文件，先写到缓冲区中直到缓冲区写满，
 BufferedOutputStream 才会把缓冲区中的数据一次性写到文件里。使用 flush() 可以强制将缓冲区的内容全部写入输出流。

```java
/**
* 缓冲流
* @param srcFilePath 源文件路径
* @param destFilePath  目标文件路径
* @return  复制是否成功
*/
public static boolean copyFileWithBufferInputOutputStream(String srcFilePath, String destFilePath) {
    File srcFile = new File(srcFilePath);
    File destFile = new File(destFilePath);

    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        bis = new BufferedInputStream(fis);
        bos = new BufferedOutputStream(fos);

        int data;
        byte[] buffer = new byte[1024];
        while ((data = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, data);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    // 关闭资源时，要求先关闭外层的流，再关闭外层的流。但是可以直接关闭外层的流，内层的流会自动关闭。
    try {
        if (bos != null) {
            bos.close();
        }
        if (bis != null) {
            bis.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return true;
}
```

## 转换流

转换流提供了在字节流和字符流之间的**转换**，因为字节流中的数据都是字符时，转成字符流操作更高效。

很多时候使用转换流来处理文件乱码问题，实现**编码**和**解码**的功能。

Java API 提供了两个转换流：

* `InputStreamReader`：将 InputStream 转换为 Reader
  * 实现将字节的输入流按指定字符集转换为字符的输入流，需要和**InputStream** “套接”。
  * `public InputStreamReader(InputStream in)`
  * `public InputSreamReader(InputStream in,String charsetName)`
* `OutputStreamWriter`：将 Writer 转换为 OutputStream
  * 实现将字符的输出流按指定字符集转换为字节的输出流，需要和**OutputStream** “套接”。
  * `public OutputStreamWriter(OutputStream out)`
  * `public OutputSreamWriter(OutputStream out,String charsetName)`

```java
package parzulpan.com.java;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 转换流
 */

public class InputStreamReaderOutputStreamWriterTest {

    // 实现将字节的输入流按指定字符集转换为字符的输入流，等效于解码
    // 然后将字符的输出流按指定字符集转换为字节的输出流，等效于编码
    @Test
    public void test1() {
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        try {
            FileInputStream fis = new FileInputStream("word.txt");
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            FileOutputStream fos = new FileOutputStream("wordGBk.txt");
            osw = new OutputStreamWriter(fos, "GBK");

            char[] buffer = new char[1024];
            int data;
            while ((data = isr.read(buffer)) != -1) {
                String str = new String(buffer, 0, data);
                System.out.println(str);
                osw.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (isr != null) {
                isr.close();
            }
            if (osw != null) {
                osw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 标准输入输出流、打印流、数据流

```java
package parzulpan.com.java;

import java.io.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 标准输入输出流、打印流、数据流
 */

public class OtherStreamTest {
    public static void main(String[] args) {
        test2();
    }


    /**
     * 1. 标准输入输出流
     * 1.1 System.in 标准的输入流，默认从键盘输入，System.out 标准的输出流，默认从控制台输出
     * 1.2 System.setIn(InputStream is) 和 System.setOut(OutputStream ps) 方式重新指定输入和输出的流
     *
     * 练习：从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续进行输入操作，直至当输入 “e” 或者 “exit” 时，退出程序。
     * 方法一：使用 Scanner；
     * 方法二：使用 System.in； -> System.in -> 转换流 -> BufferedReader.readLine
     */
    public static void test() {
        System.out.print("请输入信息（e 或 exit 退出）：");

        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            br = new BufferedReader(isr);

            String s;
            while ((s = br.readLine()) != null) {
                if ("e".equalsIgnoreCase(s) || "exit".equalsIgnoreCase(s)) {
                    System.out.print("已安全退出！");
                    break;
                }
                System.out.println(s.toUpperCase());
                System.out.print("请输入信息（e 或 exit 退出）：");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. 打印流
     * 实现将基本数据类型的数据格式转化为字符串输出，提供了一系列重载的 print() 和 println() 方法，用于多种数据类型的输出
     * 练习：将打印输出流不通过控制台，而是直接写入到文件
     */
    public static void test1() {
        PrintStream ps = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File("ASCII.txt"));
            //  创建打印输出流,设置为自动刷新模式（写入换行符或字节 '\n' 时都会刷新输出缓冲区）
            ps = new PrintStream(fos, true);
            if (ps != null) {
                System.setOut(ps);  // 把标准输出流(控制台输出)改成文件
            }

            for (int i = 0; i < 256; i++) {
                System.out.print((char) i);
                if (i % 50 == 0) {
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (ps != null) {
            ps.close();
        }
    }

    /**
     * 3. 数据流
     * 为了方便地操作 Java 语言的 基本数据类型 和 String 的数据，可以使用数据流。
     * 数据流有两个类：(用于读取和写出基本数据类型、String类的数据）
     * DataInputStream 和 DataOutputStream 分别“套接”在 InputStream 和 OutputStream 子类的流上
     */
    public static void test2() {
        DataOutputStream dos = null;
        DataInputStream dis = null;
        try {
            dos = new DataOutputStream(new FileOutputStream("destData.dat"));
            dis = new DataInputStream(new FileInputStream("destData.dat"));

            dos.writeUTF("Beijing");
            dos.writeBoolean(true);
            dos.writeLong(1008611);

            String info = dis.readUTF();
            boolean b = dis.readBoolean();
            long l = dis.readLong();
            System.out.println(info);
            System.out.println(b);
            System.out.println(l);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (dos != null) {
                dos.close();
            }
            if (dis != null) {
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 对象流

ObjectInputStream 和 OjbectOutputSteam：用于**存储**和**读取** **基本数据类型数据** 或 **对象**的处理流。它的强大之处就是可
以把 Java 中的对象写入到数据源中，也能把对象从数据源中还原回来。

**注意**：ObjectOutputStream 和 ObjectInputStream 不能序列化 static 和 transient 修饰的成员变量。

***对象的序列化**：

* **序列化**：用 ObjectOutputStream 类保存基本类型数据或对象的机制；
* **反序列化**：用 ObjectInputStream 类读取基本类型数据或对象的机制；
* **对象序列化机制**允许把内存中的 Java 对象转换成**平台无关**的二进制流，从而允许把这种二进制流持久地保存在磁盘上，或通过网络将这种二进制流传输到另一个网络节点；
* 序列化的好处在于可将任何实现了 `Serializable` 接口的对象转化为**字节数据**，使其在保存和传输时可被还原；
  * 如果需要让某个对象支持序列化机制，则必须让对象所属的类及其属性是可序列化的，为了让某个类是可序列化的，该类必须实现如下两个接口之一：
    * **Serializable**
    * **Externalizable**

***理解对象的序列化**：

* 凡是实现 Serializable 接口的类都有一个表示序列化版本标识符的静态变量：`private static final long serialVersionUID;`
* serialVersionUID 用来表明类的不同版本间的兼容性；
* 如果类没有显示定义这个静态常量，它的值是 Java 运行时环境根据类的内部细节自动生成的。若类的实例变量做了修改，serialVersionUID 可能发生变化。故建议**显式声明**。

**简单来说**，**Java 的序列化机制是通过在运行时判断类的 serialVersionUID 来验证版本一致性的**。在进行反序列化时，JVM 会把传来的字节流中的 serialVersionUID 与本地相应实体类的 serialVersionUID 进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。

```java
package parzulpan.com.java;

import org.junit.Test;

import java.io.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 对象流的使用
 */

public class ObjectInputOutputStreamTest {

    // 序列化，将对象写入到磁盘或者进行网络传输。
    @Test
    public void testObjectOutputStream() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("object.data"));
            Person par = new Person("Par", 99);
            oos.writeObject(par);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (oos != null) {
                oos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 反序列化，将磁盘或者网络中的对象数据源读出
    @Test
    public void testObjectInputStream() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("object.data"));
            Object o = ois.readObject();
            System.out.println(o.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (ois != null) {
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// 要求序列化，
// 1. 需要实现 Serializable 接口
// 2. 并且显式声明 serialVersionUID
// 3. 其内部属性要求都是可序列化的，但是 static 和 transient 修饰的成员变量不能序列化
class Person implements Serializable{
    private String name;
    private int age;
    private static final long serialVersionUID = 15683901680463743L;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

## 随机存取文件流

RandomAccessFile 声明在 `java.io` 包下，但**直接继承于** `java.lang.Object` 类。并且它实现了 DataInput、DataOutput 这两个接口，也就意味着这个类既可以读也可以写。

注意：作为输出流时，写入的文件如果不存在，则在执行过程中自动创建，如果存在，则会对原有文件内容进行覆盖，默认情况下，从头开始覆盖。

RandomAccessFile 对象包含一个记录指针，用以标示当前读写处的位置。它可以自由移动记录指针：

* `long getFilePointer()`：获取文件记录指针的当前位置
* `void seek(long pos)`：将文件记录指针定位到 pos 位置

```java
package parzulpan.com.java;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 随机存取文件流的使用
 */

public class RandomAccessFileTest {

    @Test
    public void test1() {
        RandomAccessFile r = null;
        RandomAccessFile rw = null;
        try {
            //  以只读方式打开，则不会创建文件，而是会去读取一个已经存在的文件，如果读取的文件不存在则会出现异常
            r = new RandomAccessFile(new File("hello.txt"), "r");
            // 以读取和写入方式打开，如果文件不存在则会去创建文件，如果存在则不会创建。
            rw = new RandomAccessFile(new File("hello2.txt"),  "rw");

            byte[] bytes = new byte[1024];
            int data;
            while ((data = r.read(bytes)) != -1) {
                rw.write(bytes, 0, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (rw != null) {
                rw.close();
            }
            if (r != null) {
                r.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
```

## NIO.2

`Java NIO (New IO，Non-Blocking IO)` 是从 Java4 版本开始引入的一套新的 IO API，可以替代标准的 Java IO API。NIO 与原来的 IO 有同样的作用和目的，但是使用的方式完全不同，**NIO 支持面向缓冲区**的（**IO是面向流的**）、**基于通道的 IO 操作**。NIO 将以更加高效的方式进行文件的读写操作。但是 NIO 写的一般。

随着 Java7 的发布，Java 对 NIO 进行了极大的扩展，增强了对文件处理和文件系统特性的支持，称为 **NIO.2**。

Java API 中提供了两套 NIO，一套是针对标准输入输出 NIO，另一套就是网
络编程 NIO。

* `java.nio.channels.Channel`
  * `FileChannel` 处理本地文件
  * `SocketChannel` TCP 网络编程的客户端的 Channel
  * `ServerSocketChannel` TCP 网络编程的服务器端的 Channel
  * `DatagramChannel` UDP 网络编程中发送端和接收端的 Channel

## 练习和总结

---

判断指定目录下是否有后缀名为 .jpg 的文件，如果有，就输出该文件名称？

```java
package parzulpan.com.exer;

import java.io.File;
import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 判断指定目录下是否有后缀名为 .jpg 的文件，如果有，就输出该文件名称？
 */

public class GetJPGName {
    public static void main(String[] args) {
        System.out.print("请输入目录：");
        Scanner scanner = new Scanner(System.in);
        String dir = scanner.next();
        printJPGtName(dir);
    }

    public static void printJPGtName(String dir) {
        File file = new File(dir);
        if (file.isDirectory()) {
            String[] list = file.list();
            if (list != null) {
                for (String l : list) {
                    boolean b = l.toLowerCase().endsWith(".jpg");
                    if (b) {
                        System.out.println(l);
                    }
                }
            }
        }
    }
}
```

---

**遍历指定目录所有文件名称，包括子文件目录中的文件，并计算指定目录占用空间的大小，并删除指定文件目录及其下的所有文件？**

```java
package parzulpan.com.exer;

import java.io.File;
import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 遍历指定目录所有文件名称，包括子文件目录中的文件，并计算指定目录占用空间的大小，并删除指定文件目录及其下的所有文件？
 */

public class GetAllFile {
    public static void main(String[] args) {
        System.out.print("请输入目录：");
        Scanner scanner = new Scanner(System.in);
        String dir = scanner.next();
        File file = new File(dir);
        System.out.println("目录占用空间的大小（字节数）：" + getDir(file));

        System.out.print("请输入目录：");
        Scanner scanner1 = new Scanner(System.in);
        String dir1 = scanner1.next();
        File file1 = new File(dir1);
        deleteDir(file1);

    }

    public static long getDir(File dir) {
        File[] subFiles = dir.listFiles();
        long len = 0;
        for (File file : subFiles) {
            if (file.isDirectory()) {
                len += getDir(file);
            } else {
                String absolutePath = file.getAbsolutePath();
                System.out.println(absolutePath);
                len += file.length();
            }
        }
        return len;
    }

    public static void deleteDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteDir(f);
            }
        }
        file.delete();
    }
}
```

---

**获取文本上每个字符出现的次数，并将结果写入文件？**

```java
package parzulpan.com.exer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 获取文本上每个字符出现的次数，并将结果写入文件
 */

public class WordCount {
    public static void main(String[] args) {

        worldCount("ch11/word.txt", "ch11/wordCount.txt");

    }

    public static void worldCount(String srcFilePath, String destFilePath) {
        HashMap<Character, Integer> word = new HashMap<Character, Integer>();

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(new File(srcFilePath)));
            bw = new BufferedWriter(new FileWriter(new File(destFilePath)));

            int data;
            while ((data = br.read()) != -1) {
                if (word.containsKey((char)data)) {
                    word.put((char)data, word.get((char)data) + 1);
                } else {
                    word.put((char)data, 1);
                }
            }

            Set<Map.Entry<Character, Integer>> entries = word.entrySet();
            for(Map.Entry<Character, Integer> e : entries) {
                bw.write(e.getKey() + " : " + e.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (bw != null) {
                bw.close();
            }
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

**谈谈你对java.io.Serializable接口的理解，我们知道它用于序列化，
是空方法接口，还有其它认识吗？**

* 实现 Serializable 接口的对象，可以进行序列化，即将对象写入到磁盘或者进行网络传输，也可以进行反序列化，即将磁盘或者网络中的对象数据源读出。**这意味着序列化机制能自动补偿操作系统间的差异**。
* 由于大部分作为参数的类如 String、Integer 等都实现了 java.io.Serializable 的接口，也可以利用多态的性质，作为参数使接口更灵活。

---
