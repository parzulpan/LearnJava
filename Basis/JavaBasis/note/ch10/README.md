# 泛型

## 为什么要有泛型

集合容器类在设计阶段 / 声明阶段不能确定这个容器到底实际存的是什么类型的
对象，所以在 JDK1.5 之前只能把元素类型设计为 **Object**，JDK1.5 之后使用**泛型**来解决。因为这个时候除了元素的类型不确定，其他的部分是确定的，**此时把元素的类型设计成一个参数**，这个**类型参数**叫做泛型。

例如 `Collection<E>`，`List<E>`，`ArrayList<E>`，这个 `<E>` 就是类型参数，即**泛型**。

```java
package parzulpan.com.java;

import org.junit.Test;

import java.util.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : 泛型的使用，JDK5 新增的特性
 */

public class GenericTest {

    // 在集合中使用泛型之前的情况
    @Test
    public void test1() {

        ArrayList arrayList = new ArrayList();
        // 存放学生成绩
        arrayList.add(78);
        arrayList.add(12);
        arrayList.add(88);

        // 问题一：类型不安全
        arrayList.add("Tom");

        for (Object score : arrayList) {
            // 问题二：强制类型转换时，可能出现 ClassCastException
            int stuScore = (Integer) score;
            System.out.println(stuScore);
        }

    }

    // 在集合中使用泛型之后的情况，ArrayList
    @Test
    public void test2() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        // 存放学生成绩
        arrayList.add(78);
        arrayList.add(12);
        arrayList.add(88);

        // 编译时就会进行类型检查，保证数据的安全
//        arrayList.add("Tom");

        for (Integer score : arrayList) {
            // 避免强制类型转换
            int stuScore = score;
            System.out.println(stuScore);
        }
    }

    // 在集合中使用泛型之后的情况，HashMap
    @Test
    public void test3() {
        HashMap<String, ArrayList<Integer>> stringArrayListHashMap = new HashMap<>();
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(78);
        integers.add(12);
        integers.add(88);
        stringArrayListHashMap.put("Tom", integers);

        Set<Map.Entry<String, ArrayList<Integer>>> entries = stringArrayListHashMap.entrySet();

        for (Map.Entry<String, ArrayList<Integer>> next : entries) {
            String key = next.getKey();
            System.out.println("name: " + key);
            ArrayList<Integer> value = next.getValue();
            System.out.println("scores: " + value);
        }

        System.out.println();

        Iterator<Map.Entry<String, ArrayList<Integer>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ArrayList<Integer>> next = iterator.next();
            String key = next.getKey();
            System.out.println("name: " +key);
            ArrayList<Integer> value = next.getValue();
            System.out.println("scores: " + value);
        }
    }
}
```

总之，Java 泛型可以保证如果程序在编译时没有发出警告，运行时就不会产生
ClassCastException 异常。同时，代码更加简洁、健壮。

## 自定义泛型结构

**自定义泛型类、泛型接口**：

```java
// DAO.java
package parzulpan.com.java;

import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : DAO：base（data） access object
 */

// 通型操作
public class DAO<T> {

    // 增加一条记录
    public void add(T t) {

    }

    // 删除一条记录
    public boolean remove(int index) {

        return false;
    }

    // 修改一条记录
    public void update(int index, T t) {

    }

    // 查询一条记录
    public T getIndex(int index) {

        return null;
    }

    // 查询多条记录
    public List<T> getList(int index) {

        return null;
    }

    // 泛型方法，不能为 static
    public <E> E getValueOfT(T t) {

        return null;
    }

    // 泛型方法，可以为 static
    public static <E> E getValue() {

        return null;
    }
}

// 对应 Customer 表
class Customer {

}

// 只能操作 Customer 表
class CustomerDAO extends DAO<Customer> {

}

// 对应 Producer 表
class Producer {

}

class ProducerDAO extends  DAO<Producer> {

}
```

```java
// DAOTest.java

package parzulpan.com.java;

import org.junit.Test;

import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc :
 */

public class DAOTest {
    @Test
    public void test1() {
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.add(new Customer());
        List<Customer> list = customerDAO.getList(10);

        ProducerDAO producerDAO = new ProducerDAO();
        producerDAO.remove(1);
        Producer index = producerDAO.getIndex(1);
    }
}
```

**自定义泛型方法**：

* 方法，也可以被泛型化，不管此时定义在其中的类是不是泛型类。在泛型
方法中可以定义泛型参数，此时，参数的类型就是传入数据的类型。
* **格式**：`[访问权限] <泛型> 返回类型 方法名([泛型标识 参数名称]) 抛出的异常`
  
  ```java
  public <E> List<E> copyFromArrayToList(E[] arr) {
      ArrayList<E> list = new ArrayList<>();

      for(E e : arr) {
          list.add(e);
      }

      return list;
  }
  ```

## 泛型在继承上的体现

如果 B 是 A 的一个子类型（子类或者子接口），而 G 是具有泛型声明的
类或接口，`G<B>` 并不是 `G<A>` 的子类型。

具体的，String 是 Object 的子类，但是 `List<String>` 并不是`List<Object>` 的子类。

```java
public void testGenericAndSubClass() {
    // Person 是 Man 的父类
    Person[] persons = null;
    Man[] man = null;
    persons = man;
    Person p = man[0];

    // 在泛型的集合上
    List<Person> personList = null;
    List<Man> manList = null;
    // personList = manList;    // 编译错误
}
```

## 通配符的使用

使用类型**通配符**：`？`，比如：`List<?>` ，`Map<?,?>`。

`List<?>` 是 `List<String>`、`List<Object>` 等各种泛型 List 的父类。

**读取** List<?> 的对象 list 中的元素时，永远是安全的，因为不管 list 的真实类型是什么，它包含的都是 Object。

**写入** list 中的元素时，**不行**。因为我们不知道 `?` 的元素类型，我们不能向其中添加对象。唯一的例外是 null，它是所有类型的成员。

即只能读取，不能写入。

**注意**：

* 注意点1：编译错误，不能用在泛型方法声明上，返回值类型前面 `<>` 不能使用`?`

  ```java
  public static <?> void test(ArrayList<?> list){

  }
  ```

* 注意点2：编译错误，不能用在泛型类的声明上

  ```java
  class GenericTypeClass<?>{

  }
  ```

* 注意点2：编译错误，不能用在创建对象上，右边属于创建集合对象

  ```java
  ArrayList<?> list2 = new ArrayList<?>();
  ```

**有限制条件的通配符**：

* **通配符指定上限**：上限 extends，使用时指定的类型必须是继承某个类，或者实现某个接口，即 `<=`；
* **通配符指定下限**：下限 super，使用时指定的类型不能小于操作的类，即 `>=`；
* 举例：
  * `<? extends Number> (无穷小 , Number]` 只允许泛型为 Number 及 Number 子类的引用调用;
  * `<? super Number> [Number , 无穷大)` 只允许泛型为 Number 及 Number 父类的引用调用；
  * `<? extends Comparable>` 只允许泛型为实现 Comparable 接口的实现类的引用调用；

## 泛型应用举例

...

## 练习和总结
