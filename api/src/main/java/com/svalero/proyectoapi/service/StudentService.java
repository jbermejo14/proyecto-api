package com.svalero.proyectoapi.service;

import com.svalero.proyectoapi.domain.Course;
import com.svalero.proyectoapi.domain.Student;
import com.svalero.proyectoapi.domain.dto.CourseInDto;
import com.svalero.proyectoapi.domain.dto.CourseOutDto;
import com.svalero.proyectoapi.domain.dto.StudentInDto;
import com.svalero.proyectoapi.domain.dto.StudentOutDto;
import com.svalero.proyectoapi.exception.StudentNotFoundException;
import com.svalero.proyectoapi.repository.CourseRepository;
import com.svalero.proyectoapi.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CourseRepository courseRepository;

    public List<StudentOutDto> getAll(String name, String email) {
        List<Student> studentList;

        if (name.isEmpty() && email.isEmpty()) {
            studentList = studentRepository.findAll();
        } else if (name.isEmpty()) {
            studentList = studentRepository.findByName(name);
        } else if (email.isEmpty()) {
            studentList = studentRepository.findByEmail(email);
        } else {
            studentList = studentRepository.findByNameAndEmail(name, email);
        }

        return modelMapper.map(studentList, new TypeToken<List<StudentOutDto>>() {
        }.getType());
    }

    public Student get(long id) throws StudentNotFoundException {
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public List<CourseOutDto> getCoursesByStudentId(long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        List<Course> courses = student.getCourses();
        return modelMapper.map(courses, new TypeToken<List<CourseOutDto>>() {}.getType());
    }

    public StudentOutDto convertToOutDto(Student student) {
        StudentOutDto dto = modelMapper.map(student, StudentOutDto.class);
        List<Long> courseIds = student.getCourses().stream()
                .map(Course::getId)
                .collect(Collectors.toList());
        dto.setCourseIds(courseIds);
        return dto;
    }

    public Student convertDtoToEntity(StudentInDto dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setRegistryDate(dto.getRegistryDate());
        student.setVerified(dto.getVerified());

        if (dto.getCourseIds() != null && !dto.getCourseIds().isEmpty()) {
            List<Course> courses = courseRepository.findAllById(dto.getCourseIds());
            student.setCourses(courses);
        }

        return student;
    }

    public StudentOutDto add(StudentInDto studentInDto) {
        Student student = modelMapper.map(studentInDto, Student.class);

        if (studentInDto.getCourseIds() != null && !studentInDto.getCourseIds().isEmpty()) {
            List<Course> courses = courseRepository.findAllById(studentInDto.getCourseIds());
            student.setCourses(courses);
        } else {
            student.setCourses(new ArrayList<>());
        }

        Student newStudent = studentRepository.save(student);
        return convertToOutDto(newStudent);
    }


    public Student addStudent(StudentInDto dto) {
        Student student = convertDtoToEntity(dto);
        return studentRepository.save(student);
    }

    public void remove(long id) throws StudentNotFoundException {
        studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.deleteById(id);
    }

    public StudentOutDto modify(long studentId, StudentInDto studentInDto) throws StudentNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        modelMapper.map(studentInDto, student);
        studentRepository.save(student);

        return modelMapper.map(student, StudentOutDto.class);
    }
}