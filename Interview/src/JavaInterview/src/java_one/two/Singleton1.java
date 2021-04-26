package java_one.two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 饿汉式 - 直接实例化
 * 在类初始化时直接创建实例对象，不管是否需要这个对象都会创建
 * 1. 构造器私有化
 * 2. 自行创建，并用公有静态变量保存
 * 3. 向外提供这个实例
 */

public class Singleton1 {
    public static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() { }
}
