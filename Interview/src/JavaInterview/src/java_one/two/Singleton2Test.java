package java_one.two;


/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc
 */

class Singleton2Test {
    public static void main(String[] args) {
        Singleton2 s1 = Singleton2.INSTANCE;
        Singleton2 s2 = Singleton2.INSTANCE;
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }
}