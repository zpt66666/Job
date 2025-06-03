USE career_db;

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(20) NOT NULL COMMENT '用户名',
    `password` VARCHAR(64) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
    `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `user_type` TINYINT NOT NULL DEFAULT 0 COMMENT '用户类型（0-学生，1-企业，2-管理员）',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '账号状态（0-禁用，1-启用）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `user_skill` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `skill_name` VARCHAR(100) NOT NULL COMMENT '技能名称',
    `skill_level` TINYINT NOT NULL DEFAULT 0 COMMENT '技能水平（0-初级，1-中级，2-高级）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_skill` (`user_id`, `skill_name`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户技能评估表';

-- 用户简历基本信息表
CREATE TABLE IF NOT EXISTS user_resume (
                                           id INT PRIMARY KEY AUTO_INCREMENT COMMENT '简历ID',
                                           user_id BIGINT UNIQUE NOT NULL COMMENT '用户ID，与user表关联', -- 修改为BIGINT
                                           full_name VARCHAR(255) NOT NULL COMMENT '姓名',
                                           contact_email VARCHAR(255) NOT NULL COMMENT '联系邮箱',
                                           phone_number VARCHAR(20) COMMENT '电话号码',
                                           create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                           FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE -- 外键引用user表的BIGINT类型ID
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户简历基本信息表';

-- 教育经历表
CREATE TABLE IF NOT EXISTS resume_education (
                                                id INT PRIMARY KEY AUTO_INCREMENT COMMENT '教育经历ID',
                                                resume_id INT NOT NULL COMMENT '关联的简历ID', -- 外键引用user_resume(id) (INT)
                                                school_name VARCHAR(255) NOT NULL COMMENT '学校名称',
                                                degree VARCHAR(50) COMMENT '学历',
                                                major VARCHAR(255) COMMENT '专业',
                                                start_date DATE COMMENT '开始日期',
                                                end_date DATE COMMENT '结束日期',
                                                description TEXT COMMENT '教育背景描述',
                                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                FOREIGN KEY (resume_id) REFERENCES user_resume(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='简历教育背景表';

-- 工作/实习经历表
CREATE TABLE IF NOT EXISTS resume_experience (
                                                 id INT PRIMARY KEY AUTO_INCREMENT COMMENT '工作经历ID',
                                                 resume_id INT NOT NULL COMMENT '关联的简历ID', -- 外键引用user_resume(id) (INT)
                                                 company_name VARCHAR(255) NOT NULL COMMENT '公司名称',
                                                 job_title VARCHAR(255) COMMENT '职位',
                                                 start_date DATE COMMENT '开始日期',
                                                 end_date DATE COMMENT '结束日期',
                                                 description TEXT COMMENT '工作/实习经历描述',
                                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                 FOREIGN KEY (resume_id) REFERENCES user_resume(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='简历工作/实习经历表';

-- 技能表
CREATE TABLE IF NOT EXISTS resume_skill (
                                            id INT PRIMARY KEY AUTO_INCREMENT COMMENT '技能ID',
                                            resume_id INT NOT NULL COMMENT '关联的简历ID', -- 外键引用user_resume(id) (INT)
                                            skill_name VARCHAR(255) NOT NULL COMMENT '技能名称',
                                            level VARCHAR(50) COMMENT '技能水平',
                                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                            FOREIGN KEY (resume_id) REFERENCES user_resume(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='简历技能表';

-- 项目经历表
CREATE TABLE IF NOT EXISTS resume_project (
                                              id INT PRIMARY KEY AUTO_INCREMENT COMMENT '项目经历ID',
                                              resume_id INT NOT NULL COMMENT '关联的简历ID', -- 外键引用user_resume(id) (INT)
                                              project_name VARCHAR(255) NOT NULL COMMENT '项目名称',
                                              start_date DATE COMMENT '开始日期',
                                              end_date DATE COMMENT '结束日期',
                                              description TEXT COMMENT '项目描述',
                                              technologies TEXT COMMENT '使用技术栈',
                                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                              FOREIGN KEY (resume_id) REFERENCES user_resume(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='简历项目经历表';