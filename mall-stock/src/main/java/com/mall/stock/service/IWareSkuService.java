package com.mall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.stock.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-30 14:44:15
 */
public interface IWareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

}

