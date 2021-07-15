package com.scrh.shirodemo;

import com.scrh.shirodemo.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ShiroDemoApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void getMD5Pwd() {
        Md5Hash pwd = new Md5Hash("admin","salt01",10);
        //Sha512Hash pwd2 = new Sha512Hash("admin","salt01",10);
        //System.out.println("pwd2 = " + pwd2+"  长度"+ pwd2.toString().length());
        System.out.println("pwd = " + pwd +"  长度"+ pwd.toString().length());

    }

}
