package com.mall.goods;

import com.mall.goods.entity.AttrEntity;
import com.mall.goods.service.IAttrService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class MallGoodsApplicationTests {

    @Resource
    private IAttrService attrService;
    @Test
    void contextLoads() {
        AttrEntity attrEntity = new AttrEntity(null, "huawei", 0, 0, "", "", 0, 0L, 0L, 0);
        attrService.save(attrEntity);
        System.out.println("Success");
    }

}
