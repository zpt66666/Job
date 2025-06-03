-- 用户简历表
CREATE TABLE IF NOT EXISTS user_resume (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    contact_email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 教育经历表
CREATE TABLE IF NOT EXISTS resume_education (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL,
    school_name VARCHAR(100) NOT NULL,
    degree VARCHAR(50) NOT NULL,
    major VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES user_resume(id)
);

-- 工作经历表
CREATE TABLE IF NOT EXISTS resume_experience (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    job_title VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES user_resume(id)
);

-- 技能表
CREATE TABLE IF NOT EXISTS resume_skill (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL,
    skill_name VARCHAR(100) NOT NULL,
    level VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES user_resume(id)
);

-- 项目经历表
CREATE TABLE IF NOT EXISTS resume_project (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL,
    project_name VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    technologies VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES user_resume(id)
); 