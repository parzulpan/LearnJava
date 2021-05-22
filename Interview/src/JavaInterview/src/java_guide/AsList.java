package java_guide;

import java.util.Arrays;
import java.util.List;

/**
 * @author parzulpan
 * 
 * Arrays.asList() 使用的注意事项：不能使用其修改集合相关的方法
 */

public class AsList {
    public static void main(String[] args) {
        String[] myArray = { "AA", "BB", "CC" };
        // 等价于 List<String> myList = Arrays.asList("Apple", "Banana", "Orange");
        List<String> myList = Arrays.asList(myArray);

        // AA
        System.out.println(myList.get(0));

        // java.lang.UnsupportedOperationException
        // myList.add("DD");

        myArray[0] = "EE";

        // EE
        System.out.println(myList.get(0)); 


    }

}
