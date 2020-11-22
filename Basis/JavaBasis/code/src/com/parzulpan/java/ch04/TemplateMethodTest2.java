package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 抽象类的应用：模版方法的设计模式
 */

public class TemplateMethodTest2 {
    public static void main(String[] args) {
        BankTemplateMethod bankTemplateMethod = new DrewMoney();
        bankTemplateMethod.process();
        BankTemplateMethod bankTemplateMethod1 = new ManageMoney();
        bankTemplateMethod1.process();
    }

}


abstract class BankTemplateMethod {

    private int id;
    private static int init = 0;

    BankTemplateMethod() {
        super();
        id = init++;
    }

    // 具体方法
    public void takeNumber() {
        System.out.println("取号排队 " + this.id);
    }
    public void evaluate() {
        System.out.println("反馈评分\n");
    }

    // 钩子方法，办理具体的业务
    public abstract void transact();

    // 模版方法，把基本操作组合到一起，子类一般不能重写
    public final void process() {
        this.takeNumber();
        this.transact();
        this.evaluate();
    }
}

class DrewMoney extends BankTemplateMethod {
    @Override
    public void transact() {
        System.out.println("我要取款");
    }
}

class ManageMoney extends BankTemplateMethod {
    @Override
    public void transact() {
        System.out.println("我要理财");
    }
}