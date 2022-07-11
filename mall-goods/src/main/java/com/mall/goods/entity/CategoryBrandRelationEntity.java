package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分类关联
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@Data
@TableName("gms_category_brand_relation")
public class CategoryBrandRelationEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 分类id
     */
    private Long catelogId;
    /**
     * 品牌name
     */
    @TableField(exist = false)
    private String brandName;
    /**
     * 分类name
     */
    @TableField(exist = false)
    private String catelogName;
}
