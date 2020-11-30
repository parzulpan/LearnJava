package parzulpan.com.java1;

import org.junit.Test;

import java.util.Optional;

/**
 * @Author : parzulpan
 * @Time : 2020-11-29
 * @Desc : Optional 类
 */

public class OptionalTest {

    // 创建Optional类对象的方法
    @Test
    public void test1() {
        // Optional.of(T t) 创建一个 Optional 实例，t必须非空；
        Girl girl = new Girl();
        Optional<Girl> girl1 = Optional.of(girl);
        System.out.println(girl1);

        // Optional.empty() 创建一个空的 Optional 实例
        Optional<Object> empty = Optional.empty();
        System.out.println(empty);

        // Optional.ofNullable(T t) t 可以为 null
        girl = null;
        Optional<Girl> girl2 = Optional.ofNullable(girl);
        System.out.println(girl2);

        // Optional 的应用
        Boy boy = null;
        String girlName1 = getGirlName(boy);
        System.out.println(girlName1);  // AAA

        boy = new Boy();
        String girlName2 = getGirlName(boy);
        System.out.println(girlName2);  // BBB

        boy = new Boy(new Girl("CCC"));
        String girlName3 = getGirlName(boy);
        System.out.println(girlName3);  // CCC
    }

    // 使用 Optional 优化
    public String getGirlName(Boy boy) {
        // 避免 Boy 和 Girl 为 null
        Optional<Boy> boyOptional = Optional.ofNullable(boy);
        // T orElse(T other) 如果有值则将其返回，否则返回指定的other对象
        Boy boy1 = boyOptional.orElse(new Boy(new Girl("AAA")));

        Girl girl = boy1.getGirl();

        Optional<Girl> girlOptional = Optional.ofNullable(girl);
        Girl girl1 = girlOptional.orElse(new Girl("BBB"));

        return girl1.getName();
    }
}
