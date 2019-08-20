package com.doufuplus.boot.service.impl;

import com.doufuplus.boot.service.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息生产者服务实现类
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/15
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMsgToQueue(String destination, final String msg) {
        jmsTemplate.convertAndSend(new ActiveMQQueue(destination), msg);
    }

    @Override
    public void sendMsgToTopic(String destination, final String msg) {
        jmsTemplate.convertAndSend(new ActiveMQTopic(destination), msg);
    }

}
