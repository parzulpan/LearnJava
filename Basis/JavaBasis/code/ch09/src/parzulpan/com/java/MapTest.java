package parzulpan.com.java;

import org.junit.Test;

import java.util.*;

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
}
