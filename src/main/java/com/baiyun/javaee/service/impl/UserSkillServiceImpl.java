package com.baiyun.javaee.service.impl;

import com.baiyun.javaee.common.exception.BusinessException;
import com.baiyun.javaee.model.User;
import com.baiyun.javaee.model.UserSkill;
import com.baiyun.javaee.repository.UserSkillMapper;
import com.baiyun.javaee.service.UserSkillService;
import com.baiyun.javaee.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户技能评估服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserSkillServiceImpl extends ServiceImpl<UserSkillMapper, UserSkill> implements UserSkillService {

    private final UserSkillMapper userSkillMapper;
    private final UserService userService;

    @Override
    public List<UserSkill> getUserSkills(Long userId) {
        QueryWrapper<UserSkill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userSkillMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void saveOrUpdateUserSkills(List<UserSkill> userSkillList, Long userId) {
        // TODO: 验证用户ID是否是当前登录用户，防止越权操作
        User currentUser = userService.getCurrentUser();
        if (currentUser == null || !currentUser.getId().equals(userId)) {
             throw BusinessException.unauthorized("无权操作");
        }

        // 先删除用户原有的技能评估
        QueryWrapper<UserSkill> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("user_id", userId);
        userSkillMapper.delete(deleteWrapper);

        // 保存新的技能评估
        if (userSkillList != null && !userSkillList.isEmpty()) {
            for (UserSkill userSkill : userSkillList) {
                userSkill.setUserId(userId);
                userSkill.setCreateTime(LocalDateTime.now());
                userSkill.setUpdateTime(LocalDateTime.now());
                userSkillMapper.insert(userSkill);
            }
        }
    }
} 