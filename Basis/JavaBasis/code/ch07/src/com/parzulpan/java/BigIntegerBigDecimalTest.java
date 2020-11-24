package com.parzulpan.java;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc :
 */

public class BigIntegerBigDecimalTest {
    public static void main(String[] args) {
        BigInteger bigInteger1 = new BigInteger("1258917859013868315336326263475437543423623754374574547");
        BigInteger bigInteger2 = new BigInteger("8315336326263475437543423623754");
        System.out.println(bigInteger1);
        System.out.println(bigInteger2);
        System.out.println(bigInteger1.add(bigInteger2));

        BigDecimal bigDecimal1 = new BigDecimal("3264871935828081953801312516.325216432765437");
        BigDecimal bigDecimal2 = new BigDecimal("081953801312516.32");
        System.out.println(bigDecimal1);
        System.out.println(bigDecimal2);
        System.out.println(bigDecimal1.divide(bigDecimal2, BigDecimal.ROUND_HALF_UP));
        System.out.println(bigDecimal1.divide(bigDecimal2, 3, BigDecimal.ROUND_DOWN));
    }
}
