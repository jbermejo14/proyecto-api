package com.svalero.proyectoapi.domain.dto;

import com.svalero.proyectoapi.domain.Student;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInDto {
    private Long id;
    private String title;
    private String description;
    private Date startDate;
    private Boolean active;
    private List<Student> students;
}