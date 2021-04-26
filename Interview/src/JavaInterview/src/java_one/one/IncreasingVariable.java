package java_one.one;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 1. 自增变量
 * i = 1
 * i = 2 j = 1
 * i = 4 k = 11
 * i = 4 k = 1 k = 11
 */

public class IncreasingVariable {
    public static void main(String[] args) {
        int i = 1;
        i = i++;
        int j = i++;
        int k = i + ++i * i++;
        System.out.println("i = " + i);
        System.out.println("j = " + j);
        System.out.println("k = " + k);
    }
}
