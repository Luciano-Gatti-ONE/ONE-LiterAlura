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
 * - id: identificador único del libro.
 * - title: título del libro.
 * - summaries: resúmenes opcionales del contenido.
 * - authors: lista de autores del libro.
 * - traslator: lista de traductores (¡ojo con el typo, debería ser "translators").
 * - copyright: indica si el libro está protegido por derechos de autor.
 * - downloadCount: cantidad de veces que fue descargado.
 * - format: distintos formatos disponibles para el libro (epub, html, imagen, etc.).
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
