package com.baomidou.mybatisplus.samples.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.metadata.IPage.PageType;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.pagination.entity.User;
import com.baomidou.mybatisplus.samples.pagination.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PageTypeTest {
    
    @Autowired
    private UserMapper mapper;
    
    // @Autowired
    // SqlSession session;
    
    @Test
    @Order(1)
    void t1() {
        Page<User> page = new Page<>(2, 3);
        page.pageType(PageType.ORDERS);
        page.addOrder(OrderItem.asc("name", " "));
        page.addOrder(OrderItem.asc("age", 0));
        page.addOrder(OrderItem.asc("id", " "));
        page.isFirst(false);
        //Page<User> result = mapper.selectPage(page, Wrappers.<User>lambdaQuery());
        Page<User> result = mapper.selectPage(page, Wrappers.<User>lambdaQuery().ge(User::getAge, 1).orderByAsc(User::getAge));
        assertThat(result.getTotal()).isGreaterThan(3);
        assertThat(result.getRecords().size()).isEqualTo(3);
        
        // mapper.selectPage(page, Wrappers.<User>lambdaQuery().ge(User::getAge, 1).orderByAsc(User::getAge));
        
        // session.getConfiguration().getMappedStatement("id").getBoundSql(result);
    }
}
