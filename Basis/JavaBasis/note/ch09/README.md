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
    * **LinkedList**： 底层使用双向链表存储，适用于频繁插入、删除操作；线程不安全的，效率高；
    * **Vector**：作为 List 接口的古老实现类；底层使用 `Object[] elementData` 存储，适用于频繁随机访问操作；`add(E e)` 默认情况下，扩容为原来的容量的 2 倍；线程安全的，效率低；
  * **Set 接口**：存储无序的、不可重复的对象
    * **HashSet**：作为 Set 接口的主要实现类；线程不安全的，效率高；
    * **LinkedHashSet**：作为 HashSet 的子类，遍历其内部数据时，可以按照添加的顺序遍历；
    * **TreeSet**：底层使用红黑树存储，可以按照添加对象的指定属性进行排序；
* **Map 接口**：双列集合，用来存储一对一对的对象
  * **HashMap**：作为 Map 接口的主要实现类；线程不安全的，效率高；可以存储 `null` 的 key 和 value；底层使用 数组+链表+红黑树（JDK8，JDK7无红黑树）存储；
  * **LinkedHashMap**：作为 HashMap 的子类，遍历其内部数据时，可以按照添加的顺序遍历；
  * **TreeMap**：底层使用红黑树存储，可以按照添加对象的指定属性进行排序；
  * **Hashtable**：作为 Map 接口的古老实现类；线程安全的，效率低；不可以存储 `null` 的 key 和 value；
  * **Properties**：常用来处理配置文件，key 和 value 都是 String 类型；

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

#### LinkedHashSet 类

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

Map 中的 key **用 Set 来存放**，不允许重复，即同一个 Map 对象所对应
的类，**必须重写 `hashCode()` 和 `equals()` 方法**。

* Map 的 key：无序的、不可重复的，使用 Set 存储所有的 key。**所以 key 所在的类要重写 `hashCode()` 和 `equals()`**
* Map 的 value：无序的、可重复的，使用 Collection 存储所有的 value。**所以 value 所在的类要重写 `equals()`**
* Map 的 entry：无序的、不可重复的，使用 Set 存储所有的 entry（key-value对 构成）。

常用方法：

* 添加、删除、修改操作：
  * `Object put(Object key,Object value)` 将指定 key-value 添加到（或修改）当前 map 对象中；
  * `void putAll(Map m)` 将 m 中的所有 key-value 对存放到当前 map 中；
  * `Object remove(Object key)` 移除指定 key 的 key-value 对，并返回 value；
  * `void clear()` 清空当前 map 中的所有数据。
* 元素查询的操作：
  * `Object get(Object key)` 获取指定 key 对应的 value
  * `boolean containsKey(Object key)` 是否包含指定的 key
  * `boolean containsValue(Object value)` 是否包含指定的 value
  * `int size()` 返回 map 中 key-value 对的个数
  * `boolean isEmpty()` 判断当前 map 是否为空
  * `boolean equals(Object obj)` 判断当前 map 和参数对象 obj 是否相等
* 元视图操作的方法：
  * `Set keySet()` 返回所有 key 构成的 Set 集合
  * `Collection values()` 返回所有 value 构成的 Collection 集合
  * `Set entrySet()` 返回所有 key-value 对构成的 Set 集合

```java
// map 的遍历
@Test
public void test3() {
    Map map = new HashMap();
    map.put(123, "AA");
    map.put(345, "OO");
    map.put("AAA", 1234);
    map.put(345, "WW");

    // 遍历所有的 key
    Set set = map.keySet();
    Iterator iterator = set.iterator();
    while (iterator.hasNext()) {
        System.out.println(iterator.next());
    }

    // 遍历所有的 value
    Collection values = map.values();
    for (Object obj: values) {
        System.out.println(obj);
    }

    // 遍历所有的 key-val
    Set set1 = map.entrySet();
    Iterator iterator1 = set1.iterator();
    while (iterator1.hasNext()) {
        Object next = iterator1.next();
        Map.Entry next1 = (Map.Entry) next;
        System.out.println(next1.getKey() + " -> " + next1.getValue());
    }

    // 遍历所有的 key-val
    Set set2 = map.keySet();
    Iterator iterator2 = set2.iterator();
    while (iterator2.hasNext()) {
        Object key = iterator2.next();
        Object value = map.get(key);
        System.out.println(key + " -> " + value);
    }
}

```

### HashMap 类

HashMap 是 Map 接口的主要实现类。允许使用 null 键和 null 值，与HashSet  一样，不保证映射的顺序。

HashMap 判断两个 key 相等的标准是：两个 key 通过 equals() 方法返回 true，hashCode 值也相等。

HashMap 判断两个 value 相等的标准是：两个 value 通过 equals() 方法返回 true。

***HashMap 底层实现原理**：

* 对于 Java7:
  * `HashMap map = new HashMap();` 在实例化后，底层创建了一个长度为 16 的一维数组 `Entry[] table`。执行 `map.put(kay1, value1);` 时，首先调用 key1 所在类的 hasCode() 计算其哈希值，然后得到其在 Entry 数组中的存放位置：
    * 如果此位置上的数据为空，则 key1-value1 添加成功；（**情况1**）
    * 如果此位置上的数据不为空，意味着此位置上存在一个或多个数据（以链表形式），比较 key1 与它们的哈希值：
      * 如果 key1 和它们的哈希值都不相同，则  key1-value1 添加成功；（**情况2**）
      * 如果 key1 和某一个数据（key2-value2）的哈希值相同，则调用 equals()：
        * 如果 equals() 返回 false，则  key1-value1 添加成功；（**情况3**）
        * 如果 equals() 返回 true，则使用 value1 替换 value2；
  * 对于情况 2 和情况 3，此时的 key1-value1 和原来的数据以链表的方式存储。
  * 在不断的添加中，当超出临界值且原有位置不为空时，会涉及扩容问题，默认的扩容方式是扩容到原来容量的 2 倍，并将原来的数据复制过来。
* 对于 Java8：
  * 相比较于 Java7，实例化后底层没有创建一个长度为 16 的数组，首次 `put()` 后才创建长度为 16 的数组。且一维数组不是 `Entry[]` 而是 `Node[]`。而且，底层结构还增加了 红黑树，当数组中某一个索引位置上的元素 **以链表形式存的数据个数 > 8** 且 **当前数组的长度 > 64 时**，此时此索引位置上的所有数据改为红黑树存储。

***HashMap 源码分析**：

* Java7：
  
  ```java
  // Java7 HashMap

  ```

* Java8：

  ```java
  // Java8 HashMap

  ```

源码中的重要常量：

* `DEFAULT_INITIAL_CAPACITY` : HashMap 的默认容量，16
* `DEFAULT_LOAD_FACTOR`：HashMap 的默认加载因子，0.75
* `TREEIFY_THRESHOLD`：Bucket 中链表长度大于该默认值，转化为红黑树，8
* `MIN_TREEIFY_CAPACITY`：桶中的 Node 被树化时最小的 hash 表容量，64
* `threshold`：扩容的临界值，结果为容量 * 填充因子，12

### LinkedHashMap 类

LinkedHashMap 是 HashMap 的子类，它在 HashMap 存储结构的基础上，使用了一对**双向链表**来记录添加元素的顺序，与 LinkedHashSet 类似，LinkedHashMap 可以维护 Map 的迭代顺序，迭代顺序与 Key-Value 对的插入顺序一致。

```java
package parzulpan.com.java;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc :
 */

public class MapTest {

    @Test
    public void test1() {
        Map map = new HashMap();
        map.put(123, "AA");
        map.put(345, "OO");
        map.put("AAA", 1234);
        map.put(345, "WW");

        System.out.println(map);    // {AAA=1234, 345=WW, 123=AA}
    }

    @Test
    public void test2() {
        Map map = new LinkedHashMap();
        map.put(123, "AA");
        map.put(345, "OO");
        map.put("AAA", 1234);
        map.put(345, "WW");

        System.out.println(map);    // {123=AA, 345=WW, AAA=1234}
    }
}
```

### TreeMap 类

TreeMap 存储 key-value 对时，需要根据 key-value 对进行排序。
TreeMap 可以保证所有的 key-value 对处于**有序**状态。TreeSet 底层使用红黑树结构存储数据。

TreeMap 的 Key 的排序：

* **自然排序**：TreeMap 的所有的 key 必须实现 Comparable 接口，而且所有的 key 应该是**同一个类的对象**，否则将会抛出 ClasssCastException；
* **定制排序**：创建 TreeMap 时，传入一个 Comparator 对象，该对象负责对 TreeMap 中的所有 key 进行排序。此时不需要 Map 的 key 实现
Comparable 接口；
* 排序写法同 **TreeSet** 类似。

TreeMap 判断两个 key 相等的标准：两个 key 通过 compareTo() 方法或
者 compare() 方法返回 0。

### Hashtable 类

Hashtable 实现原理和 HashMap 相同，功能相同。底层都使用哈希表结构，查询速度快，很多情况下可以互用。

与 HashMap 不同，Hashtable 不允许使用 null 作为 key 和 value。

与 HashMap 一样，Hashtable 也不能保证其中 Key-Value 对的顺序。

### Properties 类

Properties 类是 Hashtable 的子类，该对象用于处理**属性文件**。

由于属性文件里的 key、value 都是字符串类型，所以 Properties 里的 key
和 value 都是**字符串**类型。

存取数据时，建议使用 `setProperty(String key,String value)` 方法和
 `getProperty(String key)` 方法。

```java
package parzulpan.com.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : Properties 处理配置文件
 */

public class PropertiesTest {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;

        try {
            Properties properties = new Properties();

            fileInputStream = new FileInputStream("jdbc.properties");
            properties.load(fileInputStream);

            String name = properties.getProperty("name");
            String password = properties.getProperty("password");

            System.out.println("name = " + name + ", password = " + password);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

## Collections 工具类

Collections 是一个操作 **Set**、**List** 和 **Map** 等集合的工具类。Collections 中提供了一系列**静态的方法**对集合元素进行排序、查询和修改等操作，还提供了对集合对象**设置不可变**、对集合对象**实现同步控制**等方法。

常用方法：

* 排序方法：
  * `reverse(List)`：反转 List 中元素的顺序
  * `shuffle(List)`：对 List 集合元素进行随机排序
  * `sort(List)`：根据元素的自然顺序对指定 List 集合元素按升序排序
  * `sort(List, Comparator)`：根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
  * `swap(List, int, int)`：将指定 List 集合中的 i 处元素和 j 处元素进行交换
* 查找、替换方法：
  * `Object max(Collection)`：根据元素的自然顺序，返回给定集合中的最大元素
  * `Object max(Collection, Comparator)`：根据 Comparator 指定的顺序，返回给定集合中的最大元素
  * `Object min(Collection)`
  * `Object min(Collection, Comparator)`
  * `int frequency(Collection, Object obj)`：返回指定集合中指定元素的出现次数
  * `void copy(List dest, List src)`：将 src 中的内容复制到 dest中，这个需要**注意**：

    ```java
    // Collections.copy() 使用
    @Test
    public void test2() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(123);
        arrayList.add(24);
        arrayList.add(644);
        arrayList.add(2412324);
        arrayList.add(2324);
        arrayList.add(2114);

        // 错误写法
        // java.lang.IndexOutOfBoundsException: Source does not fit in dest
        // ArrayList arrayList1 = new ArrayList();
        // Collections.copy(arrayList1, arrayList);

        // 正确写法
        List<Object> arrayList1 = Arrays.asList(new Object[arrayList.size()]);
        Collections.copy(arrayList1, arrayList);

        System.out.println(arrayList1);

    }

    ```

  * `boolean replaceAll(List list，Object oldVal，Object newVal)`：使用新值替换 List 对象的所有旧值
* 同步控制方法：提供了多个 `synchronizedXxx()` 方法，该方法可使将指定集合包装成线程同步的集合，从而可以解决多线程并发访问集合时的线程安全问题
  * `synchronizedCollection(Collection<T> c)`：
  * `synchronizedList(List<T> list)`：
  * `synchronizedMap(Map<K, V> m)`：
  * `synchronizedSet(Set<T> s)`：

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

**以下的输出结果？**

```java
package parzulpan.com.exer;

import java.util.HashSet;
import java.util.Objects;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc :
 */

public class HashSetTest {
    public static void main(String[] args) {
        HashSet set = new HashSet();
        PersonA p1 = new PersonA(1001,"AA");
        PersonA p2 = new PersonA(1002,"BB");
        set.add(p1);
        set.add(p2);
        p1.name = "CC";
        set.remove(p1);
        System.out.println(set);  // [PersonA{id=1002, name='BB'}, PersonA{id=1001, name='CC'}]
        set.add(new PersonA(1001,"CC"));
        System.out.println(set);  // [PersonA{id=1002, name='BB'}, PersonA{id=1001, name='CC'}, PersonA{id=1001, name='CC'}]
        set.add(new PersonA(1001,"AA"));
        System.out.println(set);  // [PersonA{id=1002, name='BB'}, PersonA{id=1001, name='CC'}, PersonA{id=1001, name='CC'}, PersonA{id=1001, name='AA'}]
    }
}

class PersonA {
    public int id;
    public String name;

    public PersonA() {
    }

    public PersonA(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonA)) return false;

        PersonA personA = (PersonA) o;

        if (id != personA.id) return false;
        return Objects.equals(name, personA.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
```

画图分析，考察 HashSet 添加元素流程。

---

**HashMap 的底层实现原理？**

* 对于 Java7:
  * `HashMap map = new HashMap();` 在实例化后，底层创建了一个长度为 16 的一维数组 `Entry[] table`。执行 `map.put(kay1, value1);` 时，首先调用 key1 所在类的 hasCode() 计算其哈希值，然后得到其在 Entry 数组中的存放位置：
    * 如果此位置上的数据为空，则 key1-value1 添加成功；（**情况1**）
    * 如果此位置上的数据不为空，意味着此位置上存在一个或多个数据（以链表形式），比较 key1 与它们的哈希值：
      * 如果 key1 和它们的哈希值都不相同，则  key1-value1 添加成功；（**情况2**）
      * 如果 key1 和某一个数据（key2-value2）的哈希值相同，则调用 equals()：
        * 如果 equals() 返回 false，则  key1-value1 添加成功；（**情况3**）
        * 如果 equals() 返回 true，则使用 value1 替换 value2；
  * 对于情况 2 和情况 3，此时的 key1-value1 和原来的数据以链表的方式存储。
  * 在不断的添加中，当超出临界值且原有位置不为空时，会涉及扩容问题，默认的扩容方式是扩容到原来容量的 2 倍，并将原来的数据复制过来。
* 对于 Java8：
  * 相比较于 Java7，实例化后底层没有创建一个长度为 16 的数组，首次 `put()` 后才创建长度为 16 的数组。且一维数组不是 `Entry[]` 而是 `Node[]`。而且，底层结构还增加了 红黑树，当数组中某一个索引位置上的元素 **以链表形式存的数据个数 > 8** 且 **当前数组的长度 > 64 时**，此时此索引位置上的所有数据改为红黑树存储。

---

**HashMap 和 Hashtable 的异同？**

* **HashMap**：作为 Map 接口的主要实现类；线程不安全的，效率高；可以存储 `null` 的 key 和 value；底层使用 数组+链表+红黑树（JDK8，JDK7无红黑树）存储；
* **Hashtable**：作为 Map 接口的古老实现类；线程安全的，效率低；不可以存储 `null` 的 key 和 value；

---

**CurrentHashMap 和 Hashtable 的异同？**

---

**谈谈你对 HashMap 中 put/get 方法的认识？如果了解再谈谈
 HashMap 的扩容机制？默认大小是多少？什么是负载因子（
或填充比）？什么是吞吐临界值（或阈值、threshold）？**

---

**负载因子值的大小，对 HashMap 有什么影响？**

* 负载因子的大小决定了 HashMap 的**数据密度**。
* 负载因子越大密度越大，发生碰撞的几率越高，数组中的链表越容易长,
造成查询或插入时的比较次数增多，性能会下降。
* 负载因子密度越小，就越容易触发扩容，数据密度也越小，意味着发生碰撞的
几率越小，数组中的链表也就越短，查询和插入时比较的次数也越小，性
能会更高。但是会浪费一定的内容空间。而且经常扩容也会影响性能，建
议初始化预设大一点的空间。
* 按照其他语言的参考及研究经验，会考虑将负载因子设置为 0.7~0.75，此时平均检索长度接近于常数。

---
