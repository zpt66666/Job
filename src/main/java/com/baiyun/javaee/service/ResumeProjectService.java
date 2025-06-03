package com.baiyun.javaee.service;

import com.baiyun.javaee.entity.ResumeProject;
import java.util.List;

public interface ResumeProjectService {
    List<ResumeProject> getByResumeId(Integer resumeId);
    void saveBatch(Integer resumeId, List<ResumeProject> list);
} 