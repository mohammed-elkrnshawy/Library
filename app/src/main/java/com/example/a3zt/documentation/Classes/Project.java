package com.example.a3zt.documentation.Classes;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
    private String DoctorUID;
    private String Title;
    private String Description;
    private String Department;
    private String Year;
    private List<String> StudentNames;
    private String Category;
    private List<String>KeyWords;
    private String DownloadUel;
    private String UID;
    //AdapterConstractor
    public Project(String doctorUID, String title, String description, String department,
                   String year, List<String> studentNames, String category, List<String> keyWords,String downloadUel) {
        DoctorUID = doctorUID;
        Title = title;
        Description = description;
        Department = department;
        Year = year;
        StudentNames = studentNames;
        Category = category;
        KeyWords = keyWords;
        DownloadUel=downloadUel;
    }

    public Project(String doctorUID, String title, String description, String department,
                   String year, List<String> studentNames, String category, List<String> keyWords,
                   String downloadUel, String UID) {
        DoctorUID = doctorUID;
        Title = title;
        Description = description;
        Department = department;
        Year = year;
        StudentNames = studentNames;
        Category = category;
        KeyWords = keyWords;
        DownloadUel = downloadUel;
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getDownloadUel() {
        return DownloadUel;
    }

    public void setDownloadUel(String downloadUel) {
        DownloadUel = downloadUel;
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
