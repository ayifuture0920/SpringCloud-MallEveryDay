package com.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mall.user.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author tangqingao
 * @email tangqingao@foxmail.com
 * @date 2022-06-30 16:02:43
 */
public interface IMemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

