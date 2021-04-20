package com.github.luwan.jdk.learning.lambda;

import com.github.luwan.jdk.learning.lock.MyLock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author NaraLuwan
 */
@RunWith(SpringRunner.class)
public class LockTest {

    static int count = 0;

    @Test
    public void test() throws InterruptedException {
        MyLock myLock = new MyLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    myLock.lock();
                    for (int i = 0; i < 1000; i++) {
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    myLock.unlock();
                }

            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assert.assertEquals(2000, count);
    }

}
