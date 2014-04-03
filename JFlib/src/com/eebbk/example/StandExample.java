package com.eebbk.example;// 包名必须全为小写字母和数字，不能有下划线

// import不要用'*', eclipse中CTRL + SHIFT + O可自动调整import顺序

/**
 * 类注释格式
 *
 * 所有类成员变量声明放在类的开头，在任何方法声明之前。
 * 方法之间有一个空行
 * 必须使用英文进行注释
 */
public class StandExample { // 所有大括号起始都在行尾, 类名首字母大写
    private static final String TAG = "example.StyleExample";
    public static final int DATA_EXAMPLE = 0; // 常量全为大写字母，以_分隔
    public static final int DATA_EXAMPLE_ANOTHER = 1;
    private static Object sLock = null; // 静态成员变量以s开头
    private int mScreenOff = 0; // 非静态成员变量名以m开头

    /**
     * 函数注释
     */
    public static void main(String[] args) { // 圆括号后面的元素紧挨圆括号
        int wifiState = 1; // 所有双目操作符必须前后都有空格，所有;号之前都没有空格

        // 缩进为4个space，而不是tab
        // 行注释 或
        /* 行注释 */
        wifiState -= 1;

        /*
         * 多行注释
         * 行2
         */
        wifiState = wifiState + 1;
        if (true || wifiState == 0) { // if语句, if与圆括号之间以及圆括号与大括号之间有一个空格, if语句一定要有大括号.
            for (int i = 0; i < 10; i++) { // for语句, for与圆括号之间以及圆括号与大括号之间有一个空格
                ++wifiState; // 单目运算符与操作数之间无空格
            }

            for (String string : args) { // foreach语句，冒号两边各一个空格

            }
        }

        switch (wifiState) { // case之间有一个空行
            case DATA_EXAMPLE:
                System.out.println(args[0]);
                break;

            case DATA_EXAMPLE_ANOTHER:
                break;

            default:
                break;
        }

        while (true) {
            //do something
            break;
        }

        do {
            // dummy
        } while (true);
    }

    /**
     * 类方法注释, 所有public的方法都要加注释
     * @param param1 数据1
     * @param param2 数据2
     * @return 返回测试值
     */
    public String getBossId(int param1, int param2) { // 参数之间有空格, 方法名单词首字母大写(第一个字母小写).
        return "bob";
    }

    /**
     * 类方法注释, 私有成员函数
     * @return 返回测试值
     */
    private String getUrl() {
        return "http://www.eee168.com";
    }
}
 

