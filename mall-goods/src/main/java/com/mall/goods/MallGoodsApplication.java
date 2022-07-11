package com.mall.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 1.整合MyBatis-Plus
 *  1.1 导入依赖
 *  1.2 配置
 *   1.2.1 配置数据源
 *    1.2.1.1 导入mysql的依赖
 *    1.2.1.2 在application.yml中配置数据源相关信息
 *   1.2.2 配置MyBatis-Plus
 *    1.2.2.1使用@MapperScan
 * 2.逻辑删除
 *  2.1 配置全局的逻辑删除规则（可以省略）
 *  2.2 配置逻辑删除的组件Bean（可以省略）
 *  2.3 给Bean加上逻辑删除注解@TableLogic
 *
 * 3. JSR303
 *  3.1 给Bean添加校验注解：javax.validation.constraints，并自定义message提示
 *  3.2 在Controller层开启校验功能@Valid
 *      效果：校验错误以后会有默认的响应
 *  3.3 在校验的Bean后跟上BindingResult，可以获得校验的结果
 *  3.4 分组校验
 *   3.4.1 @NotBlank(message = "品牌名为空", groups = {AddGroup.class, UpdateGroup.class})
 *   给校验注解标注什么情况需要进行校验
 *   3.4.2 @Validated({AddGroup.class})
 *   3.4.3 默认没有指定分组的校验注解 @NotBlank，在分组校验情况下不生效
 * 4. 统一异常处理 @ControllerAdvice
 *  4.1 编写异常处理类，使用@ControllerAdvice
 *  4.2 使用@ExceptionHandler标注方法可以处理的异常
 */

@EnableTransactionManagement
@EnableFeignClients(basePackages = "com.mall.goods.feign")
@EnableDiscoveryClient
@MapperScan("com.mall.goods.mapper")
@SpringBootApplication
public class MallGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallGoodsApplication.class, args);
    }

}
