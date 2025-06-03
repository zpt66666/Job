package com.baiyun.javaee.service.impl;

import com.baiyun.javaee.entity.UserResume;
import com.baiyun.javaee.mapper.UserResumeMapper;
import com.baiyun.javaee.service.UserResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResumeServiceImpl implements UserResumeService {

    @Autowired
    private UserResumeMapper userResumeMapper;

    @Override
    public UserResume getResumeByUserId(Long userId) {
        return userResumeMapper.selectByUserId(userId);
    }

    @Override
    public void saveOrUpdateResume(UserResume resume) {
        UserResume existingResume = userResumeMapper.selectByUserId(resume.getUserId());
        if (existingResume != null) {
            resume.setId(existingResume.getId());
            userResumeMapper.update(resume);
        } else {
            userResumeMapper.insert(resume);
        }
    }
} 