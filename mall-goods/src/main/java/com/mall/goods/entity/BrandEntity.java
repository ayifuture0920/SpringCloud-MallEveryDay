package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.common.validator.AddGroup;
import com.common.validator.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@Data
@TableName("gms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @NotNull(message = "修改品牌id不能为null", groups = {UpdateGroup.class})
    @Null(message = "新增品牌id必须为null", groups = {AddGroup.class})
    @TableId(type = IdType.AUTO)
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotEmpty(message = "logo地址不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @URL(message = "logo地址不是一个合法的url", groups = {AddGroup.class, UpdateGroup.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotNull(message = "检索字母不能为null", groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索字母不是一个合法的字符", groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(message = "排序值不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "排序值不是一个>=0的整数", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;
}
