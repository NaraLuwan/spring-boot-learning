package com.github.luwan.jdk.learning.lambda;

/**
 * 可以使用Lambda表达式来表示函数式接口的一个实现
 * JAVA 8 之前一般是用匿名类实现
 * @author NarnLuwan
 * @date 2021-03-23
 */
@FunctionalInterface
public interface LambdaDemoInterface {

    /**
     * 函数式接口：只能有一个抽象方法的接口
     * @param arg
     */
    void print(String arg);

    /**
     * 可以有默认方法
     */
    default void defaultPrint() {
        System.out.println("this is a default print: hello lambda!");
    }

    /**
     * 可以有静态方法
     */
    static void staticPrint(){
        System.out.println("this is a static print: hello lambda!");
    }

    /**
     * 可以包含Object里的public方法
     * @param obj
     * @return
     */
    @Override
    boolean equals(Object obj);
}
