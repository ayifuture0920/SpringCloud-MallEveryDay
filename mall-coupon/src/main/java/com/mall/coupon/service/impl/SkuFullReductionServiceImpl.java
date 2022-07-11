package com.mall.coupon.service.impl;

import com.common.to.MemberPrice;
import com.common.to.SkuReductionTo;
import com.mall.coupon.entity.MemberPriceEntity;
import com.mall.coupon.entity.SkuLadderEntity;
import com.mall.coupon.service.IMemberPriceService;
import com.mall.coupon.service.ISkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.mall.coupon.mapper.SkuFullReductionMapper;
import com.mall.coupon.entity.SkuFullReductionEntity;
import com.mall.coupon.service.ISkuFullReductionService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionMapper, SkuFullReductionEntity> implements ISkuFullReductionService {

    @Resource
    private ISkuLadderService skuLadderService;

    @Resource
    IMemberPriceService memberPriceService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSkuReduction(SkuReductionTo reductionTo) {
        //1、5.4）、sku的优惠、满减等信息；mall_cms->cms_sku_ladder\cms_sku_full_reduction\cms_member_price
        //cms_sku_ladder
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(reductionTo.getSkuId());
        skuLadderEntity.setFullCount(reductionTo.getFullCount());
        skuLadderEntity.setDiscount(reductionTo.getDiscount());
        skuLadderEntity.setAddOther(reductionTo.getCountStatus());
        if(reductionTo.getFullCount() > 0){
            skuLadderService.save(skuLadderEntity);
        }
        //2、cms_sku_full_reduction
        SkuFullReductionEntity reductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(reductionTo,reductionEntity);
        if(reductionEntity.getFullPrice().compareTo(new BigDecimal("0"))==1){
            this.save(reductionEntity);
        }

        //3、cms_member_price
        List<MemberPrice> memberPrice = reductionTo.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().map(item -> {
            MemberPriceEntity priceEntity = new MemberPriceEntity();
            priceEntity.setSkuId(reductionTo.getSkuId());
            priceEntity.setMemberLevelId(item.getId());
            priceEntity.setMemberLevelName(item.getName());
            priceEntity.setMemberPrice(item.getPrice());
            priceEntity.setAddOther(1);
            return priceEntity;
        }).filter(item->item.getMemberPrice().compareTo(new BigDecimal("0")) == 1)
                .collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }
}