package com.svalero.f1wiki.response;

import com.svalero.f1wiki.domain.Student;

import java.util.List;

public class StudentsResponse {

    public MRData MRData;

    public MRData getMRData() {
        return MRData;
    }

    public static class MRData {

        public StudentTable StudentTable;

        public StudentTable getStudentTable() {
            return StudentTable;
        }

        public static class StudentTable {
            private List<Student> students;

            public List<Student> getStudents() {
                return students;
            }
        }
    }
}
