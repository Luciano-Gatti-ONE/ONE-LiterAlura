/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluraCourse.literAlura.service;

import com.aluraCourse.literAlura.client.GutenbergClient;
import com.aluraCourse.literAlura.model.Data;
import com.aluraCourse.literAlura.model.entities.Person;
import com.aluraCourse.literAlura.repository.PersonRepository;
import com.aluraCourse.literAlura.util.DataConverter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author usuario
 */
@Service
public class AuthorService {

    private final PersonRepository personRepository;
    private final GutenbergClient gutembergClient;
    private final DataConverter dataConverter;
    private static final String URL_BASE = "https://gutendex.com/books/";

    public AuthorService(PersonRepository personRepository,
                         GutenbergClient gutembergClient,
                         DataConverter dataConverter) {
        this.personRepository = personRepository;
        this.gutembergClient = gutembergClient;
        this.dataConverter = dataConverter;
    }

    public void displayAllAuthors() {
        personRepository.findAll().stream()
                .sorted(Comparator.comparing(Person::getName))
                .forEach(System.out::println);
    }

    public void displayLivingAuthorsFromDB(int year) {
        var authors = personRepository.findAliveInYear(year);
        authors.forEach(System.out::println);
    }

    public void displayLivingAuthorsFromAPI(int year) {
        var json = gutembergClient.getData(URL_BASE + "?author_year_end=" + (year + 1));

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
                .filter(author -> uniqueNames.add(author.name()))
                .forEach(System.out::println);
    }
}