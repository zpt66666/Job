package com.baiyun.javaee.service;

import com.baiyun.javaee.entity.ResumeSkill;
import java.util.List;

public interface ResumeSkillService {
    List<ResumeSkill> getByResumeId(Integer resumeId);
    void saveBatch(Integer resumeId, List<ResumeSkill> list);
} 