package com.mall.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mall.stock.mapper")
public class MallStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallStockApplication.class, args);
    }

}
