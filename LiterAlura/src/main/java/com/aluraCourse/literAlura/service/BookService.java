package com.aluraCourse.literAlura.service;

import com.aluraCourse.literAlura.model.DataBook;
import com.aluraCourse.literAlura.model.entities.Book;
import com.aluraCourse.literAlura.model.entities.Author;
import com.aluraCourse.literAlura.repository.BookRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import com.aluraCourse.literAlura.repository.AuthorRepository;

/**
* Servicio encargado de manejar la lógica relacionada con los libros.
* Realiza operaciones como búsqueda, almacenamiento, conteo y visualización de libros,
* así como el manejo de sus autores.
*
* @author Luciano E. Gatti Flekenstein
*/
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository personRepository;

    /**
    * Constructor con inyección de dependencias.
    *
    * @param bookRepository    Repositorio para operaciones con libros.
    * @param personRepository  Repositorio para operaciones con autores.
    */
    public BookService(BookRepository bookRepository, AuthorRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    /**
    * Busca si un libro ya está registrado y, si no lo está, lo guarda junto con su autor.
    *
    * @param dataBook Objeto que contiene los datos del libro obtenidos desde una fuente externa.
    */
    public void searchAndSaveBook(DataBook dataBook) {
        // Validar que el libro tenga al menos un autor
        if (dataBook.authors() == null || dataBook.authors().isEmpty()) {
            System.out.println("El libro no tiene autores y no será registrado.");
            return;
        }
        
        // Se obtiene el primer autor de la lista
        String authorName = dataBook.authors().get(0).name();

        // Se evita registrar libros duplicados
        if (bookRepository.findByTitleIgnoreCaseAndAuthor_NameIgnoreCase(dataBook.title(), authorName).isPresent()) {
            System.out.println("El libro ya está registrado.");
            return;
        }
        
        // Busca el autor por nombre, o lo crea si no existe
        Author author = personRepository.findByNameIgnoreCase(authorName)
                .orElseGet(() -> personRepository.save(new Author(dataBook.authors().get(0))));

        // Se construye y guarda el libro con el autor asignado
        Book book = new Book(dataBook);
        book.setAuthor(author);
        bookRepository.save(book);

        System.out.println("Libro guardado correctamente.");
    }

    /**
    * Muestra todos los libros registrados, ordenados alfabéticamente por título.
    */
    public void displayAllBooks() {
        bookRepository.findAll().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);
    }

    /**
    * Cuenta cuántos libros están disponibles en un determinado idioma.
    *
    * @param language Idioma a verificar (por ejemplo, "en", "es").
    * @return Número total de libros en el idioma especificado.
    */
    public long countBooksByLanguage(String language) {
        return bookRepository.countByLanguagesContaining(language);
    }

    /**
    * Devuelve los libros con mayor cantidad de descargas.
    * Nota: actualmente limitado a los 10 primeros.
    *
    * @return Lista de libros ordenados por número de descargas descendente.
    */
    public List<Book> getTopDownloadedBooks() {
        return bookRepository.findTop10ByOrderByDownloadCountDesc(); //
    }
}