# 大学生生涯规划系统

## 项目简介
这是一个面向大学生的生涯规划系统，旨在帮助大学生更好地规划自己的职业发展路径。系统提供简历制作、职业测评、面试准备等功能，帮助用户提升就业竞争力。

## 主要功能
- 用户管理
  - 用户注册与登录
  - 个人信息管理
  - 密码修改

- 简历管理
  - 在线简历制作
  - 多种简历模板选择
  - 简历内容编辑（教育经历、工作经验、项目经历、技能等）
  - 简历导出

- 职业测评
  - 职业兴趣测评
  - 职业能力评估
  - 职业规划建议

- 面试准备
  - 面试题库
  - 面试技巧指导
  - 模拟面试

## 技术栈
### 后端
- Spring Boot 2.x
- MyBatis
- MySQL
- Maven

### 前端
- HTML5
- CSS3
- JavaScript
- Bootstrap

## 系统要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+

## 快速开始
1. 克隆项目
```bash
git clone https://github.com/zpt66666/Job.git
```

2. 配置数据库
- 创建数据库
- 执行 `src/main/resources/db/create_tables.sql` 创建表结构
- 执行 `src/main/resources/db/create_admin.sql` 创建管理员账号

3. 修改配置
- 在 `src/main/resources/application.properties` 中配置数据库连接信息

4. 运行项目
```bash
mvn spring-boot:run
```

5. 访问系统
- 打开浏览器访问 `http://localhost:8080`

## 项目结构
```
src/main/java/com/baiyun/javaee/
├── common/          # 公共组件
├── config/          # 配置类
├── controller/      # 控制器
├── mapper/          # MyBatis映射接口
├── model/           # 数据模型
├── service/         # 服务层
└── vo/              # 视图对象

src/main/resources/
├── static/          # 静态资源
├── templates/       # 页面模板
├── mapper/          # MyBatis映射文件
└── application.properties  # 配置文件
```

## 贡献指南
1. Fork 本仓库
2. 创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开一个 Pull Request

## 许可证
本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系方式
- 项目维护者：zpt66666
- 邮箱：2436373793@qq.com

## 致谢
感谢所有为本项目做出贡献的开发者！