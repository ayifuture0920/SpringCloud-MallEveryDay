package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.AttrAttrgroupRelationEntity;
import com.mall.goods.entity.AttrEntity;


import java.util.List;
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

    void saveAttr(AttrEntity attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrEntity getAttrInfo(Long attrId);

    void updateAttrById(AttrEntity attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrAttrgroupRelationEntity[] relations);

    void removeAttr(List<Long> attrIdList);

    PageUtils getAttrNoRelation(Map<String, Object> params, Long attrgroupId);

}
