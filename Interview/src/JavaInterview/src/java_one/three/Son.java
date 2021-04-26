package java_one.three;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.three
 * @desc
 */

public class Son extends Father{
    private int i = test();
    private static int j = method();

    static {
        System.out.println("(6)");
    }
    Son() {
        System.out.println("(7)");
    }
    {
        System.out.println("(8)");
    }
    private int test() {
        System.out.println("(9)");
        return 1;
    }
    private static int method() {

        System.out.println("(10)");
        return 1;
    }
}
