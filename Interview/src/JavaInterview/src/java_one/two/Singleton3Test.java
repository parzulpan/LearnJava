package java_one.two;


/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc
 */

class Singleton3Test {
    public static void main(String[] args) {
        Singleton3 s1 = Singleton3.INSTANCE;
        Singleton3 s2 = Singleton3.INSTANCE;
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }
}