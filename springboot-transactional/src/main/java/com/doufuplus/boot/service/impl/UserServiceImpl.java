package com.doufuplus.boot.service.impl;

import com.doufuplus.boot.entity.Msg;
import com.doufuplus.boot.entity.User;
import com.doufuplus.boot.mapper.MsgMapper;
import com.doufuplus.boot.mapper.UserMapper;
import com.doufuplus.boot.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户服务
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/14
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MsgMapper msgMapper;

    /**
     * 用户注册
     * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
     *
     * @author 丶doufu
     * @date 2019/08/14
     */
    @Override
    @Transactional
    public int register(User user) {
        // ...
        userMapper.insert(user);
        try {
            // sendMsg(user);
            // this.sendMsg(user);
            UserService currentProxy = (UserService) AopContext.currentProxy();
            currentProxy.sendMsg(user);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        // ...
        return 0;
    }

    /**
     * 模拟调用短信网关发送短信
     * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
     *
     * @author 丶doufu
     * @date 2019/08/14
     */
    @Override
    @Transactional
    public void sendMsg(User user) {
        Msg msg = new Msg(user.getId(), "欢迎来到豆腐别馆", new Date());
        msgMapper.insert(msg);

        // 调用短信网关
        // ...
        throw new RuntimeException("调用短信网关失败！");
    }
}
