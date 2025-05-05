package com.baomidou.mybatisplus.samples.association.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.association.entity.Company;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

    Company testResultMapCollection();
}
