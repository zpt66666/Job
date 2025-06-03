package com.baiyun.javaee.mapper;

import com.baiyun.javaee.entity.ResumeExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ResumeExperienceMapper {
    List<ResumeExperience> selectByResumeId(@Param("resumeId") Integer resumeId);
    void deleteByResumeId(@Param("resumeId") Integer resumeId);
    void insertBatch(@Param("list") List<ResumeExperience> list);
} 