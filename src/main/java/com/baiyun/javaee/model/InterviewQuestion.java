package com.baiyun.javaee.model;

// 如果您使用 MyBatis-Plus，可以添加相关注解
// import com.baomidou.mybatisplus.annotation.IdType;
// import com.baomidou.mybatisplus.annotation.TableId;
// import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

// 如果使用 MyBatis-Plus，可以取消注释 @TableName
// @TableName("interview_question")
@Data
public class InterviewQuestion {

    // 如果使用 MyBatis-Plus，可以取消注释 @TableId
    // @TableId(type = IdType.AUTO)
    private Long id;
    private String category; // 题目分类，例如：Java, Python, 前端, 数据库等
    private String question;
    private String answer;
    // 您可以根据需要添加其他字段，例如：
    // private Integer difficulty; // 难度等级
    // private java.time.LocalDateTime createdAt; // 创建时间

} 