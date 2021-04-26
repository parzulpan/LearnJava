package java_one.two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 懒汉式 - 线程不安全，适用于单线程
 * 延迟创建实例对象
 * 1. 构造器私有化
 * 2. 用一个私有静态变量保存这个唯一的实例
 * 3. 提供一个静态方法，获取这个实例对象
 */

public class Singleton4 {
    private static Singleton4 instance;
    private Singleton4() { }
    public static Singleton4 getInstance() {
        if (instance == null) {
            // ...
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new Singleton4();
        }
        return instance;
    }
}
