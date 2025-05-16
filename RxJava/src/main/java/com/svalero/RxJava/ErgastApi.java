package com.svalero.RxJava;

import com.svalero.RxJava.domain.Course;
import com.svalero.RxJava.response.CoursesResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ErgastApi {

    @GET("course")
    Call<List<Course>> getCourses();

}