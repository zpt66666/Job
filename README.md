# 职业生涯管理系统
1. 项目概况
本项目是一个基于 Spring Boot 的职业生涯管理系统，提供用户登录、个人信息、简历工具、模拟面试、行动计划等功能。
部署与运行说明文档：职业生涯管理系统

1.  **环境要求**:
    *   JDK 17 或更高版本
    *   Maven 3.6 或更高版本
    *   MySQL 8.0 或更高版本
    *   Node.js 14 或更高版本 (用于前端开发)

2.  **数据库配置**:
    *   创建数据库 `career_management`
    *   执行 `src/main/resources/schema.sql` 脚本创建表结构
    *   执行 `src/main/resources/data.sql` 脚本导入初始数据 (可选)
    *   修改 `src/main/resources/application.properties` 中的数据库连接信息

3.  **后端部署**:
    *   进入项目根目录
    *   执行 `mvn clean package` 打包项目
    *   执行 `java -jar target/career-management-0.0.1-SNAPSHOT.jar` 启动后端服务

4.  **前端部署**:
    *   进入 `frontend` 目录
    *   执行 `npm install` 安装依赖
    *   执行 `npm run build` 构建前端项目
    *   将 `dist` 目录下的文件部署到 Web 服务器 (如 Nginx)

5.  **访问系统**:
    *   打开浏览器，访问 `http://localhost:8080` (或您配置的 Web 服务器地址)
    *   使用默认账号 `admin` 密码 `123456` 登录系统

6.  **常见问题**:
    *   如果遇到端口冲突，请修改 `application.properties` 中的 `server.port` 配置
    *   如果遇到数据库连接问题，请检查数据库连接信息是否正确
    *   如果遇到前端资源加载问题，请检查 Web 服务器配置是否正确

7.  **开发说明**:
    *   后端开发请遵循 Spring Boot 开发规范
    *   前端开发请遵循 Vue.js 开发规范
    *   代码提交前请进行单元测试和集成测试

8.  **维护说明**:
    *   定期备份数据库
    *   定期检查日志文件
    *   定期更新系统依赖

9.  **联系方式**:
    *   如有问题，请联系系统管理员

10. **版权信息**:
    *   本系统版权归作者：JASON所有 

============================================================
问题总结与解决方案
============================================================
在本次开发过程中，我们遇到并解决了一系列问题，主要包括：



1.  **日志问题**: 解决了日志文件过大导致程序崩溃的问题。通过删除大型日志文件并配置 `logback-spring.xml` 限制日志大小、保留时间和总大小，有效控制了日志的产生。

2.  **行动计划功能**: 新增了"行动计划"功能。在前端 `index.html` 和 `action-plan.html` 添加了入口和显示区域，后端使用内存存储 (`ActionPlanRepositoryInMemory`) 实现了 `ActionPlan` 的保存和最新计划的获取 (`ActionPlanController`)。

3.  **登录问题**: 排查并解决了登录失败的问题。通过修改前端 `login.html` 的 AJAX 请求地址和方法，以及后端 `JavaeeApplication.java` 的包扫描配置，使登录功能恢复正常。

4.  **简历保存问题**: 逐步排查并解决了简历保存功能的问题。包括：
    *   统一前端 `resume-form.js` 和后端 `ResumeApiController` 的保存接口地址。
    *   修复前端 `resume-form.html` 中 JavaScript 文件未正确加载和事件监听的问题。
    *   修改后端从 Session 获取用户 ID 的逻辑，使其与登录时存储的键和值类型一致。
    *   发现了 `user_resume` 表中 `user_id` 类型与 `user` 表 `id` 类型不匹配的问题，并修改了相关的实体类、Mapper 接口和 XML 文件、Service 接口和实现类，将用户 ID 类型统一为 `Long`。
    *   解决了 `Invalid bound statement (not found)` 错误，通过创建或补充 `ResumeEducationMapper.xml`, `ResumeExperienceMapper.xml`, `ResumeProjectMapper.xml`, `ResumeSkillMapper.xml` 文件，并添加相应的 Mapper 语句定义。
    *   提出了数据库可能缺少简历详情表 (`resume_education`, `resume_experience`, `resume_project`, `resume_skill`) 的问题，并提供了相应的建表 SQL 建议。

当前正在解决的是 `ResumeSkillMapper.xml` 和 `ResumeProjectMapper.xml` 的绑定语句错误。