package java_one.two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 饿汉式 - 静态代码块
 * 这种方式通常适用于复杂的实例化
 */

public class Singleton3 {
    public static final Singleton3 INSTANCE;
    static {
        // ...
        INSTANCE = new Singleton3();
    }
}
