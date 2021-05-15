package cn.parzulpan;

/**
 * @author parzulpan
 *
 * 获取虚拟机的相关内存信息
 */

public class JVMMemory {
    public static void main(String[] args) {
        // 返回 Java 虚拟机试图使用的最大内存量
        long maxMemory = Runtime.getRuntime().maxMemory();
        // MAX_MEMORY = 3668967424（字节）、3499.0MB
        System.out.println("MAX_MEMORY = " + maxMemory + "（字节）、" + (maxMemory / (double) 1024 / 1024) + "MB");

        // 返回 Java 虚拟机中的内存总量
        long totalMemory = Runtime.getRuntime().totalMemory();
        // TOTAL_MEMORY = 247463936（字节）、236.0MB
        System.out.println("TOTAL_MEMORY = " + totalMemory + "（字节）、" + (totalMemory / (double) 1024 / 1024) + "MB");
    }
}
