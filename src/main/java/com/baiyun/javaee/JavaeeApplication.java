package com.baiyun.javaee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import javax.sql.DataSource;   // 数据库连接核心类
import java.sql.Connection;    // SQL 连接类
import java.sql.DatabaseMetaData; // 数据库元数据类
import java.sql.SQLException;  // SQL 异常类

@SpringBootApplication(scanBasePackages = {"com.baiyun.javaee", "com.yourcompany.career"})
@MapperScan({"com.baiyun.javaee.mapper", "com.baiyun.javaee.repository"})
@ServletComponentScan
public class JavaeeApplication implements CommandLineRunner {

    // 注入 Spring Boot 自动配置的数据源（HikariCP）
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        SpringApplication.run(JavaeeApplication.class, args);
    }

    // 应用启动后自动执行的逻辑（测试数据库连接）
    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("✅ 数据库连接成功！");
            System.out.println("数据库产品名：" + meta.getDatabaseProductName());
            System.out.println("数据库版本：" + meta.getDatabaseProductVersion());
            System.out.println("驱动版本：" + meta.getDriverVersion());
        } catch (SQLException e) {
            System.err.println("❌ 数据库连接失败：" + e.getMessage());
            e.printStackTrace(); // 打印详细异常堆栈，便于排查
        }
    }
}