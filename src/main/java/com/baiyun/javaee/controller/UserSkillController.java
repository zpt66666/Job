package com.baiyun.javaee.controller;

import com.baiyun.javaee.common.ApiResponse;
import com.baiyun.javaee.common.exception.BusinessException;
import com.baiyun.javaee.model.User;
import com.baiyun.javaee.model.UserSkill;
import com.baiyun.javaee.service.UserSkillService;
import com.baiyun.javaee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户技能评估控制器
 */
@RestController
@RequestMapping("/api/user-skills")
@RequiredArgsConstructor
public class UserSkillController {

    private final UserSkillService userSkillService;
    private final UserService userService;

    /**
     * 获取当前用户的技能评估列表
     *
     * @return 技能评估列表
     */
    @GetMapping("/current")
    public ApiResponse<List<UserSkill>> getCurrentUserSkills() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw BusinessException.unauthorized("用户未登录");
        }
        List<UserSkill> userSkills = userSkillService.getUserSkills(currentUser.getId());
        return ApiResponse.success(userSkills);
    }

    /**
     * 保存或更新当前用户的技能评估
     *
     * @param userSkillList 技能评估列表
     * @return 保存结果
     */
    @PostMapping("/save")
    public ApiResponse<?> saveOrUpdateUserSkills(@RequestBody List<UserSkill> userSkillList) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw BusinessException.unauthorized("用户未登录");
        }
        userSkillService.saveOrUpdateUserSkills(userSkillList, currentUser.getId());
        return ApiResponse.success("技能评估保存成功");
    }

    // TODO: 可以根据需要添加删除单个技能评估等接口
} 