package com.baomidou.mybatisplus.samples.association.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.samples.association.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectUserPage(IPage<User> page, @Param("ew") QueryWrapper<User> wrapper);
}
