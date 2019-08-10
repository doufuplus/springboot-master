package com.doufuplus.boot.service;

import com.doufuplus.boot.entity.User;

/**
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/08
 */
public interface TestService {

    User findUserByAccount(String account);
}
