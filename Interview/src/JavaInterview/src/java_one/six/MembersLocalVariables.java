package java_one.six;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one.six
 * @desc 6. 成员变量和局部变量
 * 抓住：
 * 局部变量存在栈中，它每次调用都是新的生命周期
 * 实例变量存在堆中，它随着对象的创建而初始化，随着对象的回收而消亡，每一个对象的实例变量是独立的
 * 类变量存在方法区中，它随着类的初始化而初始化，随着类的卸载而消亡，该类的所有对象的类变量是共享的
 */

public class MembersLocalVariables {
    static int s;
    int i;
    int j;

    {
        int i = 1;
        i++;
        j++;
        s++;
    }

    public void test(int j) {
        j++;
        i++;
        s++;
    }

    public static void main(String[] args) {
        MembersLocalVariables m1 = new MembersLocalVariables();
        MembersLocalVariables m2 = new MembersLocalVariables();
        m1.test(10);
        m1.test(20);
        m2.test(30);
        // 2 1 5
        System.out.println(m1.i + " " + m1.j + " " + MembersLocalVariables.s);
        // 1 1 5
        System.out.println(m2.i + " " + m2.j + " " + MembersLocalVariables.s);
    }
}
