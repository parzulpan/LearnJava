package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 抽象类的应用：模版方法的设计模式
 */

public class TemplateMethodTest1 {
    public static void main(String[] args) {
        SubTemplate subTemplate = new SubTemplate();
        subTemplate.getTime();
    }
}

abstract class Template {
    // 计算某段代码执行花费的时间
    public final void getTime() {
        long start = System.currentTimeMillis();
        code();
        long end = System.currentTimeMillis();
        System.out.println("执行的时间是：" + (end - start));
    }

    public abstract void code();
}

class SubTemplate extends Template {
    public void code() {
        for (int i = 0; i < 10000; ++i) {
            System.out.println(i);
        }
    }
}