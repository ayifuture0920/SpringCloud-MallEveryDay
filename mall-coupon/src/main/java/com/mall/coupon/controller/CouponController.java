package com.mall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import com.mall.coupon.entity.CouponEntity;
import com.mall.coupon.service.ICouponService;
import com.common.utils.PageUtils;
import com.common.utils.R;



/**
 * 优惠券信息
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-30 12:16:41
 */
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {
    @Resource
    private ICouponService couponService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
