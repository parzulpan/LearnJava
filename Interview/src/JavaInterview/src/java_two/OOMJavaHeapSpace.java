package java_two;

import java.util.Random;

/**
 * @author parzulpan
 *
 * OOM - Java heap space
 * VM options: -Xms5m -Xmx5m -XX:+PrintGCDetails
 */

public class OOMJavaHeapSpace {
    public static void main(String[] args) {
        String str = "oom";
        while (true) {
            str += str + new Random().nextInt(111111) + new Random().nextInt(999999);
            // System.out.println(str);
        }
    }
}
