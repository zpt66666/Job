package com.baiyun.javaee.vo;
import java.sql.Date;
public class ResumeProjectVO {
    private Integer id;
    private String projectName;
    private Date startDate;
    private Date endDate;
    private String description;
    private String technologies;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTechnologies() { return technologies; }
    public void setTechnologies(String technologies) { this.technologies = technologies; }
} 