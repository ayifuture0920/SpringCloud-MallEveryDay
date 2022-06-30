package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.BrandEntity;

import java.util.Map;

public interface IBrandService extends IService<BrandEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
