package com.baomidou.mybatisplus.samples.association.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.samples.association.entity.User;

public interface IUserService extends IService<User> {

    List<User> selectUserPage(IPage<User> page, QueryWrapper<User> wrapper);
}
