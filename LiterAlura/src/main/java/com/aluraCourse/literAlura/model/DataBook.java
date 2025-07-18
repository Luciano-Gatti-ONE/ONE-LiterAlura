package com.aluraCourse.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
* Representa un libro recibido desde una API externa, como la de Project Gutenberg.
* Esta clase utiliza anotaciones de Jackson para mapear automáticamente los campos JSON
* a los atributos correspondientes del objeto.
*
* Campos destacados:
* - title: título del libro.
* - authors: lista de autores del libro.
* - languages: lista de lenguanjes en los que se encuantra el libro.
* - downloadCount: cantidad de veces que fue descargado.
*
* Se usa la anotación @JsonIgnoreProperties(ignoreUnknown = true) para ignorar
* cualquier campo no mapeado del JSON, permitiendo mayor flexibilidad frente a
* cambios en la API externa.
*
* @author Luciano E. Gatti Flekenstein
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
    @JsonAlias("title")String title,
    @JsonAlias("authors")List<DataPerson> authors,
    @JsonAlias("languages")List<String> languages,
    @JsonAlias("download_count")Double downloadCount
){}
