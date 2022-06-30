package com.mall.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1.整合MyBatis-Plus
 *  1.1 导入依赖
 *  1.2 配置
 *   1.2.1 配置数据源
 *    1.2.1.1 导入mysql的依赖
 *    1.2.1.2 在application.yml中配置数据源相关信息
 *   1.2.2 配置MyBatis-Plus
 *    1.2.2.1使用@MapperScan
 */

@MapperScan("com.mall.goods.mapper")
@SpringBootApplication
public class MallGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallGoodsApplication.class, args);
    }

}
