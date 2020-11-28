package parzulpan.com.java;

import java.io.Serializable;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 获取运行时类的完整结构 - 父类
 */

public class MyCreature<T> implements Serializable {
    private char gender;
    public double widget;

    private void breath() {
        System.out.println("生物呼吸");
    }

    public void eat() {
        System.out.println("生物吃东西");
    }
}
