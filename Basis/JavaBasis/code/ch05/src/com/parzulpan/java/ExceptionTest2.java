package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 手动抛出异常
 */

public class ExceptionTest2 {
    public static void main(String[] args) {
//        StudentAA studentAA = new StudentAA();
//        studentAA.add(-10);
//        System.out.println(studentAA);  // 请输入的数据非法！StudentAA{id=0}
        try {
            StudentAA studentAA = new StudentAA();
            studentAA.add(-10);
            System.out.println(studentAA);  // 请输入的数据非法！
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

class StudentAA {
    private int id;

    public void add(int id) throws Exception{
        if (id > 0) {
            this.id = id;
        } else {
//            System.out.println("请输入的数据非法！");
            // 手动抛出异常
            throw new Exception("请输入的数据非法！");
        }
    }

    @Override
    public String toString() {
        return "StudentAA{" +
                "id=" + id +
                '}';
    }
}