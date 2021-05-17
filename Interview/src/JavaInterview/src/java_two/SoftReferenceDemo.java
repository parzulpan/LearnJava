package java_two;

import java.lang.ref.SoftReference;

/**
 * @author parzulpan
 *
 * 软引用
 * VM options: -Xms5m -Xmx5m -XX:+PrintGCDetails
 */

public class SoftReferenceDemo {
    public static void main(String[] args) {
        memoryEnough();
        System.out.println("\n---\n");
        memoryUnEnough();
    }

    private static void memoryEnough() {
        Object o = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o);
        System.out.println(o);
        System.out.println(softReference.get());
        System.out.println();
        o = null;
        System.gc();
        System.out.println(o);
        // java.lang.Object@4554617c
        System.out.println(softReference.get());
    }

    private static void memoryUnEnough() {
        Object o = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o);
        System.out.println(o);
        System.out.println(softReference.get());
        System.out.println();
        o = null;
        System.gc();
        try {
            // 堆空间压满
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o);
            // null
            System.out.println(softReference.get());
        }
    }
}
