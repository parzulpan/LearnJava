package java_two;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author parzulpan
 *
 * 两数求和最高效的解放
 */

public class TwoSum {
    public static void main(String[] args) {
        int[] arr = {2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(new TwoSum().twoSum(arr, target)));
    }

    HashMap<Integer, Integer> valueToIndex = new HashMap<>();

    public int[] twoSum(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            int temp = target - arr[i];
            // 如果存在则返回结果，不存在则加入 map
            if (valueToIndex.containsKey(temp)) {
                return new int[]{valueToIndex.get(temp), i};
            }
            valueToIndex.put(arr[i], i);
        }
        return null;
    }
}
