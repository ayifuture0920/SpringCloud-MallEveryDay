package com.mall.goods.controller;

import com.common.utils.PageUtils;
import com.common.utils.R;
import com.common.validator.AddGroup;
import com.common.validator.UpdateGroup;
import com.mall.goods.entity.BrandEntity;
import com.mall.goods.service.IBrandService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * 品牌
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@RestController
@RequestMapping("/goods/brand")
public class BrandController {
    @Resource
    private IBrandService brandService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list( @RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand){
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
        System.out.println("brand = " + brand);
        brandService.updateById(brand);
        return R.ok();
    }
    /**
     * 修改状态
     */
    @PutMapping("/update/status")
    public R updateStatus(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }
}
