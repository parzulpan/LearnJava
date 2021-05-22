package java_guide;

/**
 * @author parzulpan
 *
 * synchronized 关键字 应用：单例模式-线程安全双重校验锁
 */

public class Singleton {
    /** valatile 防止指令重拍 */
    private volatile static Singleton instance;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized(Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
