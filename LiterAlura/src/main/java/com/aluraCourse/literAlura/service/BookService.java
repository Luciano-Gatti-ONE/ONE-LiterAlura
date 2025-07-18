/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluraCourse.literAlura.service;

import com.aluraCourse.literAlura.model.DataBook;
import com.aluraCourse.literAlura.model.entities.Book;
import com.aluraCourse.literAlura.model.entities.Person;
import com.aluraCourse.literAlura.repository.BookRepository;
import com.aluraCourse.literAlura.repository.PersonRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author usuario
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public void searchAndSaveBook(DataBook dataBook) {
        String authorName = dataBook.authors().get(0).name();

        if (bookRepository.findByTitleIgnoreCaseAndAuthor_NameIgnoreCase(dataBook.title(), authorName).isPresent()) {
            System.out.println("El libro ya está registrado.");
            return;
        }

        Person author = personRepository.findByNameIgnoreCase(authorName)
                .orElseGet(() -> personRepository.save(new Person(dataBook.authors().get(0))));

        Book book = new Book(dataBook);
        book.setAuthor(author);
        bookRepository.save(book);

        System.out.println("Libro guardado correctamente.");
    }

    public void displayAllBooks() {
        bookRepository.findAll().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);
    }

    public long countBooksByLanguage(String language) {
        return bookRepository.countByLanguagesContaining(language);
    }

    public List<Book> getTopDownloadedBooks(int limit) {
        return bookRepository.findTop10ByOrderByDownloadCountDesc(); // ya está limitado a 10
    }
}