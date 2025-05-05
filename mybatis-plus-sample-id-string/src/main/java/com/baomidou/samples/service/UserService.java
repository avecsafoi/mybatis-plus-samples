package com.baomidou.samples.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.samples.entity.User;
import com.baomidou.samples.mapper.UserMapper;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
