package com.baiyun.javaee.mapper;

import com.baiyun.javaee.entity.ResumeEducation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResumeEducationMapper {
    List<ResumeEducation> selectByResumeId(@Param("resumeId") Integer resumeId);
    void deleteByResumeId(@Param("resumeId") Integer resumeId);
    void insertBatch(@Param("list") List<ResumeEducation> list);
} 