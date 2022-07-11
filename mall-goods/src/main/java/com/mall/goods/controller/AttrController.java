package com.mall.goods.controller;

import com.common.utils.PageUtils;
import com.common.utils.R;
import com.mall.goods.entity.AttrEntity;
import com.mall.goods.entity.ProductAttrValueEntity;
import com.mall.goods.service.IAttrService;
import com.mall.goods.service.IProductAttrValueService;
import com.mall.goods.service.ISpuInfoService;
import com.mall.goods.vo.AttrVo;
import com.mall.goods.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@RestController
@RequestMapping("goods/attr")
public class AttrController {

    @Resource
    private IAttrService attrService;

    @Resource
    private ISpuInfoService spuInfoService;

    @Resource
    private IProductAttrValueService attrValueService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);
        return R.ok().put("page", page);
    }

    // /product/attr/base/listforspu/{spuId}
    /**
     * 获取spu规格
     * @param spuId
     * @return
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){

        List<ProductAttrValueEntity> entities = attrValueService.baseAttrlistforspu(spuId);

        return R.ok().put("data",entities);
    }

    //goods/attr/sale/list/{catelogId}?
    //goods/attr/base/list/{catelogId}?
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType") String type){
        PageUtils page = attrService.queryBaseAttrPage(params, catelogId, type);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
        AttrEntity attr = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:spuinfo:save")
    public R save(@RequestBody AttrEntity attr){

        attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody AttrEntity attr){
        attrService.updateAttrById(attr);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
        attrService.removeAttr(Arrays.asList(attrIds));

        return R.ok();
    }


}
