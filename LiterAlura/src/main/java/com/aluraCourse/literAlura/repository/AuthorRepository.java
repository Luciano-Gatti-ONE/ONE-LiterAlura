package com.aluraCourse.literAlura.repository;

import com.aluraCourse.literAlura.model.entities.Author;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
* Repositorio de acceso a datos para la entidad {@link Author}, que extiende
* la clase abstracta {@code Person}. Proporciona operaciones CRUD
* y consultas personalizadas utilizando Spring Data JPA.
* 
* Nota: Aunque el nombre del repositorio es PersonRepository, maneja la entidad {@code Author}.
* 
* @author Luciano E. Gatti Flekenstein
*/
public interface AuthorRepository extends JpaRepository<Author, Long> {
    /**
    * Busca un autor por su nombre, ignorando mayúsculas y minúsculas.
    *
    * @param name nombre del autor a buscar.
    * @return un {@link Optional} que contiene el autor si fue encontrado, o vacío si no existe.
    */
    Optional<Author> findByNameIgnoreCase(String name);

    /**
    * Busca todos los autores que estaban vivos en un año determinado.
    * Se consideran vivos aquellos cuya fecha de nacimiento es anterior o igual al año dado
    * y cuya fecha de fallecimiento es nula o posterior al año dado.
    *
    * @param year el año en el cual se desea verificar si el autor estaba vivo.
    * @return lista de autores vivos en el año especificado.
    */
    @Query("SELECT p FROM Person p WHERE p.birthYear <= :year AND (p.deathYear IS NULL OR p.deathYear > :year)")
    List<Author> findAliveInYear(@Param("year") int year);
}