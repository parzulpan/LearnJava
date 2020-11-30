package parzulpan.com.java10;

import java.util.ArrayList;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : Java10 局部变量类型推断
 */

public class LocalTypeInference {
    public static void main(String[] args) {
        //1.局部变量的初始化
        var list = new ArrayList<>();
        list.add("Tom");

        //2.增强for循环中的索引
        for(var v : list) {
            System.out.println(v);
        }

        //3.传统for循环中
        for(var i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
