package com.baiyun.javaee.controller;

import com.baiyun.javaee.common.Result;
import com.baiyun.javaee.entity.UserResume;
import com.baiyun.javaee.service.UserResumeService;
import com.baiyun.javaee.vo.ResumeSaveRequest;
import com.baiyun.javaee.service.ResumeEducationService;
import com.baiyun.javaee.vo.ResumeEducationVO;
import com.baiyun.javaee.entity.ResumeEducation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.baiyun.javaee.service.ResumeExperienceService;
import com.baiyun.javaee.service.ResumeSkillService;
import com.baiyun.javaee.service.ResumeProjectService;
import com.baiyun.javaee.vo.ResumeExperienceVO;
import com.baiyun.javaee.vo.ResumeSkillVO;
import com.baiyun.javaee.vo.ResumeProjectVO;
import com.baiyun.javaee.entity.ResumeExperience;
import com.baiyun.javaee.entity.ResumeSkill;
import com.baiyun.javaee.entity.ResumeProject;
import org.springframework.http.ResponseEntity;
import com.baiyun.javaee.model.User;

@RestController
@RequestMapping("/api/resume")
public class ResumeApiController {

    @Autowired
    private UserResumeService userResumeService;

    @Autowired
    private ResumeEducationService resumeEducationService;

    @Autowired
    private ResumeExperienceService resumeExperienceService;

    @Autowired
    private ResumeSkillService resumeSkillService;

    @Autowired
    private ResumeProjectService resumeProjectService;

    @GetMapping("/current")
    public Result getCurrentResume(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == null) {
            return Result.error("请先登录");
        }
        Long userId = user.getId();
        // 获取基本信息
        UserResume resume = userResumeService.getResumeByUserId(userId);
        if (resume == null) {
             // 如果基本信息不存在，返回空的基本信息和空列表
            ResumeSaveRequest emptyResume = new ResumeSaveRequest();
            emptyResume.setEducations(List.of());
            emptyResume.setExperiences(List.of());
            emptyResume.setSkills(List.of());
            emptyResume.setProjects(List.of());
             return Result.success(emptyResume);
        }

        // 获取详细信息并合并
        ResumeSaveRequest responseData = new ResumeSaveRequest();
        responseData.setFullName(resume.getFullName());
        responseData.setContactEmail(resume.getContactEmail());
        responseData.setPhoneNumber(resume.getPhoneNumber());

        Integer resumeId = resume.getId();

        List<ResumeEducation> educations = resumeEducationService.getByResumeId(resumeId);
        List<ResumeEducationVO> educationVOs = educations.stream().map(edu -> {
            ResumeEducationVO vo = new ResumeEducationVO();
            vo.setId(edu.getId());
            vo.setSchoolName(edu.getSchoolName());
            vo.setDegree(edu.getDegree());
            vo.setMajor(edu.getMajor());
            vo.setStartDate(edu.getStartDate());
            vo.setEndDate(edu.getEndDate());
            vo.setDescription(edu.getDescription());
            return vo;
        }).collect(Collectors.toList());
        responseData.setEducations(educationVOs);

        List<ResumeExperience> experiences = resumeExperienceService.getByResumeId(resumeId);
        List<ResumeExperienceVO> experienceVOs = experiences.stream().map(exp -> {
            ResumeExperienceVO vo = new ResumeExperienceVO();
            vo.setId(exp.getId());
            vo.setCompanyName(exp.getCompanyName());
            vo.setJobTitle(exp.getJobTitle());
            vo.setStartDate(exp.getStartDate());
            vo.setEndDate(exp.getEndDate());
            vo.setDescription(exp.getDescription());
            return vo;
        }).collect(Collectors.toList());
        responseData.setExperiences(experienceVOs);

        List<ResumeSkill> skills = resumeSkillService.getByResumeId(resumeId);
        List<ResumeSkillVO> skillVOs = skills.stream().map(skill -> {
            ResumeSkillVO vo = new ResumeSkillVO();
            vo.setId(skill.getId());
            vo.setSkillName(skill.getSkillName());
            vo.setLevel(skill.getLevel());
            return vo;
        }).collect(Collectors.toList());
        responseData.setSkills(skillVOs);

        List<ResumeProject> projects = resumeProjectService.getByResumeId(resumeId);
        List<ResumeProjectVO> projectVOs = projects.stream().map(project -> {
            ResumeProjectVO vo = new ResumeProjectVO();
            vo.setId(project.getId());
            vo.setProjectName(project.getProjectName());
            vo.setStartDate(project.getStartDate());
            vo.setEndDate(project.getEndDate());
            vo.setDescription(project.getDescription());
            vo.setTechnologies(project.getTechnologies());
            return vo;
        }).collect(Collectors.toList());
        responseData.setProjects(projectVOs);

        return Result.success(responseData);
    }

    @PostMapping("/save-simple")
    public Result saveResume(@RequestBody ResumeSaveRequest request, HttpSession session) {
        System.out.println("ResumeApiController saveResume - Session ID: " + session.getId());
        User user = (User) session.getAttribute("user");
        System.out.println("ResumeApiController saveResume - User from session: " + user);
        if (user == null || user.getId() == null) {
            System.out.println("ResumeApiController saveResume - User is null or ID is null, returning '请先登录'");
            return Result.error("请先登录");
        }
        Long userId = user.getId();
        System.out.println("ResumeApiController saveResume - User ID: " + userId);

        // 保存基本信息
        UserResume resume = new UserResume();
        resume.setUserId(userId);
        resume.setFullName(request.getFullName());
        resume.setContactEmail(request.getContactEmail());
        resume.setPhoneNumber(request.getPhoneNumber());

        // 调用Service保存基本信息
        userResumeService.saveOrUpdateResume(resume);

        // 获取保存后的简历ID
        Integer resumeId = userResumeService.getResumeByUserId(userId).getId();

        // 保存教育经历
        if (request.getEducations() != null) {
            List<ResumeEducation> educationList = request.getEducations().stream().map(vo -> {
                ResumeEducation edu = new ResumeEducation();
                edu.setResumeId(resumeId);
                edu.setSchoolName(vo.getSchoolName());
                edu.setDegree(vo.getDegree());
                edu.setMajor(vo.getMajor());
                edu.setStartDate(vo.getStartDate());
                edu.setEndDate(vo.getEndDate());
                edu.setDescription(vo.getDescription());
                return edu;
            }).collect(Collectors.toList());
            resumeEducationService.saveBatch(resumeId, educationList);
        }

        // 保存工作经历
        if (request.getExperiences() != null) {
            List<ResumeExperience> experienceList = request.getExperiences().stream().map(vo -> {
                ResumeExperience exp = new ResumeExperience();
                exp.setResumeId(resumeId);
                exp.setCompanyName(vo.getCompanyName());
                exp.setJobTitle(vo.getJobTitle());
                exp.setStartDate(vo.getStartDate());
                exp.setEndDate(vo.getEndDate());
                exp.setDescription(vo.getDescription());
                return exp;
            }).collect(Collectors.toList());
            resumeExperienceService.saveBatch(resumeId, experienceList);
        }

        // 保存技能
        if (request.getSkills() != null) {
            List<ResumeSkill> skillList = request.getSkills().stream().map(vo -> {
                ResumeSkill skill = new ResumeSkill();
                skill.setResumeId(resumeId);
                skill.setSkillName(vo.getSkillName());
                skill.setLevel(vo.getLevel());
                return skill;
            }).collect(Collectors.toList());
            resumeSkillService.saveBatch(resumeId, skillList);
        }

        // 保存项目经历
        if (request.getProjects() != null) {
            List<ResumeProject> projectList = request.getProjects().stream().map(vo -> {
                ResumeProject project = new ResumeProject();
                project.setResumeId(resumeId);
                project.setProjectName(vo.getProjectName());
                project.setStartDate(vo.getStartDate());
                project.setEndDate(vo.getEndDate());
                project.setDescription(vo.getDescription());
                project.setTechnologies(vo.getTechnologies());
                return project;
            }).collect(Collectors.toList());
            resumeProjectService.saveBatch(resumeId, projectList);
        }

        return Result.success();
    }
} 