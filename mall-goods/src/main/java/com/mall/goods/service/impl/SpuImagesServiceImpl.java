package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;
import com.mall.goods.entity.SpuImagesEntity;
import com.mall.goods.mapper.SpuImagesMapper;
import com.mall.goods.service.ISpuImagesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImagesEntity> implements ISpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long spuId, List<String> images) {
        if(images != null && images.size() != 0){
            List<SpuImagesEntity> entityList = images.stream().map(url -> {
                SpuImagesEntity entity = new SpuImagesEntity();
                entity.setSpuId(spuId);
                entity.setImgUrl(url);
                return entity;
            }).collect(Collectors.toList());
            this.saveBatch(entityList);
        }
    }

}