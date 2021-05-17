package java_two;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author parzulpan
 *
 * WeakHashMap 和 HashMap
 * VM options: -Xms5m -Xmx5m -XX:+PrintGCDetails
 */

public class WeakHashMapDemo {
    public static void main(String[] args) {
        testHashMap();
        System.out.println("\n---\n");
        testWeakHashMap();
    }

    private static void testHashMap() {
        HashMap<Integer, String> map = new HashMap<>();
        Integer key = 1024;
        String value = "HashMap";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map + "\t" + map.size());
    }

    private static void testWeakHashMap() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        Integer key = 1024;
        String value = "WeakHashMap";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map + "\t" + map.size());
    }
}
