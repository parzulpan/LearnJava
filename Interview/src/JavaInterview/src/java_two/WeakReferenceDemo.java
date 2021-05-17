package java_two;

import java.lang.ref.WeakReference;

/**
 * @author parzulpan
 *
 * 弱引用
 * VM options: -Xms5m -Xmx5m -XX:+PrintGCDetails
 */

public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o);
        System.out.println(o);
        System.out.println(weakReference.get());
        System.out.println("\n---\n");
        o = null;
        System.gc();
        System.out.println(o);
        // null
        System.out.println(weakReference.get());
    }
}
