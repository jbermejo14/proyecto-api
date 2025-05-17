package com.svalero.proyectoapi.domain.dto;

import com.svalero.proyectoapi.domain.Course;
import com.svalero.proyectoapi.domain.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInDto {

        @NotBlank(message = "Name is required")
        private String name;

        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        private String email;

        @NotNull(message = "Registry date is required")
        @PastOrPresent(message = "Registry date cannot be in the future")
        private Date registryDate;

        @NotNull(message = "Verified status is required")
        private Boolean verified;
        private List<Long> courseIds;

        public static StudentInDto fromEntity(Student student) {
                List<Long> courseIds = student.getCourses() != null
                        ? student.getCourses().stream().map(Course::getId).toList()
                        : null;

                return new StudentInDto(
                        student.getName(),
                        student.getEmail(),
                        student.getRegistryDate(),
                        student.getVerified(),
                        courseIds
                );
        }
}



