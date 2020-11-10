package com.github.luwan.spring.boot.learning.aop.java.proxy;

/**
 * 实现类
 * @author luwan
 * @date 2020-11-10 19:00
 */
public class HelloWorldImpl implements HelloWorld {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }
}
