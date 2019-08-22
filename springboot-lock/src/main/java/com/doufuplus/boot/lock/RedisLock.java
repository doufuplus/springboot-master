//package com.doufuplus.boot.lock;
//
//import java.util.Arrays;
//import java.util.Random;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//
//import javax.annotation.Resource;
//
//import com.doufuplus.boot.util.FileUtils;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.stereotype.Service;
//
//import redis.clients.jedis.Jedis;
//
///**
// * Redis分布式锁
// *
// * @author lwx
// */
//@Service("redisLock")
//public class RedisLock implements Lock {
//
//    @Resource
//    private JedisConnectionFactory factory;
//
//    // 锁名称
//    private static final String LOCK_NAME = "LOCK";
//
//    // 保存策略【NX|XX】：NX——只有在键不存在时才设置，XX——只有在键已经存在时才设置它
//    private static final String NXXX = "NX";
//
//    // 过期时间单位【EX|PX】：EX=秒，PX=毫秒
//    private static final String EXPX = "PX";
//
//    // 用于保存线程级变量
//    private ThreadLocal<String> local = new ThreadLocal<String>();
//
//    // 尝试次数
//    private int count = 0;
//
//    @Override
//    public void lock() {
//        if (tryLock()) {
//            return;
//        }
//        try {
//            Thread.sleep(new Random().nextInt(10) + 1);
//            count++;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (count < 8) {
//            lock();
//        }
//    }
//
//    @Override
//    public boolean tryLock() {
//        // 1、随机值，避免误删锁
//        String uuid = UUID.randomUUID().toString();
//        // 2、保存至当前线程
//        local.set(uuid);
//        // 3、设置失效时间、使用原子操作保存锁
//        Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
//        String result = jedis.set(LOCK_NAME, uuid, NXXX, EXPX, 3 * 1000);
//        if ("OK".equals(result)) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void unlock() {
//        // 1、获取lua脚本
//        String script = FileUtils.readFileByLines(this.getClass().getResource("/").getPath() + "/unlock.lua");
//        // 2、获取线程内uuid变量
//        String uuid = local.get();
//        // 3、删除redis锁
//        Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
//        jedis.eval(script, Arrays.asList(LOCK_NAME), Arrays.asList(uuid));
//    }
//
//    // ----------------------------------
//    @Override
//    public void lockInterruptibly() throws InterruptedException {
//    }
//
//    @Override
//    public Condition newCondition() {
//        return null;
//    }
//
//    @Override
//    public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
//        return false;
//    }
//}