package com.yourcompany.career.controller;

import com.yourcompany.career.model.ActionPlan;
import com.yourcompany.career.repository.ActionPlanRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/action-plan")
public class ActionPlanController {

    @Autowired
    private ActionPlanRepositoryInMemory actionPlanRepository;

    @GetMapping
    public String showActionPlanPage(Model model) {
        // 可以在这里加载一些数据，如果action-plan页面需要显示现有计划的话
        // model.addAttribute("actionPlans", actionPlanRepository.findAll());
        return "action-plan"; // 返回 action-plan.html 模板
    }

    @PostMapping("/save")
    public String saveActionPlan(@RequestParam String title, @RequestParam String content) {
        ActionPlan actionPlan = new ActionPlan(title, content);
        actionPlanRepository.save(actionPlan);
        // 保存成功后重定向到行动计划页面或首页
        return "redirect:/action-plan";
    }

    @GetMapping("/api/latest")
    @ResponseBody // 返回 JSON 数据而不是模板
    public List<ActionPlan> getLatestActionPlans(@RequestParam(defaultValue = "5") int limit) {
        return actionPlanRepository.findLatest(limit);
    }
} 