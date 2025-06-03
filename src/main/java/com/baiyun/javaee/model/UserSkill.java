package com.baiyun.javaee.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户技能评估实体类
 */
@Data
@Accessors(chain = true)
@TableName("user_skill")
public class UserSkill {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 技能名称
     */
    private String skillName;

    /**
     * 技能水平（0-初级，1-中级，2-高级）
     */
    private Integer skillLevel;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 