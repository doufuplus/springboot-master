package com.doufuplus.boot.controller;

import com.doufuplus.boot.constant.ResultCode;
import com.doufuplus.boot.entity.User;
import com.doufuplus.boot.po.Result;
import com.doufuplus.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * TestController
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/14
 */
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public Result register() {
        User user = new User("doufuplus", "123456", new Date());
        userService.register(user);
        return new Result(ResultCode.SUCCESS);
    }

}
