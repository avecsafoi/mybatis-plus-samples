package com.baomidou.mybatisplus.samples.jsonb.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.jsonb.entity.TestData;

@Mapper
public interface TestDataMapper extends BaseMapper<TestData> {

}
