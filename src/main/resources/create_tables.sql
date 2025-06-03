-- 简历基本信息表
CREATE TABLE user_resume (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE NOT NULL COMMENT '用户ID，与user表关联',
    full_name VARCHAR(255) NOT NULL COMMENT '姓名',
    contact_email VARCHAR(255) NOT NULL COMMENT '联系邮箱',
    phone_number VARCHAR(20) COMMENT '电话号码',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 简历教育背景表
CREATE TABLE resume_education (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL COMMENT '关联的简历ID',
    school_name VARCHAR(255) NOT NULL COMMENT '学校名称',
    degree VARCHAR(50) COMMENT '学历',
    major VARCHAR(255) COMMENT '专业',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    description TEXT COMMENT '教育背景描述',
    FOREIGN KEY (resume_id) REFERENCES user_resume(id) ON DELETE CASCADE
);

-- 简历工作/实习经历表
CREATE TABLE resume_experience (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL COMMENT '关联的简历ID',
    company_name VARCHAR(255) NOT NULL COMMENT '公司名称',
    job_title VARCHAR(255) COMMENT '职位',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    description TEXT COMMENT '工作/实习经历描述',
    FOREIGN KEY (resume_id) REFERENCES user_resume(id) ON DELETE CASCADE
);

-- 简历技能表
CREATE TABLE resume_skill (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL COMMENT '关联的简历ID',
    skill_name VARCHAR(255) NOT NULL COMMENT '技能名称',
    level VARCHAR(50) COMMENT '技能水平',
    FOREIGN KEY (resume_id) REFERENCES user_resume(id) ON DELETE CASCADE
);

-- 简历项目经历表
CREATE TABLE resume_project (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL COMMENT '关联的简历ID',
    project_name VARCHAR(255) NOT NULL COMMENT '项目名称',
    start_date DATE COMMENT '开始日期',
    end_date DATE DATE COMMENT '结束日期',
    description TEXT COMMENT '项目描述',
    technologies TEXT COMMENT '使用技术栈',
    FOREIGN KEY (resume_id) REFERENCES user_resume(id) ON DELETE CASCADE
); 