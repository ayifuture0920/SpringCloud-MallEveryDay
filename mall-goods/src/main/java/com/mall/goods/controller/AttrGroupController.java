package com.mall.goods.controller;


import com.common.utils.PageUtils;
import com.common.utils.R;
import com.mall.goods.entity.AttrAttrgroupRelationEntity;
import com.mall.goods.entity.AttrEntity;
import com.mall.goods.entity.AttrGroupEntity;
import com.mall.goods.service.IAttrAttrgroupRelationService;
import com.mall.goods.service.IAttrGroupService;
import com.mall.goods.service.IAttrService;
import com.mall.goods.service.ICategoryService;
import com.mall.goods.vo.AttrGroupWithAttrsVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@RestController
@RequestMapping("/goods/attrgroup")
public class AttrGroupController {

    @Resource
    private IAttrGroupService attrGroupService;

    @Resource
    private ICategoryService categoryService;

    @Resource
    private IAttrService attrService;

    @Resource
    private IAttrAttrgroupRelationService attrAttrgroupRelationService;
    /**
     * 列表
     */
    @GetMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable Integer catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, catelogId);
        return R.ok().put("page", page);
    }

    /**
     * 获取属性分组有关联的其他属性
     * @param attrgroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R getAttrgroupRelation(@PathVariable("attrgroupId") Long attrgroupId){

        List<AttrEntity> entityList = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data", entityList);
    }

    /**
     * 获取属性分组没有关联的其他属性
     */
    @GetMapping(value = "/{attrgroupId}/noattr/relation")
    public R getAttrgroupNoRelation(@RequestParam Map<String, Object> params,
                                    @PathVariable("attrgroupId") Long attrgroupId){
        PageUtils page = attrService.getAttrNoRelation(params, attrgroupId);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();

        Long[] path = categoryService.findCatelogPath(catelogId);
        attrGroup.setCatelogPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
        return R.ok();
    }

    // /goods/attrgroup/attr/relation/delete
    @DeleteMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrAttrgroupRelationEntity[] relations) {
        attrService.deleteRelation(relations);
        return R.ok();
    }

    /**
     * 添加属性与分组关联关系
     * [{
     *   "attrGroupId": 0, //分组id
     *   "attrId": 0, //属性id
     * }]
     * @param relations
     * @return
     */
    // /goods/attrgroup/attr/relation
    @PostMapping(value = "/attr/relation")
    public R addRelation(@RequestBody List<AttrAttrgroupRelationEntity> relations) {
        attrAttrgroupRelationService.saveBatch(relations);
        return R.ok();
    }

    /**
     * 获取分类下所有分组&关联属性
     * @param catelogId
     * @return
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId")Long catelogId){
        //1、查出当前分类下的所有属性分组，
        //2、查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> vos =  attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data", vos);
    }
}
