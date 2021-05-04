package java_two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc volatile 保证有序性说明
 */

public class VolatileDemo3 {
    int a = 0;
    boolean flag = false;

    public void method01(){
        //语句1
        a = 1;

        //语句2
        flag = true;
    }

    public void method02(){
        if(flag){
            //语句3
            a = a + 5;
        }

        // 多线程情况下，结果可能是6或1或5或0
        System.out.println("retValue: " + a);
    }
}
