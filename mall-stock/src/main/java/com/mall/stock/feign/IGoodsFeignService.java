package com.mall.stock.feign;

import com.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("mall-goods")
public interface IGoodsFeignService {

    @GetMapping("/goods/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
