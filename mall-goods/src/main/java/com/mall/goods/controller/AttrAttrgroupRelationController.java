package com.mall.goods.controller;

import com.common.utils.PageUtils;
import com.common.utils.R;

import com.common.validator.AddGroup;
import com.mall.goods.entity.AttrAttrgroupRelationEntity;
import com.mall.goods.entity.AttrEntity;
import com.mall.goods.feign.ICouponFeignService;
import com.mall.goods.service.IAttrAttrgroupRelationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@RefreshScope
@RestController
@RequestMapping("goods/attrattrgrouprelation")
public class AttrAttrgroupRelationController {

    @Resource
    private IAttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrAttrgroupRelationService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        AttrAttrgroupRelationEntity attrAttrgroupRelation = attrAttrgroupRelationService.getById(id);

        return R.ok().put("attrAttrgroupRelation", attrAttrgroupRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save( @RequestBody AttrAttrgroupRelationEntity attrAttrgroupRelation ){
        attrAttrgroupRelationService.save(attrAttrgroupRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody AttrAttrgroupRelationEntity attrAttrgroupRelation){
        attrAttrgroupRelationService.updateById(attrAttrgroupRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        attrAttrgroupRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


}
