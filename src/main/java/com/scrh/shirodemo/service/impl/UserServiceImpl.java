package com.scrh.shirodemo.service.impl;

import com.scrh.shirodemo.mapper.UserMapper;
import com.scrh.shirodemo.pojo.User;
import com.scrh.shirodemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.selectByUserName(username);
    }

    @Override
    public boolean checkUserInfo(String username, String password) {
        return userMapper.checkUserInfo(username,password) > 0;
    }
}
