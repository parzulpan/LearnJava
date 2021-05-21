package java_two;

import java.util.ArrayList;
import java.util.List;

/**
 * @author parzulpan
 *
 * OOM - GC overhead limit exceeded
 * VM options: -Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
 */

public class OOMGCOverhead {
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Exception e) {
            System.out.println(" i = " + i);
            e.printStackTrace();
            throw e;
        }
    }
}
