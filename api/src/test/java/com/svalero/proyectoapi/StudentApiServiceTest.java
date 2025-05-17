package com.svalero.proyectoapi;

import com.svalero.proyectoapi.domain.Student;
import com.svalero.proyectoapi.domain.dto.StudentInDto;
import com.svalero.proyectoapi.domain.dto.StudentOutDto;
import com.svalero.proyectoapi.repository.StudentRepository;
import com.svalero.proyectoapi.exception.StudentNotFoundException;
import com.svalero.proyectoapi.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentApiServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testGetAll() {
        List<Student> mockStudentList = List.of(
                new Student(31L, "Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>()),
                new Student(32L, "Alumno 2", "alumno2@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>()),
                new Student(33L, "Alumno 3", "alumno3@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>())
        );

        List<StudentOutDto> mockStudentOutDtoList = List.of(
                new StudentOutDto(31L, "Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>()),
                new StudentOutDto(32L, "Alumno 2", "alumno2@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>()),
                new StudentOutDto(33L, "Alumno 3", "alumno3@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>())
        );

        when(studentRepository.findAll()).thenReturn(mockStudentList);
        when(modelMapper.map(mockStudentList, new TypeToken<List<StudentOutDto>>() {}.getType())).thenReturn(mockStudentOutDtoList);

        List<StudentOutDto> studentList = studentService.getAll("", "");

        assertEquals(3, studentList.size());
        assertEquals("Alumno 1", studentList.get(0).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetStudentById() throws StudentNotFoundException {
        Student mockStudent = new Student(31L, "Alumno 1", "alumno1@mail.com",  Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        when(studentRepository.findById(31L)).thenReturn(Optional.of(mockStudent));

        Student student = studentService.get(31);
        assertEquals("Alumno 1", student.getName());
        verify(studentRepository, times(1)).findById(31L);
    }

    @Test
    public void testGetStudentByIdNotFound() {
        when(studentRepository.findById(31L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.get(31));
    }


    StudentInDto studentInDto = new StudentInDto("Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
    Student mockStudent = new Student(31L, "Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
    StudentOutDto mockStudentOutDto = new StudentOutDto(31L, "Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    public void testAdd() {
        StudentInDto studentInDto = new StudentInDto("Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        Student mockStudent = new Student(31L, "Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        StudentOutDto mockStudentOutDto = new StudentOutDto(31L, "Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        when(modelMapper.map(studentInDto, Student.class)).thenReturn(mockStudent);
        when(studentRepository.save(mockStudent)).thenReturn(mockStudent);
        when(modelMapper.map(mockStudent, StudentOutDto.class)).thenReturn(mockStudentOutDto);

        StudentOutDto result = studentService.add(studentInDto);

        assertEquals(31L, result.getId());
        assertEquals("Alumno 1", result.getName());
        verify(studentRepository, times(1)).save(mockStudent);
    }


    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testModifyStudent() throws StudentNotFoundException {
        long studentId = 31L;

        StudentInDto studentInDto = new StudentInDto(
                "Alumno Modificado",
                "modificado@mail.com",
                Date.valueOf(LocalDate.now()),
                false,
                new ArrayList<>()
        );

        Student existingStudent = new Student(
                studentId,
                "Alumno Original",
                "original@mail.com",
                Date.valueOf(LocalDate.now()),
                true,
                new ArrayList<>()
        );

        StudentOutDto expectedOutDto = new StudentOutDto(
                studentId,
                "Alumno Modificado",
                "modificado@mail.com",
                Date.valueOf(LocalDate.now()),
                false,
                new ArrayList<>()
        );

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        doAnswer(invocation -> {
            StudentInDto dto = invocation.getArgument(0);
            Student student = invocation.getArgument(1);
            student.setName(dto.getName());
            student.setEmail(dto.getEmail());
            student.setRegistryDate(dto.getRegistryDate());
            student.setVerified(dto.getVerified());
            return null;
        }).when(modelMapper).map(eq(studentInDto), eq(existingStudent));

        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);
        when(modelMapper.map(existingStudent, StudentOutDto.class)).thenReturn(expectedOutDto);

        StudentOutDto result = studentService.modify(studentId, studentInDto);

        assertEquals("Alumno Modificado", result.getName());
        assertEquals("modificado@mail.com", result.getEmail());

        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(existingStudent);
        verify(modelMapper, times(1)).map(studentInDto, existingStudent);
        verify(modelMapper, times(1)).map(existingStudent, StudentOutDto.class);
    }

    @Test
    public void testRemoveStudent() throws StudentNotFoundException {
        Student mockStudent = new Student(31L, "Alumno 1", "alumno1@mail.com", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        when(studentRepository.findById(31L)).thenReturn(Optional.of(mockStudent));

        studentService.remove(31L);
        verify(studentRepository, times(1)).deleteById(31L);
    }

    @Test
    public void testRemoveStudentNotFound() {
        when(studentRepository.findById(31L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.remove(31L));
    }
}
