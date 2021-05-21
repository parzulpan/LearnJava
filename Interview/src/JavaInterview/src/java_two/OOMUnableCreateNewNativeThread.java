package java_two;

/**
 * @author parzulpan
 *
 * OOM - unable to create new native thread
 * VM options: -XX:+PrintGCDetails
 */

public class OOMUnableCreateNewNativeThread {
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "" + i).start();
        }
    }
}
