package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;
import com.mall.goods.entity.CategoryEntity;
import com.mall.goods.mapper.CategoryMapper;
import com.mall.goods.service.ICategoryService;
import com.mysql.cj.log.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements ICategoryService {

    /**
     * 分页查询
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1.查出所有分类
        List<CategoryEntity> entityList = this.list();
        // 2.组装成父子的属性结构
        // 2.1 找到所有的一级分类
        List<CategoryEntity> level1Menus = entityList.stream()
                .filter(entity -> entity.getParentCid() == 0)
                .map(menu -> {
                    menu.setChildren(children(menu, entityList));
                    return menu;
                }).sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()))
                .collect(Collectors.toList());
        return level1Menus;
    }
    /**
     * 递归获取每个类别的子类别
     * @param entity
     * @param entityList
     * @return
     */
    private List<CategoryEntity> children(CategoryEntity entity, List<CategoryEntity> entityList){
        if(entity.getCatLevel() < 3){
            List<CategoryEntity> menus = entityList.stream()
                    .filter(e -> entity.getCatId().equals(e.getParentCid()))
                    .map(menu -> {
                        // 1.递归找到子菜单
                        menu.setChildren(children(menu, entityList));
                        return menu;
                    }).sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()))
                    .collect(Collectors.toList());
            return menus;
        }
        return null;
    }
    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 1.检查当前删除的菜单是否被别的地方引用

        // 逻辑删除
        this.removeByIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> path = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, path);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    private List<Long> findParentPath(Long catelogId, List<Long> path){
        CategoryEntity category = this.getById(catelogId);
        Long parentCid = category.getParentCid();
        if(parentCid != 0){
            findParentPath(parentCid, path);
        }
        path.add(catelogId);
        return path;
    }

}
