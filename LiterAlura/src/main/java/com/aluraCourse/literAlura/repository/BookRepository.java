package com.aluraCourse.literAlura.repository;

import com.aluraCourse.literAlura.model.entities.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* Repositorio de acceso a datos para la entidad {@link Book}.
* 
* Extiende {@link JpaRepository} para proporcionar operaciones CRUD
* y consultas personalizadas sobre la entidad Book.
* 
* Los métodos definidos aquí utilizan consultas derivadas (Derived Queries)
* proporcionadas por Spring Data JPA.
* 
* @author Luciano E. Gatti Flekenstein
*/
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
    * Busca un libro por su título (ignorando mayúsculas y minúsculas)
    * y por el nombre del autor (también ignorando mayúsculas y minúsculas).
    *
    * @param title título del libro a buscar.
    * @param authorName nombre del autor del libro.
    * @return un {@link Optional} que contiene el libro encontrado, o vacío si no se encuentra.
    */
    Optional<Book> findByTitleIgnoreCaseAndAuthor_NameIgnoreCase(String title, String authorName);

    /**
     * Cuenta la cantidad de libros que están disponibles en un determinado idioma.
     *
     * @param language el idioma a buscar (por ejemplo: "en" para inglés).
     * @return cantidad de libros que contienen ese idioma.
     */
    long countByLanguagesContaining(String language);

    /**
     * Obtiene una lista con los 10 libros más descargados, ordenados de forma descendente 
     * por la cantidad de descargas.
     *
     * @return lista de los 10 libros con mayor cantidad de descargas.
     */
    List<Book> findTop10ByOrderByDownloadCountDesc();
}
