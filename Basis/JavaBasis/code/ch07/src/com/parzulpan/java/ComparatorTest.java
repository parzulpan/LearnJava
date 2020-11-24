package com.parzulpan.java;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc :
 */

public class ComparatorTest {
    public static void main(String[] args) {
        Commodity[] commodities = new Commodity[5];
        commodities[0] = new Commodity("dellMouse", 45);
        commodities[1] = new Commodity("xiaomiMouse", 23);
        commodities[2] = new Commodity("huaweiMouse", 65);
        commodities[3] = new Commodity("snakeMouse", 25);
        commodities[4] = new Commodity("xxMouse", 25);
        Arrays.sort(commodities, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Commodity && o2 instanceof Commodity) {
                    Commodity c1 = (Commodity)  o1;
                    Commodity c2 = (Commodity)  o2;
                    return -c1.compareTo(c2);
                }
                throw new RuntimeException("输入的数据类型不一致");
            }
        });
        System.out.println(Arrays.toString(commodities));

    }
}