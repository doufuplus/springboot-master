package com.doufuplus.boot.controller;

import com.doufuplus.boot.po.Result;
import com.doufuplus.boot.constant.JwtConstant;
import com.doufuplus.boot.constant.RedisConstant;
import com.doufuplus.boot.constant.ResultCode;
import com.doufuplus.boot.redis.RedisClient;
import com.doufuplus.boot.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 鉴权控制器
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/03
 */
@RestController
@RequestMapping("/sso")
public class SsoController extends BaseController {

    @Value("${config.refreshToken-expireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    private RedisClient redis;

    /**
     * 登录
     * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
     *
     * @author 丶doufu
     * @date 2019/08/03
     */
    @PostMapping("/login")
    public Result login(String account, String password, HttpServletResponse response) {

        try {
            if (!("doufuplus".equals(account) && "123456".equals(password))) {
                return new Result(ResultCode.PASSWORD_ERROR, "account or password error.");
            }

            // 清除可能存在的shiro权限信息缓存
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_CACHE + account)) {
                redis.del(RedisConstant.PREFIX_SHIRO_CACHE + account);
            }

            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            redis.set(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis,
                    Integer.parseInt(refreshTokenExpireTime));

            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(account, currentTimeMillis);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");

            return new Result().OK();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 退出
     * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
     *
     * @author 丶doufu
     * @date 2019/08/03
     */
    @RequestMapping("/logout")
    public Result logout() {
        try {
            String token = "";
            // 获取头部信息
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                if ("Authorization".equalsIgnoreCase(key)) {
                    token = value;
                }
            }
            // 校验token
            if (StringUtils.isBlank(token)) {
                return new Result(ResultCode.PARAM_ERROR);
            }
            String account = JwtUtil.getClaim(token, JwtConstant.ACCOUNT);
            if (StringUtils.isBlank(account)) {
                return new Result(ResultCode.NOT_LOGIN, "token失效或不正确.");
            }
            // 清除shiro权限信息缓存
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_CACHE + account)) {
                redis.del(RedisConstant.PREFIX_SHIRO_CACHE + account);
            }
            // 清除RefreshToken
            redis.del(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);

            return new Result().OK();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultCode.ERROR, e.getMessage());
        }
    }
}
