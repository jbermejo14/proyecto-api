package com.svalero.RxJava;

import com.google.gson.annotations.SerializedName;
import com.svalero.RxJava.domain.Course;
import java.util.List;

public class MRDataCourse {
    @SerializedName("CourseTable")
    private CourseTable courseTable;

    public CourseTable getCourseTable() {
        return courseTable;
    }

    public static class CourseTable {
        @SerializedName("Courses")
        private List<Course> courses;

        public List<Course> getCourses() {
            return courses;
        }
    }
}
