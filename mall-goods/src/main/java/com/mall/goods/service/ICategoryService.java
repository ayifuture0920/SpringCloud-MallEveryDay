package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.CategoryEntity;

import java.util.Map;

public interface ICategoryService extends IService<CategoryEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
