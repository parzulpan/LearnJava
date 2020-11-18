package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 对象数组题目：
 * 定义类Student，包含三个属性：学号number(int)，年级state(int)，成绩
 * score(int)。 创建20个学生对象，学号为1到20，年级和成绩都由随机数确定。
 * 问题一：打印出3年级(state值为3）的学生信息。
 * 问题二：使用冒泡排序按学生成绩排序，并遍历所有学生信息
 */

public class Test {
    public static void main(String[] args) {
        Student[] students = new Student[20];

        //
        for (int i = 1; i < 21; ++i) {
            Student s = new Student();
            s.set(i, (int) (Math.random() * (6 - 1 + 1) + 1),(int) (Math.random() * (100 + 1)));
            students[i - 1] = s;
        }

        //
        for (Student student : students) {
            if (student.getState() == 3) {
                student.printInfo();
            }
        }

        System.out.println();

        //
        bubbleSort(students);
        for (Student student : students) {
                student.printInfo();
        }
    }



    public static void bubbleSort(Student[] s) {

        int len = s.length;
        for (int i = 0; i < len; ++i) {
            boolean flag = false;
            for (int j = 0; j < len - 1 - i; ++j) {
                if (s[j].getScore() > s[j + 1].getScore()) {
                    Student temp = s[j + 1];
                    s[j + 1] = s[j];
                    s[j] = temp;
                    flag = true;
                }
            }
            if(!flag) {
                break;
            }
        }
    }
}


class Student {

    private int number;
    private int state;
    private int score;

    public void set(int number, int state, int score) {
        this.number = number;
        this.state = state;
        this.score = score;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {

        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void printInfo() {
        System.out.println("学号：" + number +  " 年级：" + state + " 成绩：" + score);
    }
}