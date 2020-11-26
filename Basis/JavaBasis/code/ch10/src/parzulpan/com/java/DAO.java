package parzulpan.com.java;

import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : DAO：base（data） access object
 */

// 通型操作
public class DAO<T> {

    // 增加一条记录
    public void add(T t) {

    }

    // 删除一条记录
    public boolean remove(int index) {

        return false;
    }

    // 修改一条记录
    public void update(int index, T t) {

    }

    // 查询一条记录
    public T getIndex(int index) {

        return null;
    }

    // 查询多条记录
    public List<T> getList(int index) {

        return null;
    }

    // 泛型方法，不能为 static
    public <E> E getValueOfT(T t) {

        return null;
    }

    // 泛型方法，可以为 static
    public static <E> E getValue() {

        return null;
    }
}

// 对应 Customer 表
class Customer {

}

// 只能操作 Customer 表
class CustomerDAO extends DAO<Customer> {

}

// 对应 Producer 表
class Producer {

}

class ProducerDAO extends  DAO<Producer> {

}