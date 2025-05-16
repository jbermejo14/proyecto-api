package com.svalero.f1wiki.response;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.MRDataConstructor;

public class CoursesResponse {
    @SerializedName("MRData")
    private MRDataConstructor MRData;

    public MRDataConstructor getMRData() {
        return MRData;
    }
}