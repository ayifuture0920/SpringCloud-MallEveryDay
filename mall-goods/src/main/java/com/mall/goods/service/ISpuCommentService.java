package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27 16:39:23
 */
public interface ISpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

