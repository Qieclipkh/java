package com.cly.smartdoc.smartdoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cly.smartdoc.smartdoc.pojo.User;
import com.cly.smartdoc.smartdoc.service.UserService;

/**
 * @Author changleying
 * @create 2019/11/27 17:35
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUser() {
        final List<User> userList = userService.selectAll();
        return userList;
    }

    @GetMapping(value = "/{userId:\\d+}")
    public User getUser(@PathVariable Integer userId) {
        User user = User.builder().userId(userId).name("name" + userId).build();
        return user;
    }
}
