package parzulpan.com.java;

import org.junit.Test;

import java.util.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-25
 * @Desc : Collection 接口 使用示例
 */

public class CollectionTest {

    @Test
    public void test1() {
        Collection collection = new ArrayList();

        // add(Object obj)  将元素/对象 e 添加到集合
        collection.add("AA");
        collection.add(123);    // 自动装箱
        collection.add(12314.5546);
        collection.add(new Date());

        // size()   获取有效元素的个数
        System.out.println(collection.size());  // 4

        // addAll(Collection c) 将 c 集合中的元素添加到当前集合中
        Collection collection1 = new ArrayList();
        collection1.add(456);
        collection.add(collection1);
        System.out.println(collection.size());  // 5
        System.out.println(collection);

        // clear() 清空集合元素
//        collection1.clear();

        // isEmpty() 判断当前集合是否为空，即是否有元素
        System.out.println(collection1.isEmpty());   // true

        // contains(Object obj) 是否包含某个元素，是通过元素的 equals 方法来判断是否是同一个对象
        System.out.println(collection.contains("456"));    // false

        // containsAll(Collection c)    是否包含某个元素，是调用元素的 equals 方法来比较的，拿两个集合的元素逐个比较
        System.out.println(collection.containsAll(collection1));    // true

        // remove(Object obj)   通过元素的 equals 方法判断是否是要删除的那个元素，只会删除找到的第一个元素
        System.out.println(collection.size()); // 5
        collection.remove(123);
        System.out.println(collection.size());

        // removeAll(Collection c)  取当前集合的差集
        System.out.println(collection);
        System.out.println(collection1);
        System.out.println(collection.removeAll(collection1));
        System.out.println(collection);

        // retainAll(Collection c)  把交集的结果存在当前集合中，不影响 c

        // equals(Object obj)   集合是否相等

        // Object[] toArray()   转成对象数组
        System.out.println(collection.toArray().length);

        // hashCode()   获取集合对象的哈希值
        System.out.println(collection.hashCode());

        // iterator()   返回迭代器对象，用于集合遍历
        System.out.println(collection.iterator());

    }

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

//        // java.lang.IndexOutOfBoundsException: Source does not fit in dest
//        ArrayList arrayList1 = new ArrayList();
//        Collections.copy(arrayList1, arrayList);

        // 正确写法
        List<Object> arrayList1 = Arrays.asList(new Object[arrayList.size()]);
        Collections.copy(arrayList1, arrayList);


        System.out.println(arrayList1);

    }


}
