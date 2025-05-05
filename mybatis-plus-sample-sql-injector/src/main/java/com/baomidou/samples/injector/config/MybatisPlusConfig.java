package com.baomidou.samples.injector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.samples.injector.base.MySqlInjector;

/**
 * @author K
 * @since 2019/7/9
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }
}
