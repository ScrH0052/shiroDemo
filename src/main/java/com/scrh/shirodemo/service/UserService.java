package com.scrh.shirodemo.service;

import com.scrh.shirodemo.pojo.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByName(String username);

    boolean checkUserInfo(String username, String password);
}
