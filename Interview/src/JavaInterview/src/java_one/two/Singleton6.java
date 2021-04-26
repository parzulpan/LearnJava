package java_one.two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 懒汉式 - 静态内部类，线程安全，适用于多线程
 * 当内部类被加载和初始化时，才会创建实例对象
 */

public class Singleton6 {
    private Singleton6() {}
    private static class Inner {
        private static final Singleton6 INSTANCE = new Singleton6();
    }
    public static Singleton6 getInstance() {
        return Inner.INSTANCE;
    }
}
