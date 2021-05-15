package cn.parzulpan;

import java.util.Random;

/**
 * @author parzulpan
 *
 * 堆溢出 OutOfMemoryError
 */

public class OOMTest {
    public static void main(String[] args) {
        String s = "OOM";
        while (true) {
            // 每执行下面语句，会在堆里创建新的对象
            s += s + new Random().nextInt(888888) + new Random().nextInt(999999);
        }
    }
}
