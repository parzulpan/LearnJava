package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc : UML类图继承练习，测试类
 */

public class CylinderTest {
    public static void main(String[] args) {
        Cylinder cylinder = new Cylinder();
        cylinder.setRadius(2);
        cylinder.setLength(10);
        System.out.println("半径：" + cylinder.getRadius() + " 高：" + cylinder.getLength() + " 的圆柱体的体积为：" + cylinder.findVolume());
    }
}
