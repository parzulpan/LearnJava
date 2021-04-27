package java_one.three;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.three
 * @desc 3. 类初始化和实例初始化
 */

public class Test {
    public static void main(String[] args) {
        // 正确输出 5 1 10 6 9 3 2 9 8 7
        Son s1 = new Son();
        System.out.println();
        // 正确输出 9 3 2 9 8 7
        Son s2 = new Son();
    }
}
