package com.doufuplus.boot.service;

import com.doufuplus.boot.entity.User;

public interface UserService {

    int register(User user);

    void sendMsg(User user);
}
