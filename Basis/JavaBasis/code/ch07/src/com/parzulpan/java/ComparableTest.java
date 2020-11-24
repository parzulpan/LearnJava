package com.parzulpan.java;

import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 自然排序：java.lang.Comparable
 */

public class ComparableTest {
    public static void main(String[] args) {
        Commodity[] commodities = new Commodity[5];
        commodities[0] = new Commodity("dellMouse", 45);
        commodities[1] = new Commodity("xiaomiMouse", 23);
        commodities[2] = new Commodity("huaweiMouse", 65);
        commodities[3] = new Commodity("snakeMouse", 25);
        commodities[4] = new Commodity("xxMouse", 25);
        Arrays.sort(commodities);
        System.out.println(Arrays.toString(commodities));

    }
}


class Commodity implements Comparable {
    private String name;
    private double price;

    public Commodity(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    // 指明商品比较大小的方式
    @Override
    public int compareTo(Object o) {
        if (o instanceof Commodity) {
            Commodity commodity = (Commodity) o;
            if (this.price > commodity.price) {
                return 1;
            } else if (this.price < commodity.price) {
                return -1;
            } else {
                return this.name.compareTo(commodity.name);
            }
//            return Double.compare(this.price, commodity.price);
        }

        throw new RuntimeException("传入的数据类型不一致");
    }
}