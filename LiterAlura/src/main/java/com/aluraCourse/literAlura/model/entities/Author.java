package com.aluraCourse.literAlura.model.entities;

import com.aluraCourse.literAlura.model.DataPerson;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
* Entidad que representa un autor almacenado en la base de datos.
* Contiene información como el nombre, la fecha de nacimiento y la fecha de muerte.
*
* Se encuentra mapeada a la tabla "person" mediante JPA.
*
* @author Luciano E. Gatti Flekenstein
*/
@Entity
@Table(name = "author")
public class Author extends Person {
    // Atributos de la entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;
    
    //Constructor por defecto requerido por JPA
    public Author() {}

    /**
    * Constructor que inicializa un autor a partir de un objeto {@link DataPerson}.
    * Extrae el nombre, año de nacimiento y año de fallecimiento.
    * Si alguno de los valores no puede ser convertido a número, se asigna null.
    *
    * @param dataPerson objeto con los datos del autor provenientes de la API.
    */
    public Author(DataPerson dataPerson) {
        super(
            dataPerson.name(),
            dataPerson.birthYear(),
            dataPerson.deathYear()
        );
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        books.forEach(b -> b.setAuthor(this));
        this.books = books;
    }

    @Override
    public String toString() {
        return "\nAutor:" +
                "\nNombre: " + getName() +
                "\nNacimiento: " + getBirthYear() +
                "\nFallecimiento: " + getDeathYear();
    }
}