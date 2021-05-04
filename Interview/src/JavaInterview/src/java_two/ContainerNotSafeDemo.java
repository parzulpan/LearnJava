package java_two;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 集合线程安全性说明
 */

public class ContainerNotSafeDemo {
    public static void main(String[] args) {
//        arrayListNotSafe();
//        LinkedListNotSafe();
//        VectorSafe();
//        hashSetNotSafe();
//        linkedHashSetNotSafe();
//        treeSetNotSafe();
//        hashMapNotSafe();
//        linkedHashMapNotSafe();
//        treeMapSafe();
        hashTableNotSafe();

    }

    /**
     * ArrayList 线程不安全，使用 CopyOnWriteArrayList 解决线程安全问题
     */
    public static void arrayListNotSafe() {
//        List<String> list = new ArrayList<>();
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * LinkedList 线程不安全，使用 CopyOnWriteArrayList 解决线程安全问题
     */
    public static void LinkedListNotSafe() {
//        List<String> list = new LinkedList<>();
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * Vector 线程安全
     */
    public static void VectorSafe() {
        List<String> list = new Vector<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * HashSet 线程不安全，使用 CopyOnWriteArraySet 解决线程安全问题
     * CopyOnWriteArraySet 底层维护了一个CopyOnWriteArrayList数组。
     */
    public static void hashSetNotSafe() {
//        Set<String> set = new HashSet<>();
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * LinkedHashSet 线程不安全，使用 CopyOnWriteArraySet 解决线程安全问题
     */
    public static void linkedHashSetNotSafe() {
//        Set<String> set = new LinkedHashSet<>();
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * TreeSet 线程不安全，使用 CopyOnWriteArraySet 解决线程安全问题
     */
    public static void treeSetNotSafe() {
//        Set<String> set = new TreeSet<>();
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * HashMap 线程不安全，使用 ConcurrentHashMap 解决线程安全问题
     */
    public static void hashMapNotSafe() {
//        Map<String, String> map = new HashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * LinkedHashMap 线程不安全，使用 ConcurrentHashMap 解决线程安全问题
     */
    public static void linkedHashMapNotSafe() {
//        Map<String, String> map = new LinkedHashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * TreeMap 线程不安全，使用 ConcurrentHashMap 解决线程安全问题
     */
    public static void treeMapSafe() {
//        Map<String, String> map = new TreeMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * Hashtable 线程安全
     */
    public static void hashTableNotSafe() {
        Map<String, String> map = new Hashtable<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

}
