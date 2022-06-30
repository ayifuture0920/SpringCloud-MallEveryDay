package com.mall.order.controller;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import com.mall.order.entity.OrderSettingEntity;
import com.mall.order.service.IOrderSettingService;
import com.common.utils.PageUtils;
import com.common.utils.R;



/**
 * 订单配置信息
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-30 11:09:03
 */
@RestController
@RequestMapping("order/ordersetting")
public class OrderSettingController {
    @Resource
    private IOrderSettingService orderSettingService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderSettingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		OrderSettingEntity orderSetting = orderSettingService.getById(id);

        return R.ok().put("orderSetting", orderSetting);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderSettingEntity orderSetting){
		orderSettingService.save(orderSetting);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody OrderSettingEntity orderSetting){
		orderSettingService.updateById(orderSetting);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		orderSettingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
