package com.svalero.f1wiki;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.table.StudentTable;

public class MRDataStudent {
    @SerializedName("StudentTable")
    private com.svalero.f1wiki.table.StudentTable StudentTable;

    public StudentTable getStudentTable() {
        return StudentTable;
    }
}
