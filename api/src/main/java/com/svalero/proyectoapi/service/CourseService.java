package com.svalero.proyectoapi.service;

import com.svalero.proyectoapi.domain.Student;
import com.svalero.proyectoapi.domain.dto.CourseInDto;
import com.svalero.proyectoapi.exception.CourseNotFoundException;
import com.svalero.proyectoapi.repository.CourseRepository;
import com.svalero.proyectoapi.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svalero.proyectoapi.domain.dto.CourseOutDto;
import com.svalero.proyectoapi.domain.Course;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StudentRepository studentRepository;

    public List<CourseOutDto> getAll(String title, String description) {
        List<Course> courseList;

        if (title.isEmpty() && description.isEmpty()) {
            courseList = courseRepository.findAll();
        } else if (title.isEmpty()) {
            courseList = courseRepository.findByTitle(title);
        } else if (description.isEmpty()) {
            courseList = courseRepository.findByDescription(description);
        } else {
            courseList = courseRepository.findByTitleAndDescription(title, description);
        }

        return modelMapper.map(courseList, new TypeToken<List<CourseOutDto>>() {
        }.getType());
    }

    public Course get(long id) throws CourseNotFoundException {
        return courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
    }

    public void enrollStudent(long courseId, long studentId) throws CourseNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        if (!course.getStudents().contains(student)) {
            course.getStudents().add(student);
            courseRepository.save(course);
        }
    }

    public CourseOutDto add(CourseInDto courseInDto) {
        Course course = modelMapper.map(courseInDto, Course.class);
        Course newCourse = courseRepository.save(course);

        return modelMapper.map(newCourse, CourseOutDto.class);
    }

    public void remove(long id) throws CourseNotFoundException {
        courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        courseRepository.deleteById(id);
    }

    public CourseOutDto modify(long courseId, CourseInDto courseInDto) throws CourseNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);

        modelMapper.map(courseInDto, course);
        courseRepository.save(course);

        return modelMapper.map(course, CourseOutDto.class);
    }
}
