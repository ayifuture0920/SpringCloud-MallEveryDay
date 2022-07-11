package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.goods.entity.AttrGroupEntity;
import com.mall.goods.vo.AttrGroupWithAttrsVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("attrGroupService")
public interface IAttrGroupService extends IService<AttrGroupEntity> {
    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params, Integer catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}
