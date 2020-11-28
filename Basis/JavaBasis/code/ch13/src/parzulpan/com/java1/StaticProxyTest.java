package parzulpan.com.java1;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 静态代理的举例
 * 特点：代理类和被代理类在编译期间就被确定了，不利于程序的扩展。
 * 同时，每一个代理类只能为一个接口服务，这样一来程序开发中必然产生过多的代理。
 */

public class StaticProxyTest {
    public static void main(String[] args) {
        // 创建被代理类的对象
        NikeClothFactory nikeClothFactory = new NikeClothFactory();
        // 创建代理类的对象
        ProxyClothFactory proxyClothFactory = new ProxyClothFactory(nikeClothFactory);

        proxyClothFactory.produceCloth();
    }
}

// 工厂接口
interface ClothFactory {
    void produceCloth();
}

// 代理类
class ProxyClothFactory implements ClothFactory {
    private ClothFactory factory;   // 用被代理类对象进行实例化

    public ProxyClothFactory(ClothFactory factory) {
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("代理工厂进行准备工作！");

        factory.produceCloth();

        System.out.println("代理工厂进行收尾工作！");
    }
}


// 被代理类
class NikeClothFactory implements ClothFactory {

    @Override
    public void produceCloth() {
        System.out.println("Nike 工厂生产一批球鞋！");
    }
}