package java_one.five;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.five
 * @desc 5. 递归和迭代
 */

public class Fibonacci {
    private final int CONSTANT = 1000000007;

    public int numWaysByRecursion(int n) {
        if (n < 3) {
            return n;
        }
        return (numWaysByRecursion(n - 1) % CONSTANT + numWaysByRecursion(n - 2) % CONSTANT) % CONSTANT;
    }

    public int numWaysByIteration(int n) {
        int first = 0, second = 1, sum;
        for (int i = 0; i < n; ++i) {
            sum = (first + second) % CONSTANT;
            first = second;
            second = sum;
        }
        return second;
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.numWaysByRecursion(0));
        System.out.println(fibonacci.numWaysByRecursion(1));
        System.out.println(fibonacci.numWaysByRecursion(2));
        System.out.println(fibonacci.numWaysByRecursion(10));
        System.out.println();
        System.out.println(fibonacci.numWaysByIteration(0));
        System.out.println(fibonacci.numWaysByIteration(1));
        System.out.println(fibonacci.numWaysByIteration(2));
        System.out.println(fibonacci.numWaysByIteration(10));
    }
}
