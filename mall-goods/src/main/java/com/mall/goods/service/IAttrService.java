package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
public interface IAttrService extends IService<AttrEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
