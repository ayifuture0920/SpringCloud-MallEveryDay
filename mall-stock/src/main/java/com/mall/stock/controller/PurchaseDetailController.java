package com.mall.stock.controller;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import com.mall.stock.entity.PurchaseDetailEntity;
import com.mall.stock.service.IPurchaseDetailService;
import com.common.utils.PageUtils;
import com.common.utils.R;



/**
 * 
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-30 14:44:15
 */
@RestController
@RequestMapping("stock/purchasedetail")
public class PurchaseDetailController {
    @Resource
    private IPurchaseDetailService purchaseDetailService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(id);

        return R.ok().put("purchaseDetail", purchaseDetail);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail){
		purchaseDetailService.save(purchaseDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody PurchaseDetailEntity purchaseDetail){
		purchaseDetailService.updateById(purchaseDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		purchaseDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
