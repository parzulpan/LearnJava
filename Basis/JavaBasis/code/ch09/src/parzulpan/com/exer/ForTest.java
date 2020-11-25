package parzulpan.com.exer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-25
 * @Desc :
 */

public class ForTest {
    public static void main(String[] args) {
        String[] str = new String[5];
        for (String myStr : str) {
            myStr = "AA";
            System.out.println(myStr);  // AA
        }

        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]); // null
        }
    }

    @Test
    public void testListRemove() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        updateList(list);
        System.out.println(list); // [1, 2]
    }
    private static void updateList(List list) {
        list.remove(2);
    }
}
