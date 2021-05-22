package java_guide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author parzulpan
 *
 * Collection.toArray() 使用的注意事项
 */

public class ToArray {
    public static void main(String[] args) {
        String[] str = {"aa", "bb", "cc"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(str));
        System.out.println(arrayList);

        Collections.reverse(arrayList);
        System.out.println(arrayList);

        // 没有指定类型的话会报错
        str = arrayList.toArray(new String[0]);
        for (String s : str) {
            System.out.println(s);
        }
    }
    
}
