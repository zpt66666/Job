package com.baiyun.javaee.service.impl;

import com.baiyun.javaee.entity.ResumeSkill;
import com.baiyun.javaee.mapper.ResumeSkillMapper;
import com.baiyun.javaee.service.ResumeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResumeSkillServiceImpl implements ResumeSkillService {
    @Autowired
    private ResumeSkillMapper resumeSkillMapper;

    @Override
    public List<ResumeSkill> getByResumeId(Integer resumeId) {
        return resumeSkillMapper.selectByResumeId(resumeId);
    }

    @Override
    public void saveBatch(Integer resumeId, List<ResumeSkill> list) {
        resumeSkillMapper.deleteByResumeId(resumeId);
        if (list != null && !list.isEmpty()) {
            for (ResumeSkill skill : list) {
                skill.setId(null);
            }
            resumeSkillMapper.insertBatch(list);
        }
    }
} 