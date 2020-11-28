package parzulpan.com.java1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 动态代理的举例
 * 要想实现动态代理，需要解决两个问题：
 * 1. 如果根据加载到内存中的被代理类，动态的创建一个代理类及其对象？
 * 2. 当通过代理类的对象调用方法时，如果动态的去调用被代理类中的同名方法？
 */

public class ProxyTest {
    public static void main(String[] args) {
        // 被代理类
        SuperMan superMan = new SuperMan();
//        Object proxyInstance = ProxyFactory.getProxyInstance(superMan);
        // 动态代理类
        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(superMan);
        String belief = proxyInstance.getBelief();
        System.out.println(belief);
        proxyInstance.eat("回锅肉");

        System.out.println();

        // 被代理类
        NikeClothFactory nikeClothFactory = new NikeClothFactory();
        // 动态代理类
        ClothFactory proxyInstance1 = (ClothFactory) ProxyFactory.getProxyInstance(nikeClothFactory);
        proxyInstance1.produceCloth();
    }
}

// 被代理接口
interface Human {
    String getBelief();
    void eat(String food);
}

// AOP
class HumanUtil {
    public void method1() {
        System.out.println("我是方法一！");
    }

    public void method2() {
        System.out.println("我是方法二！");
    }
}

// 被代理类
class SuperMan implements Human {

    @Override
    public String getBelief() {
        return "我相信我可以飞！";
    }

    @Override
    public void eat(String food) {
        System.out.println("我喜欢吃 " + food);
    }
}

// 代理工厂
class ProxyFactory {
    /**
     * 解决问题1
     * @param obj 被代理类的对象
     * @return 代理类的对象
     */
    public static Object getProxyInstance(Object obj) {
        MyInvocationHandler handler = new MyInvocationHandler();

        handler.bind(obj);

        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }
}

// 实现接口 InvocationHandler 的类
class MyInvocationHandler implements InvocationHandler {
    private Object obj; // 需要使用被代理类的对象进行赋值

    // 绑定被代理类
    public void bind(Object obj) {
        this.obj = obj;
    }

    // 当通过代理类的对象调用方法 A 时，就会调用这个 invoke()
    // 解决问题2，将被代理类要执行的方法 A 的功能就声明在 invoke() 中
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 使用 AOP
        HumanUtil humanUtil = new HumanUtil();

        humanUtil.method1();

        // method 作为被代理类对象要调用的方法
        // obj 被代理类对象
        // returnValue 作为当前类的 invoke() 的返回值
        Object returnValue = method.invoke(obj, args);

        humanUtil.method2();

        return returnValue;
    }
}