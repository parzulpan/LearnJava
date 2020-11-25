# 集合

## 集合概述

一方面， 面向对象语言对事物的体现都是以对象的形式，为了方便对多个对象
的操作，就要对**对象进行存储**。另一方面，使用 Array 存储对象方面具有一些**弊端**，而 Java 集合就像一种**容器**，可以**动态地**把多个对象的引用放入容器中。

Array 在存储方面的特点：

* 数组初始化以后，长度就确定了；
* 数组声明的类型，就决定了进行元素初始化时的类型。

Array 在存储方面的缺点：

* 数组初始化以后，长度就不可变了，不便于扩展；
* 数组中提供的属性和方法少，不便于进行添加、删除、插入等操作，且效率不高。同时无法直接获取存储元素的个数；
* 数组存储的数据是有序的、可以重复的。对于无序、不可重复的需求，不能满足。

**集合框架**：

* **Collection 接口**：单列集合，用来存储一个一个的对象
  * **List 接口**：存储有序的、可重复的对象
    * **ArrayList**：作为 List 接口的主要实现类；底层使用 `Object[] elementData` 存储，适用于频繁随机访问操作；`add(E e)` 默认情况下，扩容为原来的容量的 1.5 倍；线程不安全的，效率高；
    * **LingkedList**： 底层使用双向链表存储，适用于频繁插入、删除操作；线程不安全的，效率高；
    * **Vector**：作为 List 接口的古老实现类；底层使用 `Object[] elementData` 存储，适用于频繁随机访问操作；`add(E e)` 默认情况下，扩容为原来的容量的 2 倍；线程安全的，效率低；
  * **Set 接口**：存储无序的、不可重复的对象
    * **HashSet**：作为 Set 接口的主要实现类；线程不安全的，效率高；
    * **LinkedHashSet**：作为 HashSet 的子类，遍历其内部数据时，可以按照添加的顺序遍历；
    * **TreeSet**：可以按照添加对象的指定属性进行排序；
* **Map 接口**：双列集合，用来存储一对一对的对象
  * **HashMap**：
  * **LinkedHashMap**：
  * **TreeMap**：
  * **Hashtable**：
  * **Properties**：

## Collection 接口

Collection 接口是 List、Set 和 Queue 等接口的父接口，该接口里定义的方法既可用于操作 Set 集合，也可用于操作 List 和 Queue 等集合。

**常用方法**：

* `add(Object obj)` 将元素/对象 e 添加到集合
* `addAll(Collection c)` 将 c 集合中的元素添加到当前集合中
* `size()` 获取有效元素的个数
* `clear()` 清空集合元素
* `isEmpty()` 判断当前集合是否为空，即是否有元素
* `contains(Object obj)` 是否包含某个元素，是通过元素的 equals 方法来判断是否是同一个对象
* `containsAll(Collection c)` 是否包含某个元素，是调用元素的 equals 方法来比较的，拿两个集合的元素逐个比较
* `remove(Object obj)` 通过元素的 equals 方法判断是否是要删除的那个元素，只会删除找到的第一个元素
* `removeAll(Collection c)` 取当前集合的差集
* `retainAll(Collection c)` 把交集的结果存在当前集合中，不影响 c
* `equals(Object obj)` 集合是否相等
* `Object[] toArray()` 转成对象数组
* `hashCode()` 获取集合对象的哈希值
* `iterator()` 返回迭代器对象，用于集合遍历

### Iterator 迭代器接口

Iterator 对象称为迭代器（设计模式的一种），**主要用于遍历 Collection 集合**（不用于 Map）中的元素。

**GOF** 给迭代器模式的**定义**为：提供一种方法访问一个容器（container）对象中各个元素，而又不需暴露该对象的内部细节。**迭代器模式，就是为容器而生**。

Collection 接口继承了 `java.lang.Iterable` 接口，该接口有一个 `iterator()` 方法，那么所有实现了 Collection 接口的集合类都有一个 `iterator()` 方法，用以返回一个实现了 Iterator 接口的对象。

注意：

* Iterator **仅用于遍历集合**，Iterator 本身并不提供承装对象的能力。如果需要创建Iterator 对象，则必须有一个被迭代的集合；
* 集合对象每次调用 `iterator()` 方法都**得到一个全新的迭代器对象**，默认**游标**都在集合的**第一个元素之前**。

常用方法：

* `hasNext()` 判断是否还有下一个元素
* `next()` 将指针下移，并下移以后集合位置上的元素返回
* `remove()` 删除集合的元素

**使用泛例**：

```java
Iterator iterator = collection.iterator();    // 回到起点，重要
while (iterator.hasNext()) {
    Object obj = iterator.next();
    System.out.println(obj);
    if ("AA".equals(obj)) {
        iterator.remove();
    }
}
```

Java5 提供了 foreach 循环迭代访问 Collection 和数组。遍历操作不需获取 Collection 或数组的长度，无需使用索引访问元素。**遍历集合的底层调用 Iterator 完成操作**。

```java
for (Object obj: collection) {
    System.out.println(obj);
}
```

### List 接口

鉴于 Java 中数组用来存储数据的局限性，通常**使用 List 替代数组**。

常用方法：

* `boolean add(E e)` 将 e 元素追加到此列表的末尾
* `void add(int index, E e)` 在 index 位置插入 e 元素
* `boolean addAll(int index, Collection eles)` 从 index 位置开始将 eles 中的所有元素添加进来
* `Object get(int index)` 获取指定 index 位置的元素
* `int indexOf(Object obj)` 返回 obj 在集合中首次出现的位置
* `int lastIndexOf(Object obj)` 返回 obj 在当前集合中末次出现的位置
* `Object remove(int index)` 移除指定 index 位置的元素，并返回此元素
* `Object set(int index, Object ele)` 设置指定 index 位置的元素为 ele
* `List subList(int fromIndex, int toIndex)` 返回从 fromIndex 到 toIndex 位置的子集合

#### ArrayList 类

***ArrayList 源码分析**：

* Java7：像饿汉式，直接创建一个初始容量为 10 的数组
  
  ```java
  // Java7 ArrayList
  ArrayList list = new ArrayList(); // 默认底层创建了长度为 10 的 Object[] 数组 elementData

  list.add(11); // elementData[0] = new Integer(123); 自动装箱
  // ...
  list.add(99); // 如果此次的添加导致底层 elementData 数组容量不够，则扩容。默认情况下，扩容为原来的容量的 1.5 倍，同时需要将原有数组中的元素复制到新数组中。因此，如果可预知数据量的多少，可在构造 ArrayList 时指定其容量。或者根据实际需求，通过调用 ensureCapacity 方法来手动增加 ArrayList 实例的容量。

  ```

* Java8：像懒汉式，一开始创建一个初始容量为 0 的数组，当添加第一个元素时再创建一个初始容量为 10 的数组

  ```java
  // Java8 ArrayList
  ArrayList list = new ArrayList(); // 默认底层创建了长度为 0 的 Object[] 数组 elementData

  list.add(11); // 第一次时才创建长度为 10 的 Object[] 数组 elementData，并将 element 添加
  // ...
  list.add(99); // 同 Java7

  ```

注意：

* `Arrays.asList(…)` 方法 返回一个固定长度的的 List 集合，既不是 ArrayList 实例，不是 Vector 实例。

#### LinkedList 类

新增方法：

* `void addFirst(Object obj)` 将指定的元素插入到此列表的开头，内部调用 `linkFirst(E e)`
* `void addLast(Object obj)` 将指定的元素添加到此列表的结尾，内部调用 `linkLast(E e)`
* `Object getFirst()`
* `Object getLast()`
* `Object removeFirst()`
* `Object removeLast()`

***LinkedList 源码分析**：

  ```java
  LinkedList list = new LinkedList();   // 内部声明 Node 类型的 first 和 last，默认值为 null

  // Node 定义
  private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
  }

  ```

#### Vector 类

新增方法：

* `void addElement(Object obj)`
* `void insertElementAt(Object obj, int index)`
* `void setElementAt(Object obj, int index)`
* `void removeElement(Object obj)`
* `void removeAllElements()`

在各种 list 中，最好把 ArrayList 作为缺省选择。当插入、删除频繁时，
使用 LinkedList。Vector 总是比 ArrayList 慢，所以尽量避免使用。

默认底层创建了长度为 10 的 Object[] 数组 elementData。

`add(E e)` 默认情况下，扩容为原来的容量的 2 倍。

### Set 接口

Set 接口是 Collection 的子接口，Set 接口没有提供额外的方法。

Set 集合**不允许包含相同的元素**，如果试把两个相同的元素加入同一个
Set 集合中，则添加操作失败。

Set 判断两个对象是否相同不是使用 `==` 运算符，而是根据 `equals()` 方法。

**注意**：

* 无序性：**不等于随机性**。存储的数据在底层数组中并非按照数组索引的顺序添加，而是根据的哈希值添加。
* 不可重复性：保证添加的元素按照 `equals()` 判断时，不能返回 true。

#### HashSet  类

HashSet 是 Set 接口的典型实现，大多数时候使用 Set 集合时都使用这个实现类。它是按 **Hash 算法**来存储集合中的元素，因此具有很好的**存取**、**查找**、**删除**性能。

**特点**：

* 不能保证元素的排列顺序；
* HashSet 不是线程安全的；
* 集合元素可以是 null；
* **判断两个元素相等的标准是 两个对象的 hashCode() 和 equals() 返回值都相等**；
* 对于存放在 Set 容器中的对象，对应的类一定要重写 equals() 和hashCode(Object obj) 方法，以实现对象相等规则。**即相等的对象必须具有相等的散列码**。

**向HashSet中添加元素的过程**：

* 向 HashSet 中添加元素 A，首先调用元素 A 所在类的 `hashCode()` 方法，计算元素 A 的哈希值，此哈希值接着通过某种算法计算出在 HashSet 底层数组中的存放位置，判断数组此位置上的是否有元素：
  * 如果此位置上没有其他元素，则元素 A 添加成功；（**情况 1**）
  * 如果此位置上有其他元素 B（或以链表形式存在的多个元素），则比较 A 和 B 的 hash 值：
    * 如果 hash 值不相同，则元素 A 添加成功；（**情况 2**）
    * 如果 hash 值相同，则调用 A 所在的 `equals()` 方法：
      * 如果 `equals()` 返回 false 则添加成功；（**情况 3**）
      * 如果 `equals()` 返回 true 则添加失败；
* 对于 情况 2 和情况 3 而言，A 与已经存在指定位置上的元素 以链表的方式存储：
  * JDK7 中，元素 A 放到数组中，指向原来的元素；
  * JDK8 中，原来的元素在数组中，指向元素 A；

```java
package parzulpan.com.java;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author : parzulpan
 * @Time : 2020-11-25
 * @Desc :
 */

public class SetTest {
    public static void main(String[] args) {

    }

    @Test
    public void test1() {
        Set set = new HashSet();
        set.add(456);
        set.add(123);
        set.add("AA");
        set.add("CC");
        set.add(new Date());

        set.add(new Person("AA", 12));
        set.add(new Person("AA", 12));
        set.add(129);

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("call equals");
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        System.out.println("return  hashCode " + result);
        return result;
    }
}
```

##### LinkedHashSet 类

LinkedHashSet 是 HashSet 的子类。

LinkedHashSet 根据元素的 hashCode 值来决定元素的存储位置，
但它同时使用**双向链表**维护元素的次序，这使得元素**看起来是以插入顺序保存**的。

这样的好处是，对于频繁的遍历操作，LinkedHashSet 的效率要高于 HashSet。

#### TreeSet 类

TreeSet 是 SortedSet 接口的实现类，TreeSet 可以确保集合元素**处于排序状态**。它底层采用红黑树的存储结构，查询速度较快。

**向 TreeSet 添加数据，要求是相同类的对象**。

TreeSet 两种排序方法，即自然排序和定制排序。默认情况下，TreeSet 采用自然排序。

* 自然排序中，比较两个对象是否相同的**标准**是 `compareTo(Object obj)` 方法 返回 0，不再是 equals();
* 定制排序中，比较两个对象是否相同的**标准**是 `compare(Object obj1, Object obj2)` 方法返回 0。

## Map 接口

### HashMap 类

#### LinkedHashMap 类

### TreeMap 类

### Hashtable 类

#### Properties 类

## Collections 工具类

## 练习和总结

---

**判断输出结果为何？**

```java
package parzulpan.com.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-25
 * @Desc :
 */

public class ForTest {
    public static void main(String[] args) {
        String[] str = new String[5];

        // foreach
        for (String myStr : str) {
            myStr = "AA";
            System.out.println(myStr);  // AA
        }

        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]); // null
        }
    }
}
```

foreach 本质是取 collection 中的元素赋值给 obj，内部仍然是使用的迭代器。又因为 String 的不可变性。

```java
for (Object obj: collection) {
    System.out.println(obj);
}
```

---

**请问 ArrayList、LinkedList、Vector 的异同？谈谈你的理解？ArrayList底层是什么？扩容机制？Vector、ArrayList的最大区别？**

* **ArrayList**：作为 List 接口的主要实现类；底层使用 `Object[] elementData` 存储，适用于频繁随机访问操作；`add(E e)` 默认情况下，扩容为原来的容量的 1.5 倍；线程不安全的，效率高；
* **LingkedList**： 底层使用双向链表存储，适用于频繁插入、删除操作；线程不安全的，效率高；
* **Vector**：作为 List 接口的古老实现类；底层使用 `Object[] elementData` 存储，适用于频繁随机访问操作；`add(E e)` 默认情况下，扩容为原来的容量的 2 倍；线程安全的，效率低；

---

**以 Eclipse/IDEA 为例，在自定义类中可以调用工具自动重写 equals 和 hashCode 。问题：为什么用 Eclipse/IDEA 复写 hashCode 方法，有 31 这个数字?**

因为：

* 选择系数的时候要选择尽量大的系数。因为如果计算出来的 hash 地址越大，所谓的“冲突”就越少，查找起来效率也会提高。（减少冲突）
* 并且 31 只占用 5bits，相乘造成数据溢出的概率较小。
* 31 可以 由 i*31== (i<<5)-1 来表示，现在很多虚拟机里面都有做相关优化。（提高算法效率）
* 31 是一个素数，素数作用就是如果我用一个数字来乘以这个素数，那么最终出来的结果只能被素数本身和被乘数还有 1 来整除。(减少冲突)

总的来说，是为了减少冲突和提高算法效率。

---