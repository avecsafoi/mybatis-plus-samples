package com.baomidou.mybatisplus.samples.assembly.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.assembly.entity.User;

/**
 * @author nieqiuqiu
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
