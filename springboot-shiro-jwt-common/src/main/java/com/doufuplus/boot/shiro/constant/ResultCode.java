package com.doufuplus.boot.shiro.constant;

/**
 * 状态码
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/7/27
 */
public interface ResultCode {

    /**
     * 未登录
     */
    int NOT_LOGIN = 10001;

    /**
     * 请求成功
     */
    int SUCCESS = 10200;

    /**
     * 请求失败
     */
    int ERROR = 10400;

    /**
     * 非法请求
     */
    int UNLAWFUL = 10401;

    /**
     * 已存在
     */
    int ALREADY_EXIST = 10403;

    /**
     * 找不到
     */
    int NOT_FOUND = 10404;

    /**
     * 次数限制
     */
    int TRIES_LIMIT = 10405;

    /**
     * 分钟级流控
     */
    int MINUTE_LIMIT = 10415;

    /**
     * 小时级流控
     */
    int HOUR_LIMIT = 10425;

    /**
     * 天级流控
     */
    int DAY_LIMIT = 10435;

    /**
     * 参数错误
     */
    int PARAM_ERROR = 10600;

    /**
     * 验证码错误
     */
    int CAPTCHA_ERROR = 10601;

    /**
     * 密码错误
     */
    int PASSWORD_ERROR = 10602;

    /**
     * 失效/超时
     */
    int DISABLED = 10604;

    /**
     * 冻结中
     */
    int FREEZING = 10605;

    /**
     * 服务降级
     */
    int HYSTRIX = 10900;
}
