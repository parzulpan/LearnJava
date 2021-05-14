package cn.parzulpan;

/**
 * @author parzulpan
 *
 * 类加载器
 */

public class ClassLoaderDemo {
    public static void main(String[] args) {
        // 在 Car Class 类模板的基础上，形成实例
        Car car1 = new Car();
        Car car2 = new Car();
        // 对某个具体的实例进行 getClass() 操作，就可以得到该实例的类模板，即 Car Class
        System.out.println(car1.getClass());
        // 对这个类模板进行 getClassLoader() 操作，就可以得到这个类模板是由哪个类装载器进行加载的，即 sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(car1.getClass().getClassLoader());
    }
}

class Car {

}