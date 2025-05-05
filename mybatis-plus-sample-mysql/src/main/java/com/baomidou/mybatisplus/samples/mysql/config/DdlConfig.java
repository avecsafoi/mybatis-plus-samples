package com.baomidou.mybatisplus.samples.mysql.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.ddl.DdlScript;

@Configuration
public class DdlConfig {

    /**
     * 注入脚本执行类，支持自定义执行脚本
     */
    @Bean
    public DdlScript ddlScript(DataSource dataSource) {
        return new DdlScript(dataSource);
    }
}
