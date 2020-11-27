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

    // 反序列化，将磁盘中的对象数据源读出
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
