package java_guide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author parzulpan
 *
 * 不要在 foreach 循环中进行元素的 remove/add 操作
 */

public class Foreach {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }

        // 源码实际也是用的迭代器
        list.removeIf(filter -> filter % 2 == 0);
        System.out.println(list);
        
        // 错误使用，java.util.ConcurrentModificationException
        for (Integer integer : list) {
            if (1 == integer) {
                list.remove(integer);
            }
        }
        System.out.println(list);

    }

}
