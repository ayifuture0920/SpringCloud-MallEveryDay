package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * spu信息介绍
 * 
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-27
 */
@Data
@TableName("gms_spu_info_desc")
public class SpuInfoDescEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId(type = IdType.AUTO)
	private Long spuId;
	/**
	 * 商品介绍
	 */
	private String decript;

}
