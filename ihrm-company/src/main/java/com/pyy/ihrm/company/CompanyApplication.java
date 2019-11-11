package com.pyy.ihrm.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * ========================
 * 项目启动类
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/11 17:16
 * Version: v1.0
 * ========================
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.pyy.ihrm.company.mapper"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableScheduling
public class CompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class, args);
    }
}
