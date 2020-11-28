package parzulpan.com.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 获取运行时类的完整结构 - 类
 */

@MyAnnotation(value = "hi")
public class MyPerson extends MyCreature<String> implements Comparable<String>, MyInterface {
    private String name;
    int age;
    public int id;

    public MyPerson() {

    }

    @MyAnnotation(value = "abc")
    private MyPerson(String name) {
        this.name = name;
    }

    MyPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @MyAnnotation
    private String show(String nation) {
        System.out.println("My nation is " + nation);
        return nation;
    }

    @MyAnnotation(value = "dis")
    public String display(String interests) throws Exception{
        return interests;
    }

    @Override
    public void info() {
        System.out.println("I am Person");
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }

    private static void showStatic() {
        System.out.println("I am rich Person");
    }

    @Override
    public String toString() {
        return "MyPerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}