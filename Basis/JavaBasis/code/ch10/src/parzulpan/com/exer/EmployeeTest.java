package parzulpan.com.exer;

import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : 泛型改写
 */

public class EmployeeTest {

    // 按名字排序，自然排序
    @Test
    public void test1() {

        TreeSet<Employee> treeSet = new TreeSet<>();
        Employee anf = new Employee("ANF", 55, new MyDate(1967, 11, 2));
        Employee esg = new Employee("ESG", 15, new MyDate(1994, 10, 22));
        Employee wr = new Employee("WR", 54, new MyDate(2000, 5, 14));
        Employee te = new Employee("TE", 34, new MyDate(2000, 5, 12));
        Employee yre = new Employee("YRE", 90, new MyDate(2010, 1, 30));
        Employee yre1 = new Employee("YRE1", 90, new MyDate(2010, 1, 30));  // 认为不是同一个人，但是能加进来
        treeSet.add(anf);
        treeSet.add(esg);
        treeSet.add(wr);
        treeSet.add(te);
        treeSet.add(yre);
        treeSet.add(yre1);

        for (Employee employee : treeSet) {
            System.out.println(employee);
        }

    }

    // 按生日排序，定制排序
    @Test
    public void test2() {

        TreeSet<Employee> treeSet = new TreeSet<Employee>(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                MyDate birthday1 = o1.getBirthday();
                MyDate birthday2 = o2.getBirthday();
                return birthday1.compareTo(birthday2);

            }
        });

        Employee ewr = new Employee("EWR", 55, new MyDate(1967, 11, 2));
        Employee esg = new Employee("ESG", 15, new MyDate(1994, 10, 22));
        Employee wr = new Employee("WR", 54, new MyDate(2000, 5, 14));
        Employee te = new Employee("TE", 34, new MyDate(2000, 5, 12));
        Employee yre = new Employee("YRE", 90, new MyDate(2010, 1, 30));
        Employee yre1 = new Employee("YRE1", 90, new MyDate(2010, 1, 30));  // 认为不是同一个人，但是加不进来
        treeSet.add(ewr);
        treeSet.add(esg);
        treeSet.add(wr);
        treeSet.add(te);
        treeSet.add(yre);
        treeSet.add(yre1);

        Iterator<Employee> iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}

class Employee implements Comparable<Employee>{
    private String name;
    private int age;
    private MyDate birthday;

    public Employee() {

    }

    public Employee(String name, int age, MyDate birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
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

    public MyDate getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public int compareTo(Employee o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (age != employee.age) return false;
        if (!name.equals(employee.name)) return false;
        return birthday.equals(employee.birthday);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        result = 31 * result + birthday.hashCode();
        return result;
    }
}

class MyDate implements Comparable<MyDate>{
    private int year;
    private int month;
    private int day;

    public MyDate() {

    }

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    @Override
    public int compareTo(MyDate o) {
        int minusY = this.getYear() - o.getYear();
        if (minusY != 0) {
            return minusY;
        }

        int minusM = this.getMonth() - o.getMonth();
        if (minusM != 0) {
            return minusM;
        }

        return this.getDay() - o.getDay();
    }
}