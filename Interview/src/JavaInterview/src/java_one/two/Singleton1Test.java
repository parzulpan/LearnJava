package java_one.two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc
 */

class Singleton1Test {
    public static void main(String[] args) {
        Singleton1 s1 = Singleton1.INSTANCE;
        Singleton1 s2 = Singleton1.INSTANCE;
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }
}