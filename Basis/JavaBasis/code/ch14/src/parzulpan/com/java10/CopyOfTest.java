package parzulpan.com.java10;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : Java10 集合新增创建不可变集合的方法
 */

public class CopyOfTest {
    public static void main(String[] args) {
        var list1 = List.of("Tom", "Jerry", "Hei");
        var copy1 = List.copyOf(list1);
        System.out.println(list1 == copy1); // true

        var list2 = new ArrayList<String>();
        var copy2 = List.copyOf(list2);
        System.out.println(list2 == copy2); // false
    }
}
