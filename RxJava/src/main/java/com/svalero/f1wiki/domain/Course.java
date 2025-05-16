package com.svalero.f1wiki.domain;

import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("courseId")
    private String courseId;

    @SerializedName("name")
    private String name;

    @SerializedName("nationality")
    private String nationality;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
