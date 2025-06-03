package com.baiyun.javaee.service;

import com.baiyun.javaee.model.UserSkill;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户技能评估服务接口
 */
public interface UserSkillService extends IService<UserSkill> {

    /**
     * 获取当前用户的技能评估列表
     *
     * @param userId 用户ID
     * @return 技能评估列表
     */
    List<UserSkill> getUserSkills(Long userId);

    /**
     * 保存或更新用户技能评估
     *
     * @param userSkillList 技能评估列表
     * @param userId 用户ID
     */
    void saveOrUpdateUserSkills(List<UserSkill> userSkillList, Long userId);
} 