package com.mall.goods.controller;

import com.common.utils.PageUtils;
import com.common.utils.R;

import com.mall.goods.entity.CategoryEntity;
import com.mall.goods.service.ICategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@RestController
@RequestMapping("/goods/category")
public class CategoryController {
    @Resource
    private ICategoryService categoryService;

    /**
     * 查出所有商品分类以及子分类，以树形结构组装起来，类似于京东首页商品分类框
     */
    @GetMapping("/list/tree")
    public R list(){
        List<CategoryEntity> entityList = categoryService.listWithTree();
        return R.ok().put("data", entityList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CategoryEntity category){
        categoryService.save(category);
        return R.ok();
    }

    /**
     * 批量修改
     */
    @PutMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CategoryEntity category){
        categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
