package cn.parzulpan;

/**
 * @author parzulpan
 *
 * 类加载器的双亲委派和沙箱安全
 */

public class MyObject {
    public static void main(String[] args) {
        // 自定义 Object
        MyObject myObject = new MyObject();
        // sun.misc.Launcher 是 JVM 相关调用的入口程序
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(myObject.getClass().getClassLoader());
        // sun.misc.Launcher$ExtClassLoader@1b6d3586
        System.out.println(myObject.getClass().getClassLoader().getParent());
        // null
        System.out.println(myObject.getClass().getClassLoader().getParent().getParent());

        System.out.println();

        // JDK 的 Object
        Object o = new Object();
        // null
        System.out.println(o.getClass().getClassLoader());
        // java.lang.NullPointerException
        System.out.println(o.getClass().getClassLoader().getParent());
        // java.lang.NullPointerException
        System.out.println(o.getClass().getClassLoader().getParent().getParent());

        new Thread().start();
    }
}
