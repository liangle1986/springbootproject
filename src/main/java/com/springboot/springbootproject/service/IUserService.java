package com.springboot.springbootproject.service;

import com.springboot.springbootproject.entity.User;

/**
 *
 *
 *
 *
 */
public interface IUserService {

    User selectByPrimaryKey(int id);
}
