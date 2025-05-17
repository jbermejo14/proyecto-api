package com.svalero.proyectoapi.controller;

import com.svalero.proyectoapi.domain.Student;
import com.svalero.proyectoapi.domain.dto.CourseOutDto;
import com.svalero.proyectoapi.domain.dto.ErrorResponse;
import com.svalero.proyectoapi.domain.dto.StudentInDto;
import com.svalero.proyectoapi.domain.dto.StudentOutDto;
import com.svalero.proyectoapi.exception.CourseNotFoundException;
import com.svalero.proyectoapi.exception.StudentNotFoundException;
import com.svalero.proyectoapi.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<StudentOutDto> getStudent(@PathVariable long studentId) throws StudentNotFoundException {
        Student student = studentService.get(studentId);
        StudentOutDto dto = studentService.convertToOutDto(student);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<CourseOutDto>> getCoursesByStudent(@PathVariable long studentId) {
        List<CourseOutDto> courses = studentService.getCoursesByStudentId(studentId);
        return ResponseEntity.ok(courses);
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
    public ResponseEntity<StudentOutDto> modifyStudent(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentInDto student) throws StudentNotFoundException {

        StudentOutDto modifiedStudent = studentService.modify(studentId, student);
        return new ResponseEntity<>(modifiedStudent, HttpStatus.OK);
    }


    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException exception) {
        ErrorResponse error = ErrorResponse.generalError(404, exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        logger.error(exception.getMessage(), exception);

        return new ResponseEntity<>(ErrorResponse.validationError(errors), HttpStatus.BAD_REQUEST);
    }

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(StudentNotFoundException.class)
        public ResponseEntity<String> handleStudentNotFound(StudentNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }

}