package com.aluraCourse.literAlura.console;

import com.aluraCourse.literAlura.model.Data;
import com.aluraCourse.literAlura.model.DataBook;
import com.aluraCourse.literAlura.util.DataConverter;
import com.aluraCourse.literAlura.client.GutendexClient;
import com.aluraCourse.literAlura.service.AuthorService;
import com.aluraCourse.literAlura.service.BookService;
import com.aluraCourse.literAlura.service.StatisticsService;
import java.util.Optional;
import java.util.Scanner;

/**
* Clase principal que gestiona la interacción con el usuario a través de un menú en consola.
* Permite buscar libros, listar libros y autores, obtener estadísticas y consultar información
* desde una API o base de datos.
*
* Utiliza servicios como {@link BookService}, {@link AuthorService}, {@link StatisticsService},
* {@link GutendexClient} y {@link DataConverter} para realizar las operaciones.
*
* @author Luciano E. Gatti Flekenstein
*/

public class Principal {

    private final BookService bookService;
    private final AuthorService authorService;
    private final StatisticsService statisticsService;
    private final GutendexClient gutembergClient;
    private final DataConverter dataConverter;

    private final Scanner scanner = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";

    public Principal(BookService bookService,
                     AuthorService authorService,
                     StatisticsService statisticsService,
                     GutendexClient gutembergClient,
                     DataConverter dataConverter) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.statisticsService = statisticsService;
        this.gutembergClient = gutembergClient;
        this.dataConverter = dataConverter;
    }
    
    /**
    * Muestra el menú principal en consola y gestiona la selección de opciones por parte del usuario.
    * El menú permite buscar libros, mostrar información, realizar estadísticas y consultar datos
    * desde la API o la base de datos.
    */
    public void mostrarMenu() {
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

    /**
    * Permite al usuario buscar un libro ingresando su nombre.
    * Consulta la API de Gutenberg, convierte la respuesta en un objeto Java,
    * filtra el libro que coincida con el título ingresado y lo guarda utilizando el BookService.
    */
    private void buscarLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        String bookName = scanner.nextLine();

        String json = gutembergClient.obtenerDatos(URL_BASE + "?search=" + bookName.replace(" ", "+"));

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

    /**
    * Solicita al usuario que ingrese un año y devuelve el valor ingresado.
    * Si el valor ingresado no es un número válido, retorna 1900 por defecto.
    *
    * @return el año ingresado por el usuario, o 1900 si hay un error de formato.
    */
    private int leerAnio() {
        System.out.println("Ingrese el año:");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Usando 1900 por defecto.");
            return 1900;
        }
    }

    /**
    * Pausa la ejecución hasta que el usuario presione la tecla ENTER.
    * Se utiliza para mejorar la interacción en consola entre una acción y el siguiente paso.
    */
    private void esperarEnter() {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}
