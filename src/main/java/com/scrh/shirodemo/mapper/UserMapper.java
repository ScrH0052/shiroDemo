package com.scrh.shirodemo.mapper;

import com.scrh.shirodemo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> selectAll();

    User selectByUserName(String username);

    int checkUserInfo(String username,String password);
}
