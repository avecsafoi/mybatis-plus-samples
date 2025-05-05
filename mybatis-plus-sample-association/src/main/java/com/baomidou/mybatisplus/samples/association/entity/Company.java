package com.baomidou.mybatisplus.samples.association.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("company")
public class Company {
	private Long id;
    private String name;
    List<User> userList;

}
