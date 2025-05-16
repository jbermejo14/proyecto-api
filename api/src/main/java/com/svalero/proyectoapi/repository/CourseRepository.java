package com.svalero.proyectoapi.repository;

import com.svalero.proyectoapi.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>, JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    List<Course> findAll();
    List<Course> findByTitle(String name);
    List<Course> findByDescription(String description);
    List<Course> findByTitleAndDescription(String name, String description);
}
