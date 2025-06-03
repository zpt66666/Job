package com.baiyun.javaee.service.impl;

import com.baiyun.javaee.entity.ResumeProject;
import com.baiyun.javaee.mapper.ResumeProjectMapper;
import com.baiyun.javaee.service.ResumeProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResumeProjectServiceImpl implements ResumeProjectService {
    @Autowired
    private ResumeProjectMapper resumeProjectMapper;

    @Override
    public List<ResumeProject> getByResumeId(Integer resumeId) {
        return resumeProjectMapper.selectByResumeId(resumeId);
    }

    @Override
    public void saveBatch(Integer resumeId, List<ResumeProject> list) {
        resumeProjectMapper.deleteByResumeId(resumeId);
        if (list != null && !list.isEmpty()) {
            for (ResumeProject project : list) {
                project.setId(null);
            }
            resumeProjectMapper.insertBatch(list);
        }
    }
} 