package com.greymatter.gmworkspace.model;

public class ProjectInfoModel {
    private int LeadImage,DeveloperImage;
    private String ProjectTile;
    private String ProjectDescription;

    public int getLeadImage() {
        return LeadImage;
    }

    public void setLeadImage(int leadImage) {
        LeadImage = leadImage;
    }

    public int getDeveloperImage() {
        return DeveloperImage;
    }

    public void setDeveloperImage(int developerImage) {
        DeveloperImage = developerImage;
    }

    public String getProjectTile() {
        return ProjectTile;
    }

    public void setProjectTile(String projectTile) {
        ProjectTile = projectTile;
    }

    public String getProjectDescription() {
        return ProjectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        ProjectDescription = projectDescription;
    }

    public ProjectInfoModel(int leadImage, int developerImage, String projectTile, String projectDescription) {
        LeadImage = leadImage;
        DeveloperImage = developerImage;
        ProjectTile = projectTile;
        ProjectDescription = projectDescription;
    }
}
