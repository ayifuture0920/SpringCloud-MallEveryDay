package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@Data
@TableName("gms_attr")
public class AttrEntity implements Serializable {
    private static final long serialVersion = 1L;

    /**
     * 属性id
     */
    @TableId(type = IdType.AUTO)
    private Long attrId;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    private Integer searchType;
    /**
     * 值类型[0-为单个值，1-可以选择多个值]
     */
    private Integer valueType;
    /**
     * 属性图标
     */
    private String icon;
    /**
     * 可选值列表[用逗号分隔]
     */
    private String valueSelect;
    /**
     * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
     */
    private Integer attrType;
    /**
     * 启用状态[0 - 禁用，1 - 启用]
     */
    private Long enable;
    /**
     * 所属分类
     */
    private Long catelogId;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    private Integer showDesc;

    /**
     * 规格参数所属分组id
     */
    @TableField(exist = false)
    private Long attrGroupId;

    /**
     * 规格参数所属分类名称
     */
    @TableField(exist = false)
    private String catelogName;

    /**
     * 规格参数所属分组名称
     */
    @TableField(exist = false)
    private String groupName;

    /**
     * 规格参数所属分类名称路径
     */
    @TableField(exist = false)
    private Long[] catelogPath;
}
