package com.baiyun.javaee.repository;

import com.baiyun.javaee.model.UserSkill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户技能评估Mapper接口
 */
@Mapper
public interface UserSkillMapper extends BaseMapper<UserSkill> {
    // 可以在这里添加自定义的数据库操作方法
} 