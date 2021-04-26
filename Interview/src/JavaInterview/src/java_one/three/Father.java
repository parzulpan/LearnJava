package java_one.three;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.three
 * @desc 3. 类初始化和实例初始化
 * 由父到子，静态先行，静态代码块 -> 非静态代码块 -> 构造函数
 */

public class Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.println("(1)");
    }
    Father() {
        System.out.println("(2)");
    }
    {
        System.out.println("(3)");
    }
    private int test() {
        System.out.println("(4)");
        return 1;
    }
    private static int method() {

        System.out.println("(5)");
        return 1;
    }
}
