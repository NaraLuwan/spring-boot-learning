package com.github.luwan.jdk.learning.thread;

/**
 * 1. 线程只是一个普通对象，调用start方法会调用本地方法JVM创建一个新线程自动调用该线程对象的run方法
 * 2. 线程执行完成会主动调用notifyAll方法释放资源，但是线程对象还存在，还可以调用run、getState等方法，不过这时候已经不是多线程了
 *
 * @author NaraLuwan
 */
public class ThreadDemo {

    public static void main(String[] args) {
        //简单起见，使用匿名内部类的方法来创建线程
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread对象的run方法被执行了");
            }
        };
        //线程启动
        thread.start();
        //一个线程只能启动一次，start方法加锁
        //thread.start();

        //用循环去监听线程thread是否还活着，只有当线程thread已经结束了，才跳出循环
        while (thread.isAlive()) {
        }
        //线程thread结束了，但仍能调用thread对象的大部分方法
        System.out.println("线程" + thread.getName() + "的状态：" + thread.getState() + "---优先级：" + thread.getPriority());
        //调用run方法
        thread.run();
        //当线程结束时，start方法不能调用，下面的方法将会抛出异常
        thread.start();
    }

}
