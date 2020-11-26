package parzulpan.com.exer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : 在 List 内去除重复数字值
 */

public class ListTest {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new Integer(1));
        list.add(new Integer(1));
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(2));
        list.add(new Integer(1));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(3));

        List list1 = duplicateList(list);
        for (Object l:
             list1) {
            System.out.println(l);
        }

    }

    public static List duplicateList(List list) {
        HashSet hashSet = new HashSet();
        hashSet.addAll(list);
        return new ArrayList(hashSet);
    }
}
