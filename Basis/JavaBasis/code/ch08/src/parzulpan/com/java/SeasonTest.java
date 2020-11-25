package parzulpan.com.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 自定义枚举类
 */

public class SeasonTest {
    public static void main(String[] args) {
        Season spring = Season.SPRING;
        System.out.println(spring);
    }
}

class Season {
    // 1. 对象如果有实例变量，应该声明为private final，并在构造器中初始化
    private final String SEASON_NAME;
    private final String SEASON_DESC;

    // 2. 私有化类的构造器，保证不能在类的外部创建其对象
    private Season(String SEASON_NAME, String SEASON_DESC) {
        this.SEASON_NAME = SEASON_NAME;
        this.SEASON_DESC = SEASON_DESC;
    }

    // 3. 在类的内部创建枚举类的实例，声明为：public static final
    public static final Season SPRING = new Season("春天", "春暖花开");
    public static final Season SUMMER = new Season("夏天", "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天", "秋高气爽");
    public static final Season WINTER = new Season("冬天", "白雪皑皑");

    // 获取枚举类对象的属性
    public String getSEASON_NAME() {
        return SEASON_NAME;
    }

    public String getSEASON_DESC() {
        return SEASON_DESC;
    }

    @Override
    public String toString() {
        return "Season{" +
                "SEASON_NAME='" + SEASON_NAME + '\'' +
                ", SEASON_DESC='" + SEASON_DESC + '\'' +
                '}';
    }
}