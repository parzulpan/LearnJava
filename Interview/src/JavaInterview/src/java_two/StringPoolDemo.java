package java_two;

/**
 * @author parzulpan
 *
 * 字符串常量池、intern() 方法
 */

public class StringPoolDemo {
    public static void main(String[] args) {
        String s1 = new StringBuilder("parzul").append("pan").toString();
        System.out.println(s1);
        System.out.println(s1.intern());
        // true
        System.out.println(s1 == s1.intern());
        // true
        System.out.println(s1.equals(s1.intern()));

        System.out.println();

        String s2 = new StringBuilder("ja").append("va").toString();
        System.out.println(s2);
        System.out.println(s2.intern());
        // false
        System.out.println(s2 == s2.intern());
        // true
        System.out.println(s2.equals(s2.intern()));
    }
}
