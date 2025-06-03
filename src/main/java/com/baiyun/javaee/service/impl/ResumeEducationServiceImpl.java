package com.baiyun.javaee.service.impl;

import com.baiyun.javaee.entity.ResumeEducation;
import com.baiyun.javaee.mapper.ResumeEducationMapper;
import com.baiyun.javaee.service.ResumeEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeEducationServiceImpl implements ResumeEducationService {
    @Autowired
    private ResumeEducationMapper resumeEducationMapper;

    @Override
    public List<ResumeEducation> getByResumeId(Integer resumeId) {
        return resumeEducationMapper.selectByResumeId(resumeId);
    }

    @Override
    public void saveBatch(Integer resumeId, List<ResumeEducation> list) {
        resumeEducationMapper.deleteByResumeId(resumeId);
        if (list != null && !list.isEmpty()) {
            for (ResumeEducation edu : list) {
                edu.setId(null); // 保证主键自增
            }
            resumeEducationMapper.insertBatch(list);
        }
    }
} 