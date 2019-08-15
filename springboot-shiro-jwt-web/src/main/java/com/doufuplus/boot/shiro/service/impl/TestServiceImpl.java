package com.doufuplus.boot.shiro.service.impl;

import com.doufuplus.boot.shiro.entity.User;
import com.doufuplus.boot.shiro.mapper.UserMapper;
import com.doufuplus.boot.shiro.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/08
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByAccount(String account) {
        return userMapper.findByAccount(account);
    }
}
