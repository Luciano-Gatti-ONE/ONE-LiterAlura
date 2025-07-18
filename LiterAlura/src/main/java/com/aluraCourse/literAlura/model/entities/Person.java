package com.aluraCourse.literAlura.model.entities;

import jakarta.persistence.MappedSuperclass;

/**
* Clase base abstracta que representa a una persona con atributos comunes
* como nombre, año de nacimiento y año de fallecimiento.
*
* Esta clase está anotada con {@link jakarta.persistence.MappedSuperclass}
* para que sus atributos sean heredados y mapeados en las entidades hijas,
* sin crear una tabla propia en la base de datos.
* 
* Sirve como superclase para entidades como {@link Author}.
* 
* @author Luciano E. Gatti Flekenstein
*/

@MappedSuperclass
public abstract class Person {
    private String name;
    private Integer birthYear;
    private Integer deathYear;

    public Person() {}

    public Person(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }
}
