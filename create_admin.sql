USE career_db;

-- 插入管理员账号，密码为 'admin123'
INSERT INTO `user` (
    `username`,
    `password`,
    `email`,
    `real_name`,
    `user_type`,
    `status`
) VALUES (
    'admin',
    '$2a$10$N.ZOn9G6/YLFixAOPMg/h.z7pCu6v2XyFDtC4q.jeeGM/qdhJ5.li',
    'admin@example.com',
    '管理员',
    2,  -- 管理员类型
    1   -- 启用状态
);
