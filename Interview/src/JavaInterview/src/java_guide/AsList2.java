package java_guide;

import java.util.Arrays;
import java.util.List;

/**
 * @author parzulpan
 * 
 * Arrays.asList() 使用的注意事项：传递的数组必须是对象数组，而不能是基本类型数组
 */

public class AsList2 {
    public static void main(String[] args) {
        int[] myArray = {1, 2, 3};
        List<int[]> asList = Arrays.asList(myArray);

        // 1
        System.out.println(asList.size());
        // 数组地址值
        System.out.println(asList.get(0));
        // java.lang.ArrayIndexOutOfBoundsException
        System.out.println(asList.get(1));

        int[] array = (int[]) asList.get(0);
        // 1
        System.out.println(array[0]);
    }

}