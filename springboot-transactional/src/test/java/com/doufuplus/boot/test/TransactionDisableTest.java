package com.doufuplus.boot.test;

import com.doufuplus.boot.WebApplication;
import com.doufuplus.boot.entity.User;
import com.doufuplus.boot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class TransactionDisableTest {

    @Autowired
    private UserService userService;

    @Test
    public void testTransactional() {
        User user = new User("doufuplus", "123456", new Date());
        userService.register(user);
    }
}
