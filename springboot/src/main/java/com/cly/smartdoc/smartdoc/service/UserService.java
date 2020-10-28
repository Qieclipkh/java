package com.cly.smartdoc.smartdoc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cly.smartdoc.smartdoc.pojo.User;

/**
 * @Author changleying
 * @create 2019/11/27 18:16
 */
@Service
public class UserService {
    public List<User> selectAll() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder().userId(i).name("name" + i).build();
            userList.add(user);
        }
        return userList;
    }
}
