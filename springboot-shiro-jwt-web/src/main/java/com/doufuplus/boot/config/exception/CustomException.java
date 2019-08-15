package com.doufuplus.boot.config.exception;

/**
 * 自定义异常
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/03
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -6736944294947154413L;

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException() {
        super();
    }
}
