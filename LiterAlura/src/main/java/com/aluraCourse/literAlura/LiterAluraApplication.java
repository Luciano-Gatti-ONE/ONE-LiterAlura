package com.aluraCourse.literAlura;

import com.aluraCourse.literAlura.client.GutendexClient;
import com.aluraCourse.literAlura.console.Principal;
import com.aluraCourse.literAlura.service.AuthorService;
import com.aluraCourse.literAlura.service.BookService;
import com.aluraCourse.literAlura.service.StatisticsService;
import com.aluraCourse.literAlura.util.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    private final BookService bookService;
    private final AuthorService authorService;
    private final StatisticsService statisticsService;
    private final GutendexClient gutembergClient;
    private final DataConverter dataConverter;

    public LiterAluraApplication(BookService bookService,
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

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        new Principal(bookService, authorService, statisticsService, gutembergClient, dataConverter).mostrarMenu();
    }
}