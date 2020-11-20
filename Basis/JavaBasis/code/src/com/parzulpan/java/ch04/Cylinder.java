package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc : UML类图继承练习，圆柱类
 */

public class Cylinder extends Circle{
    private double length;

    Cylinder() {
        System.out.println("Cylinder");
        length  = 1;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public double findVolume() {
        return findArea() * length;
    }
}
