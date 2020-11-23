package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 用户自定义异常类
 */

public class MyExceptionTest {
    public static void main(String[] args) {
        MyExceptionTest myExceptionTest = new MyExceptionTest();
        myExceptionTest.manager();
    }

    public void add(int num) throws MyException{
        if (num < 0) {
            throw new MyException("人数为负值，不合理", 3);
        } else {
            System.out.println("登记人数 " + num);
        }
    }

    public void manager() {
        try {
            add(-10);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            System.out.println("登记失败，出错种类 " + e.getId());
        }

        System.out.println("本次登记操作结束");
    }
}


class MyException extends Exception {
    static final long serialVersionUID = 2165326351631265L;
    private int id;

    public MyException(String msg, int id) {
        super(msg);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

