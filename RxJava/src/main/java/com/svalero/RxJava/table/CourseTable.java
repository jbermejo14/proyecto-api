package com.svalero.RxJava.table;

import com.google.gson.annotations.SerializedName;
import com.svalero.RxJava.domain.Course;


import java.util.List;

public class CourseTable {
    @SerializedName("Courses")
    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }
}
