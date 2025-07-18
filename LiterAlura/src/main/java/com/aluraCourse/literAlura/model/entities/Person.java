/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Person(){}

    public Person(DataPerson dataPerson) {
        this.name = dataPerson.name();
        try{
            this.birthYear = Integer.valueOf(dataPerson.birthYear());
        } catch (DateTimeParseException e){
            this.birthYear = null;
        }
        try{
            this.deathYear = Integer.valueOf(dataPerson.deathYear());
        } catch (DateTimeParseException e){
            this.deathYear = null;
        }
    }
    
    public Long getId() {
        return Id;
    }
    
    public void setId(Long Id) {
        this.Id = Id;
    }

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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        books.forEach(e -> e.setAuthor(this));
        this.books = books;
    }
    
    @Override
    public String toString() {
        return "\n" + "Autor:" + "\n" +
                "Name='" + name + "\n" +
                "Nacimiento=" + birthYear + "\n" +
                "Fallecimiento=" + deathYear;
    }
}
