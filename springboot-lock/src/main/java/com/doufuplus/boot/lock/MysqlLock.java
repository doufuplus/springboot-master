package com.doufuplus.boot.lock;

import com.doufuplus.boot.entity.LockEntity;
import com.doufuplus.boot.mapper.LockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Service("mysqlLock")
public class MysqlLock implements Lock {

    @Autowired
    private LockMapper lockMapper;

    private static final int NUM = 1;

    private static final String MODULE = "LOCK";

    // 尝试次数
    private int count = 0;

    /**
     * 阻塞锁
     */
    @Override
    public void lock() {
        if (!tryLock()) {
            try {
                Thread.sleep(new Random().nextInt(10) + 1);
                count++;
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
            if (count < 8) {
                lock();
            }
        }
    }

    /**
     * 非阻塞锁
     */
    @Override
    public boolean tryLock() {
        try {
            lockMapper.insert(new LockEntity(NUM));
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void unlock() {
        lockMapper.deleteById(NUM);
    }

    // ----------

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
