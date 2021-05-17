package java_two;

/**
 * @author parzulpan
 *
 * 强引用
 * VM options: -Xms5m -Xmx5m -XX:+PrintGCDetails
 */

public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        o1 = null;
        System.gc();
        System.out.println(o2);
    }
}
