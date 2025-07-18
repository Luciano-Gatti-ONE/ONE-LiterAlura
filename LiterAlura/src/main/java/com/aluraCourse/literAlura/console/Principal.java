package com.aluraCourse.literAlura.console;

import com.aluraCourse.literAlura.model.Data;
import com.aluraCourse.literAlura.model.entities.Book;
import com.aluraCourse.literAlura.model.DataBook;
import com.aluraCourse.literAlura.model.entities.Person;
import com.aluraCourse.literAlura.repository.BookRepository;
import com.aluraCourse.literAlura.repository.PersonRepository;
import com.aluraCourse.literAlura.util.DataConverter;
import com.aluraCourse.literAlura.client.GutenbergClient;
import com.aluraCourse.literAlura.service.AuthorService;
import com.aluraCourse.literAlura.service.BookService;
import com.aluraCourse.literAlura.service.StatisticsService;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Luciano E. Gatti Flekenstein
 */
public class Principal {

    private final BookService bookService;
    private final AuthorService authorService;
    private final StatisticsService statisticsService;
    private final GutenbergClient gutembergClient;
    private final DataConverter dataConverter;

    private final Scanner scanner = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";

    public Principal(BookService bookService,
                     AuthorService authorService,
                     StatisticsService statisticsService,
                     GutenbergClient gutembergClient,
                     DataConverter dataConverter) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.statisticsService = statisticsService;
        this.gutembergClient = gutembergClient;
        this.dataConverter = dataConverter;
    }

    public void showMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                **************************************************************************
                1 - Buscar libro 
                2 - Mostrar libros buscados
                3 - Mostrar autores de los libros buscados
                4 - Listar autores vivos en determinado año (API)
                5 - Listar autores vivos en determinado año (DB)
                6 - Cantidad de libros en ingles
                7 - Cantidad de libros en español
                8 - Listar los 10 libros más populares (API)
                9 - Listar los 10 libros más populares (DB)
                               
                0 - Salir
                **************************************************************************
                """);

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> {
                    buscarLibro();
                    esperarEnter();
                }
                case 2 -> {
                    bookService.displayAllBooks();
                    esperarEnter();
                }
                case 3 -> {
                    authorService.displayAllAuthors();
                    esperarEnter();
                }
                case 4 -> {
                    int year = leerAnio();
                    authorService.displayLivingAuthorsFromAPI(year);
                    esperarEnter();
                }
                case 5 -> {
                    int year = leerAnio();
                    authorService.displayLivingAuthorsFromDB(year);
                    esperarEnter();
                }
                case 6 -> {
                    System.out.println("Cantidad de libros en inglés: " + bookService.countBooksByLanguage("en"));
                    esperarEnter();
                }
                case 7 -> {
                    System.out.println("Cantidad de libros en español: " + bookService.countBooksByLanguage("es"));
                    esperarEnter();
                }
                case 8 -> {
                    statisticsService.showTopDownloadsFromAPI();
                    esperarEnter();
                }
                case 9 -> {
                    statisticsService.showTopDownloadsFromDB();
                    esperarEnter();
                }
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> {
                    System.out.println("Opción inválida");
                    esperarEnter();
                }
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        String bookName = scanner.nextLine();

        String json = gutembergClient.getData(URL_BASE + "?search=" + bookName.replace(" ", "+"));

        if (json == null || json.isBlank()) {
            System.out.println("Error: La respuesta de la API está vacía.");
            return;
        }

        Data datos = dataConverter.convertJsonToObject(json, Data.class);
        Optional<DataBook> dataBook = datos.results().stream()
                .filter(l -> l.title().toUpperCase().contains(bookName.toUpperCase()))
                .findFirst();

        if (dataBook.isPresent()) {
            bookService.searchAndSaveBook(dataBook.get());
            System.out.println(dataBook.get());
        } else {
            System.out.println("Libro no encontrado.");
        }

        esperarEnter();
    }

    private int leerAnio() {
        System.out.println("Ingrese el año:");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Usando 1900 por defecto.");
            return 1900;
        }
    }

    private void esperarEnter() {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}
