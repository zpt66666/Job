package com.baiyun.javaee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

/**
 * 页面控制器
 */
@Controller
public class PageController {

    /**
     * 首页
     */
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        // 从会话中获取用户信息并添加到Model
        Object user = request.getSession().getAttribute("user");
        model.addAttribute("session", new java.util.HashMap<String, Object>() {{
            put("user", user);
        }}); // 将user对象包装在session map中，与模板中的${session.user}对应
        return "index";
    }

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 个人信息页面
     */
    @GetMapping("/personal-info")
    public String personalInfo() {
        return "personal-info";
    }

    /**
     * 职业规划页面
     */
    @GetMapping("/career-plan")
    public String careerPlan() {
        return "career-plan";
    }

    /**
     * 技能评估页面
     */
    @GetMapping("/skill-assessment")
    public String skillAssessment() {
        return "skill-assessment";
    }

    /**
     * 简历工具页面
     */
    @GetMapping("/resume-tool")
    public String resumeTool() {
        return "resume-tool";
    }

    /**
     * 填写简历信息页面
     */
    @GetMapping("/resume/fill")
    public String fillResume() {
        return "resume-form";
    }

    /**
     * 模拟面试页面
     */
    @GetMapping("/mock-interview")
    public String mockInterview() {
        return "mock-interview";
    }

    /**
     * 设置页面
     */
    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    // 添加日志管理页面的映射
    @GetMapping("/log-viewer")
    public String logViewer() {
        return "log-viewer";
    }
} 