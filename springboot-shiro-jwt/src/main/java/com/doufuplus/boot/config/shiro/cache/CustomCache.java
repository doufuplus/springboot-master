package com.doufuplus.boot.config.shiro.cache;

import com.doufuplus.boot.constant.JwtConstant;
import com.doufuplus.boot.constant.RedisConstant;
import com.doufuplus.boot.redis.RedisClient;
import com.doufuplus.boot.util.JwtUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * 重写Shiro的Cache保存读取
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/03
 */
@Component
public class CustomCache<K, V> implements Cache<K, V> {

    @Value("${config.shiro-cache-expireTime}")
    private String shiroCacheExpireTime;

    @Autowired
    private RedisClient redis;

    /**
     * 缓存的key名称获取为shiro:cache:account
     *
     * @param key
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        return RedisConstant.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), JwtConstant.ACCOUNT);
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if (!redis.hasKey(this.getKey(key))) {
            return null;
        }
        return redis.get(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 读取配置文件，获取Redis的Shiro缓存过期时间
        // PropertiesUtil.readProperties("config.properties");
        // String shiroCacheExpireTime =
        // PropertiesUtil.getProperty("shiroCacheExpireTime");
        // 设置Redis的Shiro缓存
        return redis.set(this.getKey(key), value, Integer.parseInt(shiroCacheExpireTime));
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if (!redis.hasKey(this.getKey(key))) {
            return null;
        }
        redis.del(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        // TODO Auto-generated method stub

    }

    /**
     * 缓存的个数
     */
    @Override
    public Set<K> keys() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 获取所有的key
     */
    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @Override public void clear() throws CacheException {
     * redis.getJedis().flushDB(); }
     */

    /*
     * @Override public int size() { Long size = JedisUtil.getJedis().dbSize();
     * return size.intValue(); }
     */

    /*
     * @Override public Set keys() { Set<byte[]> keys =
     * JedisUtil.getJedis().keys(new String("*").getBytes()); Set<Object> set = new
     * HashSet<Object>(); for (byte[] bs : keys) {
     * set.add(SerializableUtil.unserializable(bs)); } return set; }
     */

    /*
     * @Override public Collection values() { Set keys = this.keys(); List<Object>
     * values = new ArrayList<Object>(); for (Object key : keys) {
     * values.add(JedisUtil.getObject(this.getKey(key))); } return values; }
     */
}
