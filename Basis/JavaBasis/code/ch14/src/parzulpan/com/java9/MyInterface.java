package parzulpan.com.java9;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : 自定义接口
 */

public interface MyInterface {

    void methodAbstract();

    // 只能自己调用
    static void methodStatic() {
        System.out.println("我是接口中的静态方法");
    }

    default void methodDefault() {
        System.out.println("我是接口中的默认方法");
        methodPrivate();
    }

    // 不能在接口外部调用
    private void methodPrivate() {
        System.out.println("我是接口中的私有方法");
    }
}
