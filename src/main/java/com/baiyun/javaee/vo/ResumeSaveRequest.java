package com.baiyun.javaee.vo;

import java.util.List;

public class ResumeSaveRequest {
    private String fullName;
    private String contactEmail;
    private String phoneNumber;
    private List<ResumeEducationVO> educations;
    private List<ResumeExperienceVO> experiences;
    private List<ResumeSkillVO> skills;
    private List<ResumeProjectVO> projects;

    // Getters and setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ResumeEducationVO> getEducations() {
        return educations;
    }

    public void setEducations(List<ResumeEducationVO> educations) {
        this.educations = educations;
    }

    public List<ResumeExperienceVO> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ResumeExperienceVO> experiences) {
        this.experiences = experiences;
    }

    public List<ResumeSkillVO> getSkills() {
        return skills;
    }

    public void setSkills(List<ResumeSkillVO> skills) {
        this.skills = skills;
    }

    public List<ResumeProjectVO> getProjects() {
        return projects;
    }

    public void setProjects(List<ResumeProjectVO> projects) {
        this.projects = projects;
    }
} 