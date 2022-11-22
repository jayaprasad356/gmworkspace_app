package com.greymatter.gmworkspace.model;

public class RecentTimeSheetModel {
    private String name;
    private String client_id;
    private String status;
    private String hours;
    private String description;
    private String project_id;
    private String staff_id;
    private String date;
    private String id;


    public RecentTimeSheetModel(){

    }

    public RecentTimeSheetModel(String name, String client_id, String status, String hours, String description, String project_id, String staff_id, String date, String id) {
        this.name = name;
        this.client_id = client_id;
        this.status = status;
        this.hours = hours;
        this.description = description;
        this.project_id = project_id;
        this.staff_id = staff_id;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
