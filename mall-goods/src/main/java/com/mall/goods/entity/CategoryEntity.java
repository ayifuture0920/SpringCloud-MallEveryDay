package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品三级分类
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@Data
@TableName("gms_category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    @TableId(type = IdType.AUTO)
    private Long catId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类id
     */
    private Long parentCid;
    /**
     * 层级
     */
    private Integer catLevel;
    /**
     * 是否显示[0-不显示，1显示]
     */
    @TableLogic // 逻辑删除
    private Integer showStatus;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 计量单位
     */
    private String productUnit;
    /**
     * 商品数量
     */
    private Integer productCount;

    /**
     * 商品类别包含的子分类
     */
    @TableField(exist = false)
    private List<CategoryEntity> children;
}
