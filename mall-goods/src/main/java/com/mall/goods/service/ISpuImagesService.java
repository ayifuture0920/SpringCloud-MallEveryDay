package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.SpuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * spu图片
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27 16:39:23
 */
public interface ISpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveImages(Long spuId, List<String> images);
}

