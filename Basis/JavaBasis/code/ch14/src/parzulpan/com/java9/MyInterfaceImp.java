package parzulpan.com.java9;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc :  Java9 接口的私有方法
 */

public class MyInterfaceImp implements MyInterface {
    @Override
    public void methodAbstract() {
        System.out.println("我实现接口中的方法");
    }

    @Override
    public void methodDefault() {
        System.out.println("我实现了接口中的默认方法");
    }

    public static void main(String[] args) {
        MyInterfaceImp myInterfaceImp = new MyInterfaceImp();
        myInterfaceImp.methodAbstract();
        myInterfaceImp.methodDefault();
    }
}
