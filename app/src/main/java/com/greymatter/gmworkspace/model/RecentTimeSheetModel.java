package com.greymatter.gmworkspace.model;

public class RecentTimeSheetModel {
    private String ProjectName;
    private String ProjectDescription;
    private String VerifiedStatus;
    private String ProjectDate;
    private String ProjectWorkedHours;

    public RecentTimeSheetModel(String projectName, String projectDescription, String verifiedStatus, String projectDate, String projectWorkedHours) {
        ProjectName = projectName;
        ProjectDescription = projectDescription;
        VerifiedStatus = verifiedStatus;
        ProjectDate = projectDate;
        ProjectWorkedHours = projectWorkedHours;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectDescription() {
        return ProjectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        ProjectDescription = projectDescription;
    }

    public String getVerifiedStatus() {
        return VerifiedStatus;
    }

    public void setVerifiedStatus(String verifiedStatus) {
        VerifiedStatus = verifiedStatus;
    }

    public String getProjectDate() {
        return ProjectDate;
    }

    public void setProjectDate(String projectDate) {
        ProjectDate = projectDate;
    }

    public String getProjectWorkedHours() {
        return ProjectWorkedHours;
    }

    public void setProjectWorkedHours(String projectWorkedHours) {
        ProjectWorkedHours = projectWorkedHours;
    }
}
