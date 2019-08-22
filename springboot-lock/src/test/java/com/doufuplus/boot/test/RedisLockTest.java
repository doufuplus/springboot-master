package com.doufuplus.boot.test;

import com.doufuplus.boot.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class RedisLockTest {

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    private int count = 100; // 100张票

    @Test
    public void RedisLockTest() throws InterruptedException {

        TestRunnable runnable = new TestRunnable();
        // 四个线程对应四个窗口
        Thread t1 = new Thread(runnable, "窗口A");
        Thread t2 = new Thread(runnable, "窗口B");
        Thread t3 = new Thread(runnable, "窗口C");
        Thread t4 = new Thread(runnable, "窗口D");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        Thread.currentThread().join();
    }

    public class TestRunnable implements Runnable {

        @Override
        public void run() {
            Lock lock = redisLockRegistry.obtain("lock");
            while (count > 0) {
                if (count > 0) {
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + "售出第" + (count--) + "张票");
                    } catch (Exception e) {
                    } finally {
                        lock.unlock();
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

