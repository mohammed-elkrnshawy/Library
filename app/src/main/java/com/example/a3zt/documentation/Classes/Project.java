package com.example.a3zt.documentation.Classes;

import java.util.List;

public class Project {
    private String DoctorUID;
    private String Title;
    private String Description;
    private String Department;
    private String Year;
    private List<String> StudentNames;
    private String Category;
    private List<String>KeyWords;

    //AdapterConstractor
    public Project(String doctorUID, String title, String description, String department,
                   String year, List<String> studentNames, String category, List<String> keyWords) {
        DoctorUID = doctorUID;
        Title = title;
        Description = description;
        Department = department;
        Year = year;
        StudentNames = studentNames;
        Category = category;
        KeyWords = keyWords;
    }

    //AdapterUpload
    public Project(String title, String description, String department,
                   String year, List<String> studentNames, String category, List<String> keyWords) {
        Title = title;
        Description = description;
        Department = department;
        Year = year;
        StudentNames = studentNames;
        Category = category;
        KeyWords = keyWords;
    }

    public String getDoctorUID() {
        return DoctorUID;
    }

    public void setDoctorUID(String doctorUID) {
        DoctorUID = doctorUID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public List<String> getStudentNames() {
        return StudentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        StudentNames = studentNames;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public List<String> getKeyWords() {
        return KeyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        KeyWords = keyWords;
    }
}
