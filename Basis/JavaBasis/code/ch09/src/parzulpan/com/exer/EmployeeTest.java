package parzulpan.com.exer;

import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc :
 */

public class EmployeeTest {

    // 按名字排序，自然排序
    @Test
    public void test1() {

        TreeSet treeSet = new TreeSet();
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

        Iterator iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

    // 按生日排序，定制排序
    @Test
    public void test2() {

        TreeSet treeSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Employee && o2 instanceof Employee) {
                    Employee employee1 = (Employee) o1;
                    Employee employee2 = (Employee) o2;

                    MyDate birthday1 = employee1.getBirthday();
                    MyDate birthday2 = employee2.getBirthday();

                    int minusY = birthday1.getYear() - birthday2.getYear();
                    if (minusY != 0) {
                        return minusY;
                    }

                    int minusM = birthday1.getMonth() - birthday2.getMonth();
                    if (minusM != 0) {
                        return minusM;
                    }

                    return birthday1.getDay() - birthday2.getDay();

                }
                throw new RuntimeException("数据类型不一致～");
            }
        });
        Employee anf = new Employee("ANF", 55, new MyDate(1967, 11, 2));
        Employee esg = new Employee("ESG", 15, new MyDate(1994, 10, 22));
        Employee wr = new Employee("WR", 54, new MyDate(2000, 5, 14));
        Employee te = new Employee("TE", 34, new MyDate(2000, 5, 12));
        Employee yre = new Employee("YRE", 90, new MyDate(2010, 1, 30));
        Employee yre1 = new Employee("YRE1", 90, new MyDate(2010, 1, 30));  // 认为不是同一个人，但是加不进来
        treeSet.add(anf);
        treeSet.add(esg);
        treeSet.add(wr);
        treeSet.add(te);
        treeSet.add(yre);
        treeSet.add(yre1);

        Iterator iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}

class Employee implements Comparable{
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
    public int compareTo(Object o) {
        if (o instanceof Employee) {
            Employee employee = (Employee) o;
            return this.name.compareTo(employee.name);
        }

        throw new RuntimeException("数据类型不一致～");
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

class MyDate {
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
}