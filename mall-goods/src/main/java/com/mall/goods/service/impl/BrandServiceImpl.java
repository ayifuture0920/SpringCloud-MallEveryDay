package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;
import com.mall.goods.entity.BrandEntity;
import com.mall.goods.mapper.BrandMapper;
import com.mall.goods.service.IBrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements IBrandService {

    /**
     * 分页查询 模糊查询
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 1.获取key
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            queryWrapper.eq("brand_id", key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }
}
