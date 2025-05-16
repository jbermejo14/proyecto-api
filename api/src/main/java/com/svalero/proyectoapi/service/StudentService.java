package com.svalero.proyectoapi.service;

import com.svalero.proyectoapi.domain.Student;
import com.svalero.proyectoapi.domain.dto.CourseInDto;
import com.svalero.proyectoapi.domain.dto.StudentInDto;
import com.svalero.proyectoapi.domain.dto.StudentOutDto;
import com.svalero.proyectoapi.exception.StudentNotFoundException;
import com.svalero.proyectoapi.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;

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

    public StudentOutDto add(StudentInDto studentInDto) {
        Student student = modelMapper.map(studentInDto, Student.class);
        Student newStudent = studentRepository.save(student);

        return modelMapper.map(newStudent, StudentOutDto.class);
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