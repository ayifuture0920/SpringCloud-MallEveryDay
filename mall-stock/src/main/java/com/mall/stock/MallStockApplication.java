package com.mall.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients("com.mall.stock.feign")
@EnableTransactionManagement //开启事务
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.mall.stock.mapper")
public class MallStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallStockApplication.class, args);
    }

}
