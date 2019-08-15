package com.doufuplus.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/14
 */
@SpringBootApplication
// @EnableTransactionManagement // 开启事务支持，事实上写不写都无多大关系，因为SpringBoot在TransactionAutoConfiguration类里已经为我们自动配置启用了该注解
@MapperScan({"com.doufuplus.boot.mapper"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}

