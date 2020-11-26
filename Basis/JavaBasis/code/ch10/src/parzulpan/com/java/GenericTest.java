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
