package com.svalero.proyectoapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svalero.proyectoapi.domain.Course;
import com.svalero.proyectoapi.domain.dto.CourseInDto;
import com.svalero.proyectoapi.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnCourseById200() throws Exception {
        Course course = new Course(null, "Curso test", "Descripción", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        course = courseRepository.save(course);

        mockMvc.perform(get("/course/" + course.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Curso test"));
    }

    @Test
    public void shouldReturn404WhenCourseNotFound() throws Exception {
        mockMvc.perform(get("/course/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateCourse201() throws Exception {
        CourseInDto dto = new CourseInDto("Nuevo Curso", "Descripción nueva", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Nuevo Curso"));
    }

    @Test
    public void shouldReturn400WhenInvalidCourse() throws Exception {
        // Por ejemplo, falta el título o está vacío
        CourseInDto dto = new CourseInDto("", "Sin título", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteCourse200() throws Exception {
        Course course = new Course(null, "Curso eliminar", "Para eliminar", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        course = courseRepository.save(course);

        mockMvc.perform(delete("/course/" + course.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonexistentCourse() throws Exception {
        mockMvc.perform(delete("/courses/88888"))
                .andExpect(status().isNotFound());
    }
}
