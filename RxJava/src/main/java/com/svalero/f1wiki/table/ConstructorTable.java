package com.svalero.f1wiki.table;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.domain.Course;


import java.util.List;

public class ConstructorTable {
    @SerializedName("Constructors")
    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }
}
