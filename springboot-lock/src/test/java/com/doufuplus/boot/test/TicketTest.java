package com.doufuplus.boot.test;

import com.doufuplus.boot.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class TicketTest {

    private int count = 100; // 100张票

    // JDK自带JVM锁：其解决不了分布式环境多任务对共享资源竞争的协同操作问题
    private Lock lock = new ReentrantLock();

    @Test
    public void TicketTest() throws InterruptedException {

        TicketRunnable tr = new TicketRunnable();
        // 四个线程对应四个窗口
        Thread t1 = new Thread(tr, "窗口A");
        Thread t2 = new Thread(tr, "窗口B");
        Thread t3 = new Thread(tr, "窗口C");
        Thread t4 = new Thread(tr, "窗口D");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        Thread.currentThread().join();
    }

    public class TicketRunnable implements Runnable {

        @Override
        public void run() {
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

