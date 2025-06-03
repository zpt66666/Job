package com.baiyun.javaee.service;

import com.baiyun.javaee.entity.UserResume;

public interface UserResumeService {
    UserResume getResumeByUserId(Long userId);

    void saveOrUpdateResume(UserResume userResume);
} 