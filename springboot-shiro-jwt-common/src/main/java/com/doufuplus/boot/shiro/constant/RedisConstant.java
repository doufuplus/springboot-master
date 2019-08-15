package com.doufuplus.boot.shiro.constant;

/**
 * redis常量配置
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/7/27
 */
public interface RedisConstant {

    String BASE_KEY = "doufuplus_";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

}
