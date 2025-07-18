/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author usuario
 */
@Service
public class StatisticsService {

    private final GutenbergClient gutembergClient;
    private final DataConverter dataConverter;
    private final BookRepository bookRepository;
    private static final String URL_BASE = "https://gutendex.com/books/";

    public StatisticsService(GutenbergClient gutembergClient,
                             DataConverter dataConverter,
                             BookRepository bookRepository) {
        this.gutembergClient = gutembergClient;
        this.dataConverter = dataConverter;
        this.bookRepository = bookRepository;
    }

    public void showTopDownloadsFromAPI() {
        var json = gutembergClient.getData(URL_BASE + "?popular");

        if (json == null || json.isBlank()) {
            System.out.println("Error: La respuesta de la API está vacía.");
            return;
        }

        Data datos = dataConverter.convertJsonToObject(json, Data.class);

        var topBooks = datos.results().stream()
                .sorted(Comparator.comparing(DataBook::downloadCount).reversed())
                .limit(10)
                .toList();

        topBooks.forEach(System.out::println);
        showStatistics(topBooks.stream().mapToDouble(DataBook::downloadCount));

    }

    public void showTopDownloadsFromDB() {
        var books = bookRepository.findTop10ByOrderByDownloadCountDesc();

        books.forEach(System.out::println);
        showStatistics(books.stream().mapToDouble(Book::getDownloadCount));
    }

    private void showStatistics(DoubleStream downloads) {
        DoubleSummaryStatistics stats = downloads.summaryStatistics();
        System.out.println("Suman un total de: " + stats.getSum() + " descargas");
        System.out.println("El libro con más descargas tiene: " + stats.getMax());
        System.out.println("El libro con menos descargas tiene: " + stats.getMin());
    }
}
