package com.baiyun.javaee.service;

import com.baiyun.javaee.entity.ResumeEducation;
import java.util.List;

public interface ResumeEducationService {
    List<ResumeEducation> getByResumeId(Integer resumeId);
    void saveBatch(Integer resumeId, List<ResumeEducation> list);
} 