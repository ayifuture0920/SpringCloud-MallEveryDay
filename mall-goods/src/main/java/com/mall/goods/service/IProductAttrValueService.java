package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27 16:39:26
 */
public interface IProductAttrValueService extends IService<ProductAttrValueEntity> {
    PageUtils queryPage(Map<String, Object> params);

    List<ProductAttrValueEntity> baseAttrlistforspu(Long spuId);
}
