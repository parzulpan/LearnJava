package java_two;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc CAS 基本概念
 */

public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        // true current value 999
        System.out.println(atomicInteger.compareAndSet(5, 999) + " current value " + atomicInteger.get());
        // false current value 999 修改失败
        System.out.println(atomicInteger.compareAndSet(5, 1024) + " current value " + atomicInteger.get());
    }
}
