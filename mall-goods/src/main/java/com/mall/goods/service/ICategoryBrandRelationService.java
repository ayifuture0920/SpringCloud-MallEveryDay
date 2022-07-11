package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.BrandEntity;
import com.mall.goods.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

public interface ICategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryBrandRelationEntity> getCatlogList(Long brandId);

    List<BrandEntity> getBrandsByCatId(Long catId);
}
