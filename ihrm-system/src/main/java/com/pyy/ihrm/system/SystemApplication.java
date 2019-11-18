package com.pyy.ihrm.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringBootApplication(scanBasePackages = "com.pyy.ihrm")
@MapperScan(basePackages = {"com.pyy.ihrm.system.mapper"})
@EnableTransactionManagement
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
