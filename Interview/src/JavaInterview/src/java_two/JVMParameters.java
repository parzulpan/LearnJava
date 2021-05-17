package java_two;

/**
 * @author parzulpan
 *
 * JVM 参数
 */

public class JVMParameters {
    public static void main(String[] args) {
        try {
            Thread.sleep(100000);
            System.out.println("JVMParameters");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
