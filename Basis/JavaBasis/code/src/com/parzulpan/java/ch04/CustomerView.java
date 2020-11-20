package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc : Java 基础项目二：客户信息管理软件。
 * CustomerView 为主模块，负责菜单的显示和处理用户操作
 */

public class CustomerView {

    private CustomerList customers = new CustomerList(10);

    public CustomerView() {
        Customer customer = new Customer("张三", '男', 30, "010-12415323", "abc@qq.com");
        customers.addCustomer(customer);
    }

    public void enterMainMenu() {
        boolean loopFlag = true;

        do {
            System.out.println("-----------------客户信息管理软件-----------------\n" +
                    "\n" +
                    "                1 添 加 客 户\n" +
                    "                2 修 改 客 户\n" +
                    "                3 删 除 客 户\n" +
                    "                4 客 户 列 表\n" +
                    "                5 退       出\n" +
                    "\n" +
                    "                请选择(1-5)：");

            char key = CustomerUtility.readMenuSelection();
            System.out.println();
            switch (key) {
                case '1':
                    addNewCustomer();
                    break;
                case '2':
                    modifyCustomer();
                    break;
                case '3':
                    deleteCustomer();
                    break;
                case '4':
                    listAllCustomers();
                    break;
                case '5':
                    System.out.print("确定是否退出（Y/N）：");
                    char exit = CustomerUtility.readConfirmSelection();
                    if (exit == 'Y') {
                        loopFlag = false;
                    }
                    break;
            }
        } while (loopFlag);
    }

    private void addNewCustomer() {
        System.out.println("---------------------添加客户---------------------");
        System.out.print("姓名：");
        String name = CustomerUtility.readString(4);
        System.out.print("性别：");
        char gender = CustomerUtility.readChar();
        System.out.print("年龄：");
        int age = CustomerUtility.readInt();
        System.out.print("电话：");
        String number = CustomerUtility.readString(15);
        System.out.print("邮箱：");
        String email = CustomerUtility.readString(15);
        Customer customer = new Customer(name, gender, age, number, email);
        boolean flag = customers.addCustomer(customer);
        if (flag) {
            System.out.println("---------------------添加完成---------------------");
        } else {
            System.out.println("----------------记录已满！添加失败！----------------");
        }
    }

    private void modifyCustomer() {
        System.out.println("---------------------修改客户---------------------");

        int index = 0;
        Customer cust = null;
        for (;;) {
            System.out.print("请选择待修改客户编号(-1退出)：");
            index = CustomerUtility.readInt();
            if (index == -1) {
                return;
            }

            cust = customers.getCustomer(index - 1);
            if (cust == null) {
                System.out.println("无法找到指定客户！");
            } else
                break;
        }

        System.out.print("姓名(" + cust.getName() + ")：");
        String name = CustomerUtility.readString(4, cust.getName());

        System.out.print("性别(" + cust.getGender() + ")：");
        char gender = CustomerUtility.readChar(cust.getGender());

        System.out.print("年龄(" + cust.getAge() + ")：");
        int age = CustomerUtility.readInt(cust.getAge());

        System.out.print("电话(" + cust.getNumber() + ")：");
        String phone = CustomerUtility.readString(15, cust.getNumber());

        System.out.print("邮箱(" + cust.getEmail() + ")：");
        String email = CustomerUtility.readString(15, cust.getEmail());

        cust = new Customer(name, gender, age, phone, email);

        boolean flag = customers.modifyCustomer(index - 1, cust);
        if (flag) {
            System.out
                    .println("---------------------修改完成---------------------");
        } else {
            System.out.println("----------无法找到指定客户,修改失败--------------");
        }
    }

    private void deleteCustomer() {
        System.out.println("---------------------删除客户---------------------");

        int index = 0;
        Customer cust = null;
        for (;;) {
            System.out.print("请选择待删除客户编号(-1退出)：");
            index = CustomerUtility.readInt();
            if (index == -1) {
                return;
            }

            cust = customers.getCustomer(index - 1);
            if (cust == null) {
                System.out.println("无法找到指定客户！");
            } else
                break;
        }

        System.out.print("确认是否删除(Y/N)：");
        char yn = CustomerUtility.readConfirmSelection();
        if (yn == 'N')
            return;

        boolean flag = customers.deleteCustomer(index - 1);
        if (flag) {
            System.out
                    .println("---------------------删除完成---------------------");
        } else {
            System.out.println("----------无法找到指定客户,删除失败--------------");
        }
    }

    private void listAllCustomers() {
        System.out.println("---------------------------客户列表---------------------------");
        Customer[] custs = customers.getCustomers();
        if (custs.length == 0) {
            System.out.println("没有客户记录！");
        } else {
            System.out.println("编号\t\t姓名\t\t性别\t\t年龄\t\t电话\t\t邮箱");
            for (int i = 0; i < custs.length; i++) {
                System.out.println((i+1) + "\t" + custs[i].printInfo());
            }
        }

        System.out.println("-------------------------客户列表完成-------------------------");
    }

    public static void main(String[] args) {
        CustomerView customerView = new CustomerView();
        customerView.enterMainMenu();
    }
}
