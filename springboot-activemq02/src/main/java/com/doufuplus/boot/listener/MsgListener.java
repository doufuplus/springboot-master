package com.doufuplus.boot.listener;

import com.doufuplus.boot.constant.MsgConstant;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听器
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/15
 */
@Component
public class MsgListener {

    // 接收test.queue队列的消息
    @JmsListener(destination = MsgConstant.QUEUE_ONE)
    public void receiveQueue(String msg) {
        System.out.println("[" + MsgConstant.QUEUE_ONE + "]消息:" + msg);
    }


    // @JmsListener如果不指定独立的containerFactory的话是只能消费queue消息
    @JmsListener(destination = MsgConstant.TOPIC_QUEUE, containerFactory = MsgConstant.TOPIC_LISTENER_FACTORY)
    public void receive1(String msg) {
        System.out.println("[" + MsgConstant.TOPIC_QUEUE + "]消费者:receive1=" + msg);
    }


    @JmsListener(destination = MsgConstant.TOPIC_QUEUE, containerFactory = MsgConstant.TOPIC_LISTENER_FACTORY)
    public void receive2(String msg) {
        System.out.println("[" + MsgConstant.TOPIC_QUEUE + "]消费者:receive2=" + msg);
    }


    @JmsListener(destination = MsgConstant.TOPIC_QUEUE, containerFactory = MsgConstant.TOPIC_LISTENER_FACTORY)
    public void receive3(String msg) {
        System.out.println("[" + MsgConstant.TOPIC_QUEUE + "]消费者:receive3=" + msg);
    }


}