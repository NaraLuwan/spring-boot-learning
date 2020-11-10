package com.github.luwan.spring.boot.learning.aop.java.proxy;

import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) throws Exception {
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        CustomInvocationHandler handler = new CustomInvocationHandler(new HelloWorldImpl());
        HelloWorld proxy = (HelloWorld) Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(),
                new Class[]{HelloWorld.class},
                handler);
        proxy.sayHello("Mikan");
    }

}
