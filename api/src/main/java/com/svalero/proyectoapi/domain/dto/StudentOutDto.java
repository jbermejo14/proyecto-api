package com.svalero.proyectoapi.domain.dto;

import com.svalero.proyectoapi.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentOutDto {
    private Long id;
    private String name;
    private String email;
    private Date registryDate;
    private Boolean verified;
    private List<Long> courseIds;

}


