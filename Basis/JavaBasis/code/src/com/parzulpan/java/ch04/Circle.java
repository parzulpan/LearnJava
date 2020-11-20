package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc : UML类图继承练习，圆类
 */

public class Circle {
    private double radius;

    Circle() {
        System.out.println("Circle");
        radius = 1.0;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double findArea() {
        return Math.PI * Math.pow(radius, 2.0);
    }
}
