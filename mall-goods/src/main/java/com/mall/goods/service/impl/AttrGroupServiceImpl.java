package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;
import com.mall.goods.entity.AttrEntity;
import com.mall.goods.entity.AttrGroupEntity;
import com.mall.goods.mapper.AttrGroupMapper;
import com.mall.goods.service.IAttrGroupService;
import com.mall.goods.service.IAttrService;
import com.mall.goods.vo.AttrGroupWithAttrsVo;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroupEntity> implements IAttrGroupService {

    @Resource
    private IAttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );
        return new PageUtils(page);
    }

    /**
     * 分页查询
     * @param params
     * @param catelogId
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Integer catelogId) {
        // 根据关键字查询
        /*select * from gms_attr_group where catelog_id = ? and (attr_group_id = ${key} or attr_group_name like %${key}%)*/
        String key = (String)params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();
        if(!StringUtils.isEmpty(key)){
            wrapper.and(obj -> obj.eq("attr_group_id", key).or().like("attr_group_name", key));
        }
        if(catelogId != 0){
            wrapper.eq("catelog_id", catelogId);
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //1、查询分组信息
        List<AttrGroupEntity> attrGroupList = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

        //2、查询所有属性
        List<AttrGroupWithAttrsVo> collect = attrGroupList.stream().map(group -> {
            AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group,attrsVo);
            List<AttrEntity> attrs = attrService.getRelationAttr(attrsVo.getAttrGroupId());
            attrsVo.setAttrs(attrs);
            return attrsVo;
        }).collect(Collectors.toList());

        return collect;
    }
}
