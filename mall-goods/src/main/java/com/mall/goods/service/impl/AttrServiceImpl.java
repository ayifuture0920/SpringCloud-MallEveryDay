package com.mall.goods.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.constant.GoodsConstant;
import com.common.utils.PageUtils;
import com.common.utils.Query;
import com.mall.goods.entity.AttrAttrgroupRelationEntity;
import com.mall.goods.entity.AttrEntity;
import com.mall.goods.entity.AttrGroupEntity;
import com.mall.goods.entity.CategoryEntity;
import com.mall.goods.mapper.AttrAttrgroupRelationMapper;
import com.mall.goods.mapper.AttrMapper;
import com.mall.goods.service.IAttrGroupService;
import com.mall.goods.service.IAttrService;
import com.mall.goods.service.ICategoryService;
import com.mall.goods.vo.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrMapper, AttrEntity> implements IAttrService {

    @Resource
    private AttrAttrgroupRelationMapper relationMapper;

    @Resource
    private ICategoryService categoryService;

    @Resource
    private IAttrGroupService attrGroupService;
    /**
     * 分页查询
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>());
        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAttr(AttrEntity attr) {
        // 1.保存基本数据
        this.save(attr);
        // 2.保存关联关系
        //判断类型，如果是基本属性就设置分组id
        if(attr.getAttrType() == GoodsConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId() != null){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();

            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());
            relationMapper.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {

        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type", "base".equalsIgnoreCase(type)
                        ? GoodsConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                        : GoodsConstant.AttrEnum.ATTR_TYPE_SALE.getCode()
                );

        if(catelogId != 0){
            wrapper.eq("catelog_id", catelogId);
        }

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and(queryWrapper -> queryWrapper.eq("attr_id", key).or().like("attr_name", key));
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pageUtils = new PageUtils(page);

        List<AttrEntity> records = page.getRecords();
        List<AttrEntity> respList = records.stream().map(attr -> {
            AttrAttrgroupRelationEntity relationEntity = relationMapper
                    .selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if (relationEntity != null) {
                Long attrGroupId = relationEntity.getAttrGroupId();
                attr.setAttrGroupId(attrGroupId);

                attr.setGroupName(attrGroupService.getById(attrGroupId).getAttrGroupName());
            }
            CategoryEntity categoryEntity = categoryService.getById(attr.getCatelogId());

            if (categoryEntity != null) {
                attr.setCatelogName(categoryEntity.getName());
            }

            return attr;
        }).collect(Collectors.toList());
        pageUtils.setList(respList);
        return pageUtils;
    }

    @Override
    public AttrEntity getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);

        //判断是否是基本类型
        if (attrEntity.getAttrType() == GoodsConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //1、设置分组信息
            AttrAttrgroupRelationEntity relationEntity = relationMapper
                    .selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));

            if (relationEntity != null) {
                attrEntity.setAttrGroupId(relationEntity.getAttrGroupId());
                //获取分组名称
                AttrGroupEntity attrGroupEntity = attrGroupService.getById(relationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    attrEntity.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        attrEntity.setCatelogPath(catelogPath);

        CategoryEntity categoryEntity = categoryService.getById(catelogId);
        if (categoryEntity != null) {
            attrEntity.setCatelogName(categoryEntity.getName());
        }
        return attrEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAttrById(AttrEntity attr) {
        this.updateById(attr);

        if (attr.getAttrType() == GoodsConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //1、修改分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());

            Long count = relationMapper.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attr.getAttrId()));
            if (count > 0) {
                relationMapper.update(relationEntity,
                        new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            } else {
                relationMapper.insert(relationEntity);
            }
        }
    }

    /**
     * 根据分组attrgroupId找到关联的所有属性
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> relationList = relationMapper.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId)
        );
        if(relationList == null || relationList.size() == 0){
            return null;
        }
        List<Long> attrIdList = relationList.stream()
                .map(relation -> relation.getAttrId())
                .collect(Collectors.toList());

        List<AttrEntity> attrEntityList = this.listByIds(attrIdList);
        return attrEntityList;
    }

    @Override
    public void deleteRelation(AttrAttrgroupRelationEntity[] relations) {
        Arrays.asList(relations).stream().forEach(relation -> {
            UpdateWrapper<AttrAttrgroupRelationEntity> wrapper = new UpdateWrapper<>();
            relationMapper.delete(wrapper.eq("attr_id", relation.getAttrId()).eq("attr_group_id", relation.getAttrGroupId()));
        });
    }

    @Override
    public void removeAttr(List<Long> attrIdList) {
        this.removeByIds(attrIdList);
        attrIdList.stream().forEach( attrId -> {
            relationMapper.delete(new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
        });
    }

    /**
     * 获取当前分组没有被关联的所有属性
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getAttrNoRelation(Map<String, Object> params, Long attrgroupId) {
        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupService.getById(attrgroupId);
        //获取当前分类的id
        Long catelogId = attrGroupEntity.getCatelogId();
        //2、当前分组只能关联别的分组没有引用的属性
        //2.1）、当前分类下的其它分组
        List<AttrGroupEntity> groupEntities = attrGroupService.list(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));
        //获取到所有的attrGroupId
        List<Long> groupIdList = groupEntities.stream()
                .map(entity -> entity.getAttrGroupId())
                .collect(Collectors.toList());
        //2.2）、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId = relationMapper.selectList
                (new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIdList));
        List<Long> attrIdList = groupId.stream()
                .map(item -> item.getAttrId())
                .collect(Collectors.toList());

        //2.3）、从当前分类的所有属性移除这些属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId).eq("attr_type",GoodsConstant.AttrEnum.ATTR_TYPE_BASE.getCode());

        if (attrIdList != null && attrIdList.size() > 0) {
            queryWrapper.notIn("attr_id", attrIdList);
        }
        //判断是否有参数进行模糊查询
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(w -> w.eq("attr_id",key).or().like("attr_name",key));
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }
}
