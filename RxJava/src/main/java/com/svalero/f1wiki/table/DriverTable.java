package com.svalero.f1wiki.table;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.domain.Student;

import java.util.List;

public class DriverTable {
    @SerializedName("Drivers")
    private List<Student> students;

    public List<Student> getDrivers() {
        return students;
    }
}
