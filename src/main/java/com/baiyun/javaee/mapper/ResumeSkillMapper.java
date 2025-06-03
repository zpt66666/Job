package com.baiyun.javaee.mapper;

import com.baiyun.javaee.entity.ResumeSkill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ResumeSkillMapper {
    List<ResumeSkill> selectByResumeId(@Param("resumeId") Integer resumeId);
    void deleteByResumeId(@Param("resumeId") Integer resumeId);
    void insertBatch(@Param("list") List<ResumeSkill> list);
} 