package com.springboot.springbootproject.controller;

import com.springboot.springbootproject.entity.User;
import com.springboot.springbootproject.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Author:梁乐乐
* @Description: 用户信息控制器
* @params:
* @return:
* @Date: 2018/4/22 18:11
*/
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/selectUser")
    public User selectByPrimaryKey(int id){
        User user = userService.selectByPrimaryKey(id);

        return user;
    }
}
