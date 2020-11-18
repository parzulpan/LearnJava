# 数组和算法

## 查找算法

### 线性查找

...

### 二分查找

二分查找要求数据结构是有序的。

```java
package com.parzulpan.java.ch03;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : 二分查找
 */

public class BinaryFind {

    public static boolean binaryFind(int[] arr, int number) {
        boolean isFlag = false;
        int start = 0, end = arr.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (arr[mid] == number) {
                isFlag = true;
                break;
            } else if (arr[mid] > number) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return isFlag;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{-99, -54, -2, 0, 2, 33, 43, 256, 999};
        System.out.println(binaryFind(arr, 256));
        System.out.println(binaryFind(arr, 1342));
    }
}

```

## 排序算法

排序：假设含有 n 个记录的序列为 {R1，R2，...,Rn}，其相应的关键字序列为 {K1，K2，...,Kn}。将这些记录重新排序为 {Ri1,Ri2,...,Rin}，使得相应的关键字值满足条 Ki1<=Ki2<=...<=Kin，这样的一种操作称为排序。

通常来说，排序的目的是快速查找。

衡量排序算法的优劣：

* 时间复杂度：分析关键字的比较次数和记录的移动次数；
* 空间复杂度：分析排序算法中需要多少辅助内存；
* 稳定性：若两个记录 A 和 B 的关键字值相等，但排序后 A、B 的先后次序保持不变，则称这种排序算法是稳定的。

排序算法分类：

* 内部排序：整个排序过程不需要借助于外部存储器（如磁盘等），所有排序操作都在内存中完成；
* 外部排序：参与排序的数据非常多，数据量非常大，计算机无法把整个排序过程放在内存中完成，必须借助于外部存储器（如磁盘）。外部排序最常见的是**多路归并排序**。可以认为外部排序是由多次内部排序组成。

内部排序算法：

* 选择排序：直接选择排序、堆排序；
* 交换排序：冒泡排序、快速排序；
* 插入排序：直接插入排序、折半插入排序、希尔排序；
* 归并排序；
* 桶排序；
* 基数排序。

其中，快速排序、直接选择排序、希尔排序和堆排序是**不稳定**排序。

**！待更新**

排序算法性能对比：

| 排序方法 | 平均时间复杂度| 平均时间复杂度 | 平均时间复杂度 | 空间复杂度 | 稳定性 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 直接选择排序 | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` | 不稳定 |
| 堆排序 | `O(nlog2n)` | `O(nlog2n)` | `O(nlog2n)` | `O(1)` | 不稳定 |
| 冒泡排序 | `O(n^2)` | `O(n^2)` | `O(n)` | `O(1)` | 稳定 |
| 快速排序 | `O(nlog2n)` | `O(n^2)` | `O(nlog2n)` | `O(nlog2n)` | 不稳定 |
| 直接插入排序 | `O(n^2)` | `O(n^2)` | `O(n)` | `O(1)` | 稳定 |
| 直接选择排序 | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` | 不稳定 |
| 直接选择排序 | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` | 不稳定 |
| 直接选择排序 | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` | 不稳定 |
| 直接选择排序 | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` | 不稳定 |
| 直接选择排序 | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` | 不稳定 |

排序算法性能比较：

排序算法的选择：

## Arrays 工具类

java.util.Arrays 类即为操作数组的工具类，包含了用来操作数组（比如排序和搜索）的各种方法。

常见方法：

| 方法 | 说明 |
| :--- | :--- |
| `boolean equals(int[] a,int[] b)` | 判断两个数组是否相等。 |
| `String toString(int[] a)` | 输出数组信息。 |
| `void fill(int[] a,int val)` | 将指定值填充到数组之中。 |
| `void sort(int[] a)` | 对数组进行排序。 |
| `int binarySearch(int[] a,int key)` | 对排序后的数组进行二分法检索指定的值。 |

```java
package com.parzulpan.java.ch03;

import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : Arrays 工具类的常用方法
 */

public class ArraysTest {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3, 4};
        int[] arr2 = new int[]{1, 3, 2, 4};

        System.out.println(Arrays.equals(arr1, arr2));

        System.out.println(Arrays.toString(arr1));

        int[] arr3 = new int[]{1, 2, 3, 4};
        Arrays.fill(arr3, 10);
        System.out.println(Arrays.toString(arr3));

        Arrays.sort(arr2);
        System.out.println(Arrays.toString(arr2));

        int[] arr4 = new int[]{-99, -54, -2, 0, 2, 33, 43, 256, 999};
        int index = Arrays.binarySearch(arr4, 239);
        if (index < 0) {
            System.out.println("arr4 中不存在 " + 239);
        }
    }
}

```

## 练习和总结

**！待更新**
