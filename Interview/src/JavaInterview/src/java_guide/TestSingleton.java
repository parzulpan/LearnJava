package java_guide;

/**
 * @author parzulpan
 *
 * 测试单例模式
 */

public class TestSingleton {
    public static void main(String[] args) {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance1 == instance2);
        System.out.println(instance1.equals(instance2));
    }
}
