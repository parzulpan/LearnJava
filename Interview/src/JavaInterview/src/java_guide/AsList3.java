package java_guide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author parzulpan
 * 
 * Arrays.asList() 正确的使用方式
 */

public class AsList3 {
    public static void main(String[] args) {
        method1();
        System.out.println();
        method2();
        
    }

    /**
     * 直接强转（推荐）
     */
    public static void method1() {
        List<String> arrayList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        System.out.println(arrayList.size());
        arrayList.add("DD");
        System.out.println(arrayList.size());
    }

    /**
     * 使用 Java8 的 Stream（推荐）
     */
    public static void method2() {
        Integer[] array = {1, 2, 3};
        List<Integer> collect = Arrays.stream(array).collect(Collectors.toList());
        System.out.println(collect.size());
        collect.add(4);
        System.out.println(collect.size());

        // 依赖 boxed 的装箱操作，基本类型数组也可以实现转换
        int[] array2 = {1, 2, 3};
        List<Integer> collect2 = Arrays.stream(array2).boxed().collect(Collectors.toList());
        System.out.println(collect2.size());
        collect2.add(4);
        System.out.println(collect2.size());
    }
    
}
