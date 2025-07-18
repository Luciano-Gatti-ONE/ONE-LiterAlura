package com.aluraCourse.literAlura.service;

import com.aluraCourse.literAlura.client.GutenbergClient;
import com.aluraCourse.literAlura.model.Data;
import com.aluraCourse.literAlura.model.DataBook;
import com.aluraCourse.literAlura.model.entities.Book;
import com.aluraCourse.literAlura.repository.BookRepository;
import com.aluraCourse.literAlura.util.DataConverter;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.stream.DoubleStream;
import org.springframework.stereotype.Service;

/**
* Servicio encargado de consultar, procesar y mostrar estadísticas
* relacionadas con libros, tanto desde una API externa (Gutendex) 
* como desde la base de datos local. 
*
* Permite obtener los libros más descargados, ordenarlos, imprimirlos 
* por consola y generar estadísticas como el total de descargas, el máximo 
* y el mínimo. Es útil para obtener una visión general de los libros más 
* populares en el sistema.
*
* Este servicio actúa como intermediario entre la API externa, el conversor 
* de datos JSON y el repositorio local, manteniendo desacopladas las responsabilidades.
* 
* @author Luciano E. Gatti Flekenstein
*/
@Service
public class StatisticsService {

    private final GutenbergClient gutembergClient;
    private final DataConverter dataConverter;
    private final BookRepository bookRepository;

    // URL base de la API de Gutendex, que permite obtener libros populares
    private static final String URL_BASE = "https://gutendex.com/books/";

    /**
    * Constructor con inyección de dependencias necesarias para operar:
    * cliente HTTP, convertidor de datos JSON y repositorio de libros.
    */
    public StatisticsService(GutenbergClient gutembergClient,
                             DataConverter dataConverter,
                             BookRepository bookRepository) {
        this.gutembergClient = gutembergClient;
        this.dataConverter = dataConverter;
        this.bookRepository = bookRepository;
    }

    /**
    * Muestra por consola los 10 libros más descargados obtenidos directamente desde la API.
    * También imprime estadísticas sobre sus descargas.
    */
    public void showTopDownloadsFromAPI() {
        // Consulta los datos desde la API
        var json = gutembergClient.obtenerDatos(URL_BASE + "?popular");

        // Verifica si la respuesta es válida
        if (json == null || json.isBlank()) {
            System.out.println("Error: La respuesta de la API está vacía.");
            return;
        }

        // Convierte el JSON a un objeto Data
        Data datos = dataConverter.convertJsonToObject(json, Data.class);

        // Ordena los libros por cantidad de descargas de mayor a menor y toma los primeros 10
        var topBooks = datos.results().stream()
                .sorted(Comparator.comparing(DataBook::downloadCount).reversed())
                .limit(10)
                .toList();

        // Muestra los libros
        topBooks.forEach(System.out::println);

        // Muestra estadísticas de descargas
        showStatistics(topBooks.stream().mapToDouble(DataBook::downloadCount));
    }

    /**
    * Muestra por consola los 10 libros más descargados que ya están almacenados en la base de datos.
    * También imprime estadísticas de sus descargas.
    */
    public void showTopDownloadsFromDB() {
        var books = bookRepository.findTop10ByOrderByDownloadCountDesc();

        books.forEach(System.out::println);
        showStatistics(books.stream().mapToDouble(Book::getDownloadCount));
    }

    /**
    * Imprime estadísticas básicas (suma, máximo y mínimo) de una secuencia de descargas.
    *
    * @param downloads secuencia de valores de descargas para analizar
    */
    private void showStatistics(DoubleStream downloads) {
        DoubleSummaryStatistics stats = downloads.summaryStatistics();
        System.out.println("Suman un total de: " + stats.getSum() + " descargas");
        System.out.println("El libro con más descargas tiene: " + stats.getMax());
        System.out.println("El libro con menos descargas tiene: " + stats.getMin());
    }
}