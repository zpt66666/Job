package com.baiyun.javaee.service;

import com.baiyun.javaee.entity.ResumeExperience;
import java.util.List;

public interface ResumeExperienceService {
    List<ResumeExperience> getByResumeId(Integer resumeId);
    void saveBatch(Integer resumeId, List<ResumeExperience> list);
} 