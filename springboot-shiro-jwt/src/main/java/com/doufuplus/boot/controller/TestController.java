package com.doufuplus.boot.controller;

import com.doufuplus.boot.constant.JwtConstant;
import com.doufuplus.boot.constant.ResultCode;
import com.doufuplus.boot.entity.User;
import com.doufuplus.boot.po.Result;
import com.doufuplus.boot.service.TestService;
import com.doufuplus.boot.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/08
 */
@RestController
public class TestController extends BaseController {

    @Autowired
    private TestService testService;

    /**
     * test
     * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
     *
     * @author 丶doufu
     * @date 2019/8/10
     */
    @RequestMapping("/test")
    public Result test() {
        return new Result(ResultCode.SUCCESS, "Hello SHIRO JWT!");
    }


    /**
     * 获取当前登录用户
     * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
     *
     * @author 丶doufu
     * @date 2019/8/10
     */
    @RequestMapping("/current")
    public Result current() {
        try {
            User user = new User();
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                String token = (String) subject.getPrincipal();
                if (StringUtils.isNotBlank(token)) {
                    String account = JwtUtil.getClaim(token, JwtConstant.ACCOUNT);
                    if (StringUtils.isNotBlank(account)) {
                        user = testService.findUserByAccount(account);
                    }
                }
            }
            return new Result(ResultCode.SUCCESS, "success.", user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultCode.ERROR, e.getMessage());
        }
    }

}
