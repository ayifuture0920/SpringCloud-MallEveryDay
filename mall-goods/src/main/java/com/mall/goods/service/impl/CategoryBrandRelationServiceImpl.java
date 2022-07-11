package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;
import com.mall.goods.entity.BrandEntity;
import com.mall.goods.entity.CategoryBrandRelationEntity;
import com.mall.goods.entity.CategoryEntity;
import com.mall.goods.mapper.CategoryBrandRelationMapper;
import com.mall.goods.service.IBrandService;
import com.mall.goods.service.ICategoryBrandRelationService;
import com.mall.goods.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelationEntity> implements ICategoryBrandRelationService {

    @Resource
    private IBrandService brandService;

    @Resource
    private ICategoryService categoryService;

    /**
     * 分页查询
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>());
        return new PageUtils(page);
    }

    @Override
    public List<CategoryBrandRelationEntity> getCatlogList(Long brandId) {

        BrandEntity brand = brandService.getById(brandId);
        String brandName = brand.getName();
        List<CategoryBrandRelationEntity> entityList = this.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
        List<Long> catelogIdList = entityList.stream().map(entity -> entity.getCatelogId()).collect(Collectors.toList());
        List<CategoryEntity> categoryList = categoryService.listByIds(catelogIdList);
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).setBrandName(brandName);
            entityList.get(i).setCatelogName(categoryList.get(i).getName());
        }
        return entityList;
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<Long> brandIdList = this.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId))
                .stream()
                .map(entity -> entity.getBrandId())
                .collect(Collectors.toList());
        List<BrandEntity> brandList = brandService.listByIds(brandIdList);
        return brandList;
    }

}
