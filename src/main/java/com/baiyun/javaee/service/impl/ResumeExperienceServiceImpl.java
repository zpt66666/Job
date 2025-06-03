package com.baiyun.javaee.service.impl;

import com.baiyun.javaee.entity.ResumeExperience;
import com.baiyun.javaee.mapper.ResumeExperienceMapper;
import com.baiyun.javaee.service.ResumeExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResumeExperienceServiceImpl implements ResumeExperienceService {
    @Autowired
    private ResumeExperienceMapper resumeExperienceMapper;

    @Override
    public List<ResumeExperience> getByResumeId(Integer resumeId) {
        return resumeExperienceMapper.selectByResumeId(resumeId);
    }

    @Override
    public void saveBatch(Integer resumeId, List<ResumeExperience> list) {
        resumeExperienceMapper.deleteByResumeId(resumeId);
        if (list != null && !list.isEmpty()) {
            for (ResumeExperience exp : list) {
                exp.setId(null);
            }
            resumeExperienceMapper.insertBatch(list);
        }
    }
} 