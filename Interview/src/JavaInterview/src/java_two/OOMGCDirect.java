package java_two;

import sun.misc.VM;

import java.nio.ByteBuffer;

/**
 * @author parzulpan
 *
 * OOM - GC Direct buffer memory
 * VM options: -Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
 */

public class OOMGCDirect {
    public static void main(String[] args) {
        System.out.println("MaxDirectMemorySize = " + (VM.maxDirectMemory() / 1024 / 1024) + "M");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
