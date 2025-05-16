package com.svalero.proyectoapi;

import com.svalero.proyectoapi.domain.Course;
import com.svalero.proyectoapi.domain.dto.CourseInDto;
import com.svalero.proyectoapi.domain.dto.CourseOutDto;
import com.svalero.proyectoapi.repository.CourseRepository;
import com.svalero.proyectoapi.exception.CourseNotFoundException;
import com.svalero.proyectoapi.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseApiServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testGetAll() {
        List<Course> mockCourseList = List.of(
                new Course(31L, "Curso 1", "Descripción 1", Date.valueOf(LocalDate.now()), true, new ArrayList<>()),
                new Course(32L, "Curso 2", "Descripción 2", Date.valueOf(LocalDate.now()), true, new ArrayList<>()),
                new Course(33L, "Curso 3", "Descripción 3", Date.valueOf(LocalDate.now()), true, new ArrayList<>())
        );

        List<CourseOutDto> mockCourseOutDtoList = List.of(
                new CourseOutDto(31L, "Curso 1", "Descripción 1", Date.valueOf(LocalDate.now()), true, new ArrayList<>()),
                new CourseOutDto(32L, "Curso 2", "Descripción 2", Date.valueOf(LocalDate.now()), true, new ArrayList<>()),
                new CourseOutDto(33L, "Curso 3", "Descripción 3", Date.valueOf(LocalDate.now()), true, new ArrayList<>())
        );

        when(courseRepository.findAll()).thenReturn(mockCourseList);
        when(modelMapper.map(mockCourseList, new TypeToken<List<CourseOutDto>>() {}.getType())).thenReturn(mockCourseOutDtoList);

        List<CourseOutDto> courseList = courseService.getAll(null, null);

        assertEquals(3, courseList.size());
        assertEquals("Curso 1", courseList.get(0).getTitle());
        verify(courseRepository, times(1)).findAll();
    }


    @Test
    public void testGetCourseById() throws CourseNotFoundException {
        Course mockCourse = new Course(31L, "Curso 1", "Descripción 1",  Date.valueOf(LocalDate.now()),true, new ArrayList<>());
        when(courseRepository.findById(31L)).thenReturn(Optional.of(mockCourse));

        Course course = courseService.get(31);
        assertEquals("Curso 1", course.getTitle());
        verify(courseRepository, times(1)).findById(31L);
    }

    @Test
    public void testGetCourseByIdNotFound() {
        when(courseRepository.findById(31L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.get(31));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testAdd() {
        CourseInDto courseRegistrationDto = new CourseInDto(31L, "Curso 1", "Descripción 1", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        Course mockCourse = new Course(31L, "Curso 1", "Descripción 1", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        CourseOutDto mockCourseOutDto = new CourseOutDto(31L, "Curso 1", "Descripción 1", Date.valueOf(LocalDate.now()), true, new ArrayList<>());

        when(modelMapper.map(courseRegistrationDto, Course.class)).thenReturn(mockCourse);
        when(modelMapper.map(mockCourse, CourseOutDto.class)).thenReturn(mockCourseOutDto);

        courseService.add(courseRegistrationDto);

        assertEquals(31L, mockCourseOutDto.getId());
        assertEquals("Curso 1", mockCourseOutDto.getTitle());
        assertEquals("Descripción 1", mockCourseOutDto.getDescription());

        verify(courseRepository, times(1)).save(mockCourse);
    }
//
//    @Test
//    public void testModifyCustomer() throws CustomerNotFoundException {
//        Customer mockCustomer = new Customer(31, "Pablo", "test@email.com", "111111111", "test", "user", Date.valueOf(LocalDate.now()));
//        CustomerInDto customerInDto = new CustomerInDto("Juan", "test@email.com", "111111111", "test", "user", Date.valueOf(LocalDate.now()));
//        when(customerRepository.findById(31L)).thenReturn(Optional.of(mockCustomer));
//
//        CustomerOutDto updatedCustomer = customerService.modify(31L, customerInDto);
//        assertEquals("Juan", updatedCustomer.getName());
//        verify(customerRepository, times(1)).save(mockCustomer);
//    }

    @Test
    public void testRemoveCourse() throws CourseNotFoundException {
        Course mockCourse = new Course(31L, "Curso 1", "Descripción 1", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
        when(courseRepository.findById(31L)).thenReturn(Optional.of(mockCourse));

        courseService.remove(31L);
        verify(courseRepository, times(1)).deleteById(31L);
    }

    @Test
    public void testRemoveCourseNotFound() {
        when(courseRepository.findById(31L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.remove(31L));
    }
}
