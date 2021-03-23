package com.github.luwan.jdk.learning.lambda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;

/**
 * @author NaraLuwan
 */
@RunWith(SpringRunner.class)
public class LambdaTest {

    @Test
    public void test(){
        LambdaDemoInterface lambdaDemoImpl = s -> System.out.println("hello " + s);
        lambdaDemoImpl.print("lambda");
        lambdaDemoImpl.defaultPrint();
        LambdaDemoInterface.staticPrint();
    }

    @Test
    public void test1() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello runnable");
            }
        };
        r.run();
        Runnable r1 = () -> System.out.println("hello lambda");
        r1.run();
    }

    @Test
    public void test2(){
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("有一个参数，无返回值的用法（Consumer函数式接口）");
    }

    @Test
    public void test3() {
        BinaryOperator<Integer> binary = (x, y) -> x + y;
        System.out.println(binary.apply(1, 2));// 3
    }


    @Test
    public void test4() {
        // 无返回值lambda函数体中用法
        Runnable r1 = () -> {
            System.out.println("hello lambda1");
            System.out.println("hello lambda2");
            System.out.println("hello lambda3");
        };
        r1.run();

        // 有返回值lambda函数体中用法
        BinaryOperator<Integer> binary = (x, y) -> {
            int a = x * 2;
            int b = y + 2;
            return a + b;
        };
        System.out.println(binary.apply(1, 2));// 3
    }
}
