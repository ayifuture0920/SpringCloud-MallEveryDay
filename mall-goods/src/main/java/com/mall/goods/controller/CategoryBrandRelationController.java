package com.mall.goods.controller;

import com.common.utils.PageUtils;
import com.common.utils.R;
import com.mall.goods.entity.BrandEntity;
import com.mall.goods.entity.CategoryBrandRelationEntity;
import com.mall.goods.service.ICategoryBrandRelationService;
import com.mall.goods.vo.BrandVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 品牌分类关联
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@RestController
@RequestMapping("/goods/categorybrandrelation")
public class CategoryBrandRelationController {
    @Resource
    private ICategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取分类关联的品牌
     * @param catId
     * @return
     */
    @GetMapping("/brands/list")
    public R categoryRelationBrandList(@RequestParam("catId") Long catId ){
        List<BrandEntity> brandList =  categoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> brandVoList = brandList.stream().map(brand -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(brand.getBrandId());
            brandVo.setBrandName(brand.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data", brandVoList);
    }

    /**
     * 根据branId查询与品牌关联的所有分类列表
     * @param brandId
     * @return
     */
    @GetMapping("/catelog/list")
    public R catlogList(@RequestParam("brandId") Long brandId){

        List<CategoryBrandRelationEntity> entityList = categoryBrandRelationService.getCatlogList(brandId);
        return R.ok().put("data", entityList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.save(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
