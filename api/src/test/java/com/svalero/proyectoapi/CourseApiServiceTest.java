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

        List<CourseOutDto> courseList = courseService.getAll("", "");

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
        CourseInDto courseRegistrationDto = new CourseInDto("Curso 1", "Descripción 1", Date.valueOf(LocalDate.now()), true, new ArrayList<>());
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

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testModifyCourse() throws CourseNotFoundException {
        long courseId = 31L;

        CourseInDto courseInDto = new CourseInDto(
                "Curso Modificado",
                "Descripción Modificada",
                Date.valueOf(LocalDate.now()),
                false,
                new ArrayList<>()
        );

        Course existingCourse = new Course(
                courseId,
                "Curso Original",
                "Descripción Original",
                Date.valueOf(LocalDate.now()),
                true,
                new ArrayList<>()
        );

        CourseOutDto expectedOutDto = new CourseOutDto(
                courseId,
                "Curso Modificado",
                "Descripción Modificada",
                Date.valueOf(LocalDate.now()),
                false,
                new ArrayList<>()
        );

        // Mock repository returning existing course
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));

        // Mock modelMapper to simulate updating the existingCourse with values from courseInDto
        doAnswer(invocation -> {
            CourseInDto dto = invocation.getArgument(0);
            Course course = invocation.getArgument(1);
            course.setTitle(dto.getTitle());
            course.setDescription(dto.getDescription());
            course.setStartDate(dto.getStartDate());
            return null;
        }).when(modelMapper).map(eq(courseInDto), eq(existingCourse));

        // Save returns the same course
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);

        // Mapper returns the output DTO
        when(modelMapper.map(existingCourse, CourseOutDto.class)).thenReturn(expectedOutDto);

        // Call the method
        CourseOutDto result = courseService.modify(courseId, courseInDto);

        // Assertions
        assertEquals("Curso Modificado", result.getTitle());
        assertEquals("Descripción Modificada", result.getDescription());

        // Verify interactions
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(existingCourse);
        verify(modelMapper, times(1)).map(courseInDto, existingCourse);
        verify(modelMapper, times(1)).map(existingCourse, CourseOutDto.class);
    }



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
