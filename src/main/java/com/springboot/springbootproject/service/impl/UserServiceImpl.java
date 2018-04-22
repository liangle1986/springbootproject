package com.springboot.springbootproject.service.impl;

import com.springboot.springbootproject.dao.UserMapper;
import com.springboot.springbootproject.entity.User;
import com.springboot.springbootproject.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 *
 *
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByPrimaryKey(int id){
        return userMapper.selectByPrimaryKey(id);
    }
}
