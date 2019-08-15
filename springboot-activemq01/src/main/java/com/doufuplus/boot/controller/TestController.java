package com.doufuplus.boot.controller;

import com.doufuplus.boot.constant.MsgConstant;
import com.doufuplus.boot.constant.ResultCode;
import com.doufuplus.boot.po.Result;
import com.doufuplus.boot.service.ProducerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/15
 */
@RestController
public class TestController {

    @Autowired
    private ProducerService producerService;

    /**
     * 点对点
     * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
     *
     * @author 丶doufu
     * @date 2019/08/15
     */
    @RequestMapping("/queue")
    public Result queue(String msg) {
        if (StringUtils.isBlank(msg)) {
            return new Result(ResultCode.PARAM_ERROR, "msg not null.");
        }
        producerService.sendMsgToQueue(MsgConstant.QUEUE_ONE, msg);
        return new Result(ResultCode.SUCCESS);
    }


    /**
     * 发布/订阅
     * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
     *
     * @author 丶doufu
     * @date 2019/08/15
     */
    @RequestMapping("/topic")
    public Result topic(String msg) {
        if (StringUtils.isBlank(msg)) {
            return new Result(ResultCode.PARAM_ERROR, "msg not null.");
        }
        producerService.sendMsgToTopic(MsgConstant.TOPIC_QUEUE, msg);
        return new Result(ResultCode.SUCCESS);
    }

}
