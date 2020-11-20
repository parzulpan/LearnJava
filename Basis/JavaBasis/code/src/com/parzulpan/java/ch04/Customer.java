package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc : Customer 为实体对象，用来封装客户信息。
 */

public class Customer {
    private String name;
    private char gender;
    private int age;
    private String number;
    private String email;

    Customer(String name, char gender, int age, String number, String email) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setName(int age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String printInfo() {
        return name + "\t" + gender + "\t" + age + "\t" + number + "\t" + email ;
    }

}
