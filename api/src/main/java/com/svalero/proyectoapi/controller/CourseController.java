package com.svalero.proyectoapi.controller;

import com.svalero.proyectoapi.domain.Course;
import com.svalero.proyectoapi.domain.dto.CourseInDto;
import com.svalero.proyectoapi.domain.dto.CourseOutDto;
import com.svalero.proyectoapi.exception.CourseNotFoundException;
import com.svalero.proyectoapi.service.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @GetMapping
    public ResponseEntity<List<CourseOutDto>> getAll(@RequestParam(value = "title", defaultValue = "") String title,
                                                     @RequestParam(value = "description", defaultValue = "") String description)  {
        List<CourseOutDto> courses = courseService.getAll(title, description);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable long courseId)  throws CourseNotFoundException {
        Course course = courseService.get(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseOutDto> addCourse(@RequestBody @Valid CourseInDto course) {
        CourseOutDto newCourse = courseService.add(course);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> removeCourse(@PathVariable long courseId) throws CourseNotFoundException{
        courseService.remove(courseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseOutDto> modifyCourse(@PathVariable @Valid Long courseId, @RequestBody CourseInDto course) throws CourseNotFoundException {
        CourseOutDto modifiedCourse = courseService.modify(courseId, course);
        return new ResponseEntity<>(modifiedCourse, HttpStatus.OK);
    }
}
