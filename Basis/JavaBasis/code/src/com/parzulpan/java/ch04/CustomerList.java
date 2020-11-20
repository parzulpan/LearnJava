package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc : CustomerList 为 Customer 对象的管理模块，内部用数组管理一组Customer 对象，
 * 并提供相应的添加、修改、删除和遍历方法，供 CustomerView 调用；
 */

public class CustomerList {
    private Customer[] customers;
    private int customerTotal;

    CustomerList(int customerTotal) {
        this.customers = new Customer[customerTotal];
    }

    public boolean addCustomer(Customer customer) {
        if (customerTotal >= customers.length) {
            return false;
        }

        customers[customerTotal++] = customer;

        return true;
    }

    public boolean modifyCustomer(int index, Customer customer) {
        if (index < 0  || index >= customerTotal) {
            return false;
        }

        customers[customerTotal] = customer;

        return true;
    }

    public boolean deleteCustomer(int index) {
        if (index < 0 || index >= customerTotal) {
            return false;
        }

        for (int i = index; i < customerTotal - 1; ++i) {
            customers[i] = customers[i + 1];
        }

        customers[--customerTotal] = null;

        return true;
    }

    public Customer[] getCustomers() {
        Customer[] custs = new Customer[customerTotal];
        for (int i = 0; i < customerTotal; ++i) {
            custs[i] = customers[i];
        }
        return custs;
    }

    public Customer getCustomer(int index) {
        if (index < 0 || index >= customerTotal) {
            return null;
        }

        return customers[index];
    }

    public int getCustomerTotal() {
        return customerTotal;
    }

}
