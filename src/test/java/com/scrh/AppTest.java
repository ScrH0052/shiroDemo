package com.scrh;


import com.scrh.shirodemo.pojo.User;
import com.scrh.shirodemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.Resource;
import java.util.List;

/**
 * Unit test for simple App.
 */
@SpringBootTest("Application.class")
public class AppTest {
    /**
     * Rigorous Test :-)
     */

    @Resource
    private UserService userService;

    @Test
    public void shouldAnswerWithTrue() {
        List<User> users = userService.getAllUsers();
        System.out.println("users = " + users);
    }
}
