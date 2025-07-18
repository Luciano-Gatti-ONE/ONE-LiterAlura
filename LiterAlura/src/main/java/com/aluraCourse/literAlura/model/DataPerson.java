/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluraCourse.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Representa una persona relacionada con un libro, ya sea como autor o traductor,
 * según los datos obtenidos desde una API externa como Project Gutenberg.
 *
 * Esta clase utiliza anotaciones de Jackson para mapear automáticamente los campos JSON
 * a los atributos correspondientes del objeto.
 *
 * Campos:
 * - birthYear: año de nacimiento de la persona.
 * - deathYear: año de fallecimiento de la persona (puede ser null si sigue viva o se desconoce).
 * - name: nombre completo de la persona.
 *
 * Esta clase puede ser utilizada tanto para autores como traductores en otros modelos,
 * como la clase Book.
 *
 * @author Luciano E. Gatti Flekenstein
 */

public record DataPerson(
    @JsonAlias("birth_year") Integer birthYear,
    @JsonAlias("death_year")Integer deathYear,
    @JsonAlias("name")String name
){}
