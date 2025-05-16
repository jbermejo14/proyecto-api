package com.svalero.proyectoapi.controller;

import com.svalero.proyectoapi.domain.Student;
import com.svalero.proyectoapi.domain.dto.StudentInDto;
import com.svalero.proyectoapi.domain.dto.StudentOutDto;
import com.svalero.proyectoapi.exception.StudentNotFoundException;
import com.svalero.proyectoapi.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping
    public ResponseEntity<List<StudentOutDto>> getAll(@RequestParam(value = "title", defaultValue = "") String title,
                                                     @RequestParam(value = "description", defaultValue = "") String description) {
        List<StudentOutDto> students = studentService.getAll(title, description);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable long studentId) throws StudentNotFoundException {
        Student student = studentService.get(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentOutDto> addStudent(@RequestBody @Valid StudentInDto student) {
        StudentOutDto newStudent = studentService.add(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> removeStudent(@PathVariable long studentId) throws StudentNotFoundException {
        studentService.remove(studentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentOutDto> modifyStudent(@PathVariable @Valid Long studentId, @RequestBody StudentInDto student) throws StudentNotFoundException {
        StudentOutDto modifiedStudent = studentService.modify(studentId, student);
        return new ResponseEntity<>(modifiedStudent, HttpStatus.OK);
    }
}