package com.svalero.proyectoapi.domain.dto;

import com.svalero.proyectoapi.domain.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInDto {

        private String name;
        private String email;
        private Date registryDate;
        private Boolean verified;
        private List<Long> courseIds;
}



