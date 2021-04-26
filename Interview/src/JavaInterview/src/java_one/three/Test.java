package java_one.three;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.three
 * @desc
 */

public class Test {
    public static void main(String[] args) {
        // 错误输出 5 4 1 3 2 10 9 6 8 7
        // 正确输出 5 1 10 6 4 3 2 9 8 7
        Son s1 = new Son();
        System.out.println();
        // 正确输出 4 3 2 9 8 7
        Son s2 = new Son();
    }
}
