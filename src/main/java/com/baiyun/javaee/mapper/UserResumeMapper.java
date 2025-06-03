package com.baiyun.javaee.mapper;

import com.baiyun.javaee.entity.UserResume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserResumeMapper {
    UserResume selectByUserId(@Param("userId") Long userId);

    void insert(UserResume resume);

    void update(UserResume resume);
} 