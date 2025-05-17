package com.svalero.proyectoapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svalero.proyectoapi.domain.Student;
import com.svalero.proyectoapi.domain.dto.StudentInDto;
import com.svalero.proyectoapi.repository.StudentRepository;
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
public class StudentApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // --------------------- GET ---------------------

    @Test
    public void shouldReturnStudentById200() throws Exception {
        Student student = new Student(null, "John Doe", "john@example.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        student = studentRepository.save(student);

        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void shouldReturn404WhenStudentNotFound() throws Exception {
        mockMvc.perform(get("/student/999999"))
                .andExpect(status().isNotFound());
    }

    // --------------------- POST ---------------------

    @Test
    public void shouldCreateStudent201() throws Exception {
        StudentInDto dto = new StudentInDto("Alice", "alice@example.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void shouldReturn400WhenInvalidStudent() throws Exception {
        StudentInDto dto = new StudentInDto("", "invalid@example.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // --------------------- DELETE ---------------------

    @Test
    public void shouldDeleteStudent204() throws Exception {
        Student student = new Student(null, "Delete Me", "delete@example.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        student = studentRepository.save(student);

        mockMvc.perform(delete("/student/" + student.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonexistentStudent() throws Exception {
        mockMvc.perform(delete("/student/88888"))
                .andExpect(status().isNotFound());
    }

    // --------------------- PUT ---------------------

    @Test
    public void shouldUpdateStudent200() throws Exception {
        Student student = studentRepository.save(new Student(null, "Jane", "jane@example.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>()));
        StudentInDto updatedDto = new StudentInDto("Jane Updated", "jane.updated@example.com", Date.valueOf(LocalDate.now()), false, new ArrayList<>());

        mockMvc.perform(put("/student/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Updated"));
    }

    @Test
    public void shouldReturn404WhenUpdatingNonexistentStudent() throws Exception {
        StudentInDto dto = new StudentInDto("Ghost", "ghost@example.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(put("/student/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn400WhenUpdatingInvalidStudent() throws Exception {
        Student student = studentRepository.save(new Student(null, "Invalid Name", "invalid@example.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>()));
        StudentInDto invalidDto = new StudentInDto("", "invalid@example.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        mockMvc.perform(put("/student/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}
