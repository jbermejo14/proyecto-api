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

    // --------------------- GET ---------------------

    // Test: obtener curso por ID (espera 200 OK)
    @Test
    public void shouldReturnCourseById200() throws Exception {
        Course course = new Course(null, "Curso test", "Descripción", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        course = courseRepository.save(course);

        mockMvc.perform(get("/course/" + course.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Curso test"));
    }

    // Test: obtener curso inexistente (espera 404 Not Found)
    @Test
    public void shouldReturn404WhenCourseNotFound() throws Exception {
        mockMvc.perform(get("/course/999999"))
                .andExpect(status().isNotFound());
    }

    // --------------------- POST ---------------------

    // Test: crear curso válido (espera 201 Created)
    @Test
    public void shouldCreateCourse201() throws Exception {
        CourseInDto dto = new CourseInDto("Nuevo Curso", "Descripción nueva", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Nuevo Curso"));
    }

    // Test: crear curso inválido (por ejemplo, título vacío) (espera 400 Bad Request)
    @Test
    public void shouldReturn400WhenInvalidCourse() throws Exception {
        CourseInDto dto = new CourseInDto("", "Sin título", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // --------------------- DELETE ---------------------

    // Test: eliminar curso existente (espera 204 No Content)
    @Test
    public void shouldDeleteCourse200() throws Exception {
        Course course = new Course(null, "Curso eliminar", "Para eliminar", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        course = courseRepository.save(course);

        mockMvc.perform(delete("/course/" + course.getId()))
                .andExpect(status().isNoContent());
    }

    // Test: eliminar curso inexistente (espera 404 Not Found)
    @Test
    public void shouldReturn404WhenDeletingNonexistentCourse() throws Exception {
        mockMvc.perform(delete("/course/88888"))
                .andExpect(status().isNotFound());
    }

    // --------------------- PUT ---------------------

    // Test: actualizar curso existente (espera 200 OK)
    @Test
    public void shouldUpdateCourse200() throws Exception {
        Course course = courseRepository.save(new Course(null, "Curso original", "Desc", Date.valueOf(LocalDate.now()), true, new ArrayList<>()));
        CourseInDto updatedDto = new CourseInDto("Curso modificado", "Desc mod", Date.valueOf(LocalDate.now()), false, new ArrayList<>());

        mockMvc.perform(put("/course/" + course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Curso modificado"));
    }

    // Test: actualizar curso inexistente (espera 404 Not Found)
    @Test
    public void shouldReturn404WhenUpdatingNonexistentCourse() throws Exception {
        CourseInDto dto = new CourseInDto("Curso", "Descripción", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(put("/course/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // Test: actualizar curso inválido (por ejemplo, título vacío) (espera 400 Bad Request)
    @Test
    public void shouldReturn400WhenUpdatingInvalidCourse() throws Exception {
        Course course = courseRepository.save(new Course(null, "Curso original", "Desc", Date.valueOf(LocalDate.now()), true, new ArrayList<>()));
        CourseInDto invalidDto = new CourseInDto("", "Descripción", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(put("/course/" + course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}
