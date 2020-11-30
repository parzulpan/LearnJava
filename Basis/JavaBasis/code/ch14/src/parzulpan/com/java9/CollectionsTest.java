package parzulpan.com.java9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : Java9 集合工厂方法：快速创建只读集合
 */

public class CollectionsTest {
    public static void main(String[] args) {
        // Java8
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add("Tom");
        arrayList.add("Jerry");
        arrayList.add("Hei");
        Collections.unmodifiableList(arrayList);
        System.out.println(arrayList);

        // Java9
        List<String> tom = List.of("Tom", "Jerry", "Hei");
        System.out.println(tom);
    }
}
