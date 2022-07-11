package com.mall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsMapping方法实现跨域
 */
/*@Configuration
public class MallCorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("+++++++++++++++");
        registry.addMapping("/api/**") //为指定的路径启用跨域请求处理
                .allowedHeaders("*")
                .allowedOriginPatterns("*") //允许从浏览器进行跨域请求的来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") //允许跨域的HTTP方法
                .allowCredentials(true) //允许携带cookie跨域
                .maxAge(3600); //设置询问间隔时间，在第一次询问是否同意跨域的3600s内浏览器不再询问
    }
}*/
/**
 * CorsWebFilter 方法实现跨域
 * @return
 */
@Configuration
public class MallCorsConfiguration{

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1.配置跨域
        //允许跨域的头部信息
        corsConfiguration.addAllowedHeader("*");
        //允许跨域的请求方法
        corsConfiguration.addAllowedMethod("*");
        //允许跨域的请求来源
        corsConfiguration.addAllowedOriginPattern("*");
        //允许携带cookie跨域
        corsConfiguration.setAllowCredentials(true);
        //任意路径都要进行跨域配置
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }
}
