package com.svalero.proyectoapi.domain.dto;

import com.svalero.proyectoapi.domain.Student;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInDto {
    @NotBlank(message = "El título es obligatorio")
    private String title;
    @NotBlank(message = "La descripción es obligatoria")
    private String description;
    private Date startDate;
    @NotNull(message = "El campo active es obligatorio")
    private Boolean active;
    private List<Student> students;
}