package parzulpan.com.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc :
 */

public class SeasonEnumTest {
    public static void main(String[] args) {
        Season1 summer = Season1.SUMMER;
        System.out.println(summer); // SUMMER

        System.out.println(Season1.class.getSuperclass());  // class java.lang.Enum，说明枚举类默认继承 class java.lang.Enum

        System.out.println();
        Season1[] values = Season1.values();
        for (Season1 value : values) {
            System.out.println(value);
            value.show();
        }

        System.out.println();
        Thread.State[] values1 = Thread.State.values();
        for (Thread.State value: values1) {
            System.out.println(value);
        }

        System.out.println();
        try {
            Season1 winter = Season1.valueOf("WINTER");
            System.out.println(winter); // WINTER
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println();
        summer.show();
    }
}

interface Info {
    void show();
}

enum  Season1 implements Info{
    SPRING("春天", "春暖花开") {
        @Override
        public void show() {
            System.out.println("春天在哪里？");
        }
    },
    SUMMER("夏天", "夏日炎炎") {
        @Override
        public void show() {
            System.out.println("夏天在哪里？");
        }
    },
    AUTUMN("秋天", "秋高气爽") {
        @Override
        public void show() {
            System.out.println("秋天在哪里？");
        }
    },
    WINTER("冬天", "白雪皑皑") {
        @Override
        public void show() {
            System.out.println("冬天在哪里？");
        }
    };

    private final String SEASON_NAME;
    private final String SEASON_DESC;


    Season1(String SEASON_NAME, String SEASON_DESC) {
        this.SEASON_NAME = SEASON_NAME;
        this.SEASON_DESC = SEASON_DESC;
    }

    @SuppressWarnings("unused")
    public String getSEASON_NAME() {
        return SEASON_NAME;
    }

    public String getSEASON_DESC() {
        return SEASON_DESC;
    }

//    @Override
//    public void show() {
//        System.out.println("四季在哪里？");
//    }

//    @Override
//    public String toString() {
//        return "Season{" +
//                "SEASON_NAME='" + SEASON_NAME + '\'' +
//                ", SEASON_DESC='" + SEASON_DESC + '\'' +
//                '}';
//    }
}