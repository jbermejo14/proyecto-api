package com.svalero.RxJava.response;

import com.google.gson.annotations.SerializedName;
import com.svalero.RxJava.MRDataCourse;

public class CoursesResponse {
    @SerializedName("MRData")
    private MRDataCourse MRData;

    public MRDataCourse getMRData() {
        return MRData;
    }
}
