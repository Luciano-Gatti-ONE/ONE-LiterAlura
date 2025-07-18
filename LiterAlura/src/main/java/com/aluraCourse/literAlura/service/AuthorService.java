package com.aluraCourse.literAlura.service;

import com.aluraCourse.literAlura.client.GutendexClient;
import com.aluraCourse.literAlura.model.Data;
import com.aluraCourse.literAlura.model.entities.Author;
import com.aluraCourse.literAlura.util.DataConverter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.aluraCourse.literAlura.repository.AuthorRepository;

/**
 * Servicio encargado de gestionar operaciones relacionadas con autores,
 * incluyendo la obtención y visualización de autores desde la base de datos
 * y desde la API de Gutendex.
 * 
 * Utiliza un repositorio JPA para acceder a la base de datos local
 * y un cliente HTTP para consumir datos externos.
 * 
 * @author Luciano E. Gatti Flekenstein
 */
@Service
public class AuthorService {

    private final AuthorRepository personRepository;
    private final GutendexClient gutembergClient;
    private final DataConverter dataConverter;
    private static final String URL_BASE = "https://gutendex.com/books/";

    /**
    * Constructor que inyecta las dependencias necesarias para el servicio.
    *
    * @param personRepository repositorio para acceder a los autores persistidos.
    * @param gutembergClient cliente HTTP para consumir la API de Gutendex.
    * @param dataConverter utilidad para convertir JSON en objetos Java.
    */
    public AuthorService(AuthorRepository personRepository,
                         GutendexClient gutembergClient,
                         DataConverter dataConverter) {
        this.personRepository = personRepository;
        this.gutembergClient = gutembergClient;
        this.dataConverter = dataConverter;
    }

    /**
    * Muestra por consola todos los autores almacenados en la base de datos,
    * ordenados alfabéticamente por nombre.
    */
    public void displayAllAuthors() {
        personRepository.findAll().stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    /**
    * Muestra por consola los autores almacenados en la base de datos
    * que estaban vivos en un año determinado.
    *
    * @param year año para filtrar autores vivos.
    */
    public void displayLivingAuthorsFromDB(int year) {
        var authors = personRepository.findAliveInYear(year);
        authors.forEach(System.out::println);
    }

    /**
    * Muestra por consola los autores vivos en un año determinado,
    * obtenidos desde la API de Gutendex.
    * 
    * Solo se muestran nombres únicos y se ignoran aquellos
    * que no contienen años válidos de nacimiento o muerte.
    *
    * @param year año para filtrar autores vivos.
    */
    public void displayLivingAuthorsFromAPI(int year) {
        var json = gutembergClient.obtenerDatos(URL_BASE + "?author_year_end=" + (year + 1));

        if (json == null || json.isBlank()) {
            System.out.println("Error: La respuesta de la API está vacía.");
            return;
        }

        Data datos = dataConverter.convertJsonToObject(json, Data.class);
        Set<String> uniqueNames = new HashSet<>();

        datos.results().stream()
                .flatMap(book -> book.authors().stream())
                .filter(author -> author.birthYear() != null && author.deathYear() != null)
                .filter(author -> author.birthYear() <= year && author.deathYear() > year)
                .filter(author -> uniqueNames.add(author.name())) // evita duplicados
                .forEach(System.out::println);
    }
}