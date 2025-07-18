/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.aluraCourse.literAlura.repository;

import com.aluraCourse.literAlura.model.entities.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author usuario
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNameIgnoreCase(String name);
    @Query("SELECT p FROM Person p WHERE p.birthYear <= :year AND (p.deathYear IS NULL OR p.deathYear > :year)")
    List<Person> findAliveInYear(@Param("year") int year);
}
