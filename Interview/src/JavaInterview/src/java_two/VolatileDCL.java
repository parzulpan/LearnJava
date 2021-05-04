package java_two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 单例模式双重共锁写法
 */

public class VolatileDCL {
    private static volatile VolatileDCL instance;

    public static VolatileDCL getInstance() {
        if (instance == null) {
            synchronized (VolatileDCL.class) {
                if (instance == null) {
                    // 3
                    instance = new VolatileDCL();
                }
            }
        }
        return instance;
    }
}
