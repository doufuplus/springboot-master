package com.doufuplus.boot.shiro.config.exception;

/**
 * 自定义401无权限异常(UnauthorizedException)
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/03
 */
public class CustomUnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -3993376696547776573L;

    public CustomUnauthorizedException(String msg) {
        super(msg);
    }

    public CustomUnauthorizedException() {
        super();
    }
}
