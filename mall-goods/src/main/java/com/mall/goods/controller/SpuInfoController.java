package com.mall.goods.controller;

import com.common.to.SpuBoundTo;
import com.common.utils.PageUtils;
import com.common.utils.R;
import com.mall.goods.entity.SpuInfoEntity;
import com.mall.goods.feign.ICouponFeignService;
import com.mall.goods.service.ISpuInfoService;
import com.mall.goods.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;



/**
 * spu信息
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27 16:39:24
 */
@RestController
@RequestMapping("goods/spuinfo")
public class SpuInfoController {
    @Resource
    private ISpuInfoService spuInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 新增商品
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:spuinfo:save")
    public R save(@RequestBody SpuSaveVo vo){
        //spuInfoService.save(spuInfo);

        spuInfoService.saveSpuInfo(vo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
