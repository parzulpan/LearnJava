package parzulpan.com.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc :
 */

public class Person {
    private String name;
    public int age;

    public Person() {
    }

    private Person(String name) {
        this.name = name;
    }

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

    public void show() {
        System.out.println("Hello, I am Person!");
    }

    private String showNation(String nation) {
        System.out.println("My nation is " + nation + "!");
        return nation;
    }

    private static void showNationStatic() {
        System.out.println("My nation is Static!");
    }
}
