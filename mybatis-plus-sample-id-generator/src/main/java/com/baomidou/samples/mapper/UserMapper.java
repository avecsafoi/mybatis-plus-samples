package com.baomidou.samples.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.samples.entity.User;

/**
 * @author nieqiuqiu 2019/11/30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
