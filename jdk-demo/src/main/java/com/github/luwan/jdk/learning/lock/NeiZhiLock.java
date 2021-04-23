package com.github.luwan.jdk.learning.lock;

public class NeiZhiLock {

    static class Fu {

        public synchronized void doSomething() {
            System.out.println(this.toString() + "---------------------");
        }
    }

    static class Zi extends Fu {

        @Override
        public synchronized void doSomething() {
            System.out.println(this.toString() + ": calling doSomething");
            super.doSomething();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                Fu fu = new Zi();
                fu.doSomething();
            });
            thread.start();
        }
    }
}
