package com.svalero.proyectoapi.repository;

import com.svalero.proyectoapi.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long>, JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    List<Student> findAll();
    List<Student> findByName(String name);
    List<Student> findByEmail(String email);
    List<Student> findByNameAndEmail(String name, String email);
}
