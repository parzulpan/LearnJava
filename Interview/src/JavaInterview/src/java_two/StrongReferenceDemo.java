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
        // o2 引用赋值
        Object o2 = o1;
        o1 = null;
        System.gc();
        // java.lang.Object@1b6d3586
        System.out.println(o2);
    }
}
