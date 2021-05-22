package java_two;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author parzulpan
 *
 * OOM - Metaspace
 * VM options: -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:+PrintGCDetails
 */

public class OOMMetaspace {
    /** 静态类 */
    static class OOMObject {}

    public static void main(final String[] args) {
        int i = 0;
        try {
            while (true) {
                ++i;
                // 使用 Spring 的动态字节码技术
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMObject.class);
                enhancer.setUseCache(false);
                enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, args));
                enhancer.create();
            }
        } catch (Throwable throwable) {
            System.out.println("循环 " + i + " 次发生OOM - Metaspace");
        } finally {
            ;
        }
    }
}
