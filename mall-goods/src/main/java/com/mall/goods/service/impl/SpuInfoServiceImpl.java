package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.to.SkuReductionTo;
import com.common.to.SpuBoundTo;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.common.utils.R;
import com.mall.goods.entity.*;
import com.mall.goods.feign.ICouponFeignService;
import com.mall.goods.mapper.SpuInfoMapper;
import com.mall.goods.service.*;
import com.mall.goods.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfoEntity> implements ISpuInfoService {

    @Resource
    private ISpuInfoDescService spuInfoDescService;

    @Resource
    private ISpuImagesService spuImagesService;

    @Resource
    private IAttrService attrService;

    @Resource
    private IProductAttrValueService attrValueService;

    @Resource
    private ICouponFeignService couponFeignService;

    @Resource
    private ISkuInfoService skuInfoService;

    @Resource
    private ISkuImagesService skuImagesService;

    @Resource ISkuSaleAttrValueService skuSaleAttrValueService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * //TODO 高级部分完善
     * @param vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        //1、保存spu基本信息 gms_spu_info
        SpuInfoEntity infoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo,infoEntity);
        infoEntity.setCreateTime(new Date());
        infoEntity.setUpdateTime(new Date());
        this.save(infoEntity);

        //2、保存Spu的描述图片 gms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(infoEntity.getId());
        descEntity.setDecript(String.join(",",decript));
        spuInfoDescService.save(descEntity);

        //3、保存spu的图片集 gms_spu_images
        List<String> images = vo.getImages();
        spuImagesService.saveImages(infoEntity.getId(),images);

        //4、保存spu的规格参数 gms_product_attr_value
        List<BaseAttrs> baseAttrList = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrList.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());
            AttrEntity attrEntity = attrService.getById(attr.getAttrId());
            valueEntity.setAttrName(attrEntity.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(infoEntity.getId());
            return valueEntity;
        }).collect(Collectors.toList());
        attrValueService.saveBatch(collect);

        //5、保存spu的积分信息；mall_cms->cms_spu_bounds
        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(infoEntity.getId());

        log.debug("++++++++++++++++++");
        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        log.debug("------------------");
        if(r.getCode() != 0){
            log.error("远程保存spu积分信息失败");
        }

        // 6. 保存当前spu对应的所有sku信息；
        List<Skus> skus = vo.getSkus();
        System.out.println("Skus = " + skus);
        if(skus != null && skus.size() > 0){
            skus.forEach(item -> {
                String defaultImg = "";
                for (Images image : item.getImages()) {
                    if(image.getDefaultImg() == 1){
                        defaultImg = image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item,skuInfoEntity);
                skuInfoEntity.setBrandId(infoEntity.getBrandId());
                skuInfoEntity.setCatalogId(infoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(infoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                //6.1）、sku的基本信息；gms_sku_info
                skuInfoService.save(skuInfoEntity);
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imagesEntityList = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity->{
                    //返回true就是需要，false就是剔除
                    return !StringUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                //6.2）、sku的图片信息；gms_sku_images
                skuImagesService.saveBatch(imagesEntityList);
                //TODO 没有图片路径的无需保存
                List<Attr> attrList = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntityList = attrList.stream().map(a -> {
                    SkuSaleAttrValueEntity attrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, attrValueEntity);
                    attrValueEntity.setSkuId(skuId);
                    return attrValueEntity;
                }).collect(Collectors.toList());
                //6.3）、sku的销售属性信息：gms_sku_sale_attr_value
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntityList);
                //6.4）、sku的优惠、满减等信息；mall_cms->cms_sku_ladder\cms_sku_full_reduction\cms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if(skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1){
                    R r1 = couponFeignService.saveSkuReduction(skuReductionTo);
                    if(r1.getCode() != 0){
                        log.error("远程保存sku优惠信息失败");
                    }
                }
            });

        }
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and(w-> w.eq("id", key).or().like("spu_name",key));
        }
        // status=1 and (id=1 or spu_name like xxx)
        String status = (String) params.get("status");
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("publish_status", status);
        }

        String brandId = (String) params.get("brandId");
        if(!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id", brandId);
        }

        String catelogId = (String) params.get("catelogId");
        if(!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id", catelogId);
        }
        /**
         * status: 2
         * key:
         * brandId: 9
         * catelogId: 225
         */
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

}