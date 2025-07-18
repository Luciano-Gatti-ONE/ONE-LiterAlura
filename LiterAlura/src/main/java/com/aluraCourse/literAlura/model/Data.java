/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.aluraCourse.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Representa el contenedor principal de la respuesta JSON recibida desde la API externa,
 * como la de Project Gutenberg. Esta clase encapsula una lista de libros bajo la clave "results".
 *
 * Uso típico: deserializar directamente la respuesta completa de una búsqueda o consulta
 * que devuelve múltiples libros.
 *
 * Campos:
 * - results: lista de libros (`Book`) obtenidos en la respuesta de la API.
 *
 * Se utiliza @JsonIgnoreProperties(ignoreUnknown = true) para ignorar cualquier otra propiedad
 * del JSON que no esté explícitamente mapeada, brindando flexibilidad frente a cambios en la API.
 *
 * @author usuario
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(
    @JsonAlias("results") List<DataBook> results
){}
