package com.mall.goods;

import com.mall.goods.entity.CategoryEntity;

import com.mall.goods.feign.ICouponFeignService;
import com.mall.goods.service.IBrandService;
import com.mall.goods.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class MallGoodsApplicationTests {

    @Resource
    private ICategoryService categoryService;

    @Resource
    private IBrandService brandService;

    @Resource
    private ICouponFeignService feignService;

    @Test
    void contextLoads() {
        List<CategoryEntity> entityList = categoryService.list();
        entityList.stream().map(entity -> entity.getName());//.forEach(System.out::println);
    }

    @Test
    void test(){
    }
}