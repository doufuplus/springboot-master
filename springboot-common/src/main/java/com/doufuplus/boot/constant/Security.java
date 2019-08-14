package com.doufuplus.boot.constant;

/**
 * Security配置
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/7/27
 */
public interface Security {

    String ACCOUNT = "doufuplus";

    String PASSWORD = "123456";

    String SIGNING_KEY = "doufuplus";

    class ROLES {

        public static final String ADMIN = "ADMIN";

        public static final String USER = "USER";
    }
}
