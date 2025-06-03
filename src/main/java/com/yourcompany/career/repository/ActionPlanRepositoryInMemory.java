package com.yourcompany.career.repository;

import com.yourcompany.career.model.ActionPlan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ActionPlanRepositoryInMemory {

    private final List<ActionPlan> actionPlans = Collections.synchronizedList(new ArrayList<>());

    public void save(ActionPlan actionPlan) {
        actionPlans.add(actionPlan);
    }

    public List<ActionPlan> findLatest(int limit) {
        // 按照创建时间降序排序，并获取最新的指定数量的行动计划
        return actionPlans.stream()
                .sorted(Comparator.comparing(ActionPlan::getCreatedAt).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<ActionPlan> findAll() {
        return new ArrayList<>(actionPlans);
    }
} 