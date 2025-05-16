package com.svalero.f1wiki;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.table.CourseTable;

public class MRDataCourse {
    @SerializedName("CourseTable")
    private CourseTable CourseTable;

    public CourseTable getCourseTable() {
        return CourseTable;
    }
}

