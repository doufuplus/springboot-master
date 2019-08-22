package com.doufuplus.boot.controller;

import com.doufuplus.boot.constant.ResultCode;
import com.doufuplus.boot.po.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/22
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public Result test() {
        return new Result(ResultCode.SUCCESS);
    }

}
