package com.svalero.f1wiki;

import com.svalero.f1wiki.response.StudentsResponse;
import com.svalero.f1wiki.response.CoursesResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ErgastApi {
    
    @GET("f1/students.json")
    Call<StudentsResponse> getStudents();

    @GET("f1/courses.json")
    Call<CoursesResponse> getCourses();
}