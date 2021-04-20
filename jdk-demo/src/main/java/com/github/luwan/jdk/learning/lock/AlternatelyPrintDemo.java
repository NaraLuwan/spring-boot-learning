package com.github.luwan.jdk.learning.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印奇偶数，基于ReentrantLock实现
 *
 * @author NaraLuwan
 */
public class AlternatelyPrintDemo {

    static class Wrapper {
        private Integer count;

        public Wrapper(Integer count) {
            this.count = count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getCount() {
            return count;
        }
    }

    static class PrintOdd implements Runnable {
        private volatile Wrapper wrapper;
        private ReentrantLock lock;
        private Condition condition;

        public PrintOdd(Wrapper wrapper, ReentrantLock lock, Condition condition) {
            this.wrapper = wrapper;
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            while (wrapper.getCount() <= 100) {
                lock.lock();
                try {
                    if (wrapper.getCount() % 2 == 0) {
                        condition.await();
                    } else {
                        System.out.println(Thread.currentThread().getName() + ":" + wrapper.getCount());
                        wrapper.setCount(wrapper.getCount() + 1);
                        condition.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class PrintEve implements Runnable {

        private volatile Wrapper wrapper;
        private ReentrantLock lock;
        private Condition condition;

        public PrintEve(Wrapper wrapper, ReentrantLock lock, Condition condition) {
            this.wrapper = wrapper;
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            while (wrapper.getCount() <= 100) {
                lock.lock();
                try {
                    if (wrapper.getCount() % 2 == 1) {
                        condition.await();
                    } else {
                        System.out.println(Thread.currentThread().getName() + ":" + wrapper.getCount());
                        wrapper.setCount(wrapper.getCount() + 1);
                        condition.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Wrapper wrapper = new Wrapper(0);
        Condition condition = lock.newCondition();
        new Thread(new PrintOdd(wrapper, lock, condition)).start();
        new Thread(new PrintEve(wrapper, lock, condition)).start();
    }

}
