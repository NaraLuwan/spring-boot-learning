package com.github.luwan.jdk.learning.join;

/**
 * Join用法&源码分析
 *
 * @author NaraLuwan
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程t1执行");
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程t2执行");
        });

        t1.start();
        t1.join(10000);
        t2.start();

        System.out.println("线程main执行");
    }

}
