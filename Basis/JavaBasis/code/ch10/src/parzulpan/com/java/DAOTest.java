package parzulpan.com.java;

import org.junit.Test;

import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc :
 */

public class DAOTest {
    @Test
    public void test1() {
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.add(new Customer());
        List<Customer> list = customerDAO.getList(10);

        ProducerDAO producerDAO = new ProducerDAO();
        producerDAO.remove(1);
        Producer index = producerDAO.getIndex(1);
    }
}
