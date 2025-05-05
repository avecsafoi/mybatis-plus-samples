package com.baomidou.mybatisplus.samples.dytablename;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.samples.dytablename.config.RequestDataHelper;
import com.baomidou.mybatisplus.samples.dytablename.entity.User;
import com.baomidou.mybatisplus.samples.dytablename.mapper.UserMapper;

/**
 * <p>
 * 内置 动态表名 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@SpringBootTest
class DynamicTableNameTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicTableNameTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    void test() {
        try {
            RequestDataHelper.setRequestData(new HashMap<String, Object>() {{
                put("id", 123);
                put("hello", "tomcat");
                put("name", "汤姆凯特");
            }});
            // 自己去观察打印 SQL 目前随机访问 user_2018  user_2019 表
            for (int i = 0; i < 6; i++) {
                User user = userMapper.selectById(1);
                LOGGER.info("userName:{}", user.getName());
            }
        } finally {
            RequestDataHelper.removeRequestData();
        }
    }
}
