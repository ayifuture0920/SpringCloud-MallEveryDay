package com.mall.goods.feign;

import com.common.to.SkuReductionTo;
import com.common.to.SpuBoundTo;
import com.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-coupon")
public interface ICouponFeignService {

    /**
     * 1、CouponFeignService.saveSpuBounds(spuBoundTo);
     *      1）、@RequestBody将这个对象转为json。
     *      2）、找到mall-coupon服务，给/coupon/spubounds/save发送请求。
     *          将上一步转的json放在请求体位置，发送请求；
     *      3）、对方服务收到请求。请求体里有json数据。
     *          (@RequestBody SpuBoundsEntity spuBounds)；将请求体的json转为SpuBoundsEntity；
     * 只要json数据模型是兼容的。双方服务无需使用同一个to
     * @param spuBoundTo
     * @return
     */
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
