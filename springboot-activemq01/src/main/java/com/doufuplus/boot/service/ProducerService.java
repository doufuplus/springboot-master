package com.doufuplus.boot.service;

/**
 * 消息生产者服务
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/15
 */
public interface ProducerService {

    /**
     * 发送消息（点对点）
     *
     * @param destination
     * @param msg
     */
    void sendMsgToQueue(String destination, String msg);

    /**
     * 发送消息（发布-订阅模式）
     *
     * @param destination
     * @param msg
     */
    void sendMsgToTopic(String destination, String msg);
}
