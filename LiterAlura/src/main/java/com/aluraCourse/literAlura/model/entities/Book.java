/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluraCourse.literAlura.model.entities;

import com.aluraCourse.literAlura.model.DataBook;
import com.aluraCourse.literAlura.model.entities.Person;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author usuario
 */

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    
    @ElementCollection
    private List<String> languages;
    
    private Double downloadCount;
    
    @ManyToOne
    private Person author;
    
    public Book(){}

    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        try{
            this.downloadCount = Double.valueOf(dataBook.downloadCount());
        }catch (NumberFormatException e){
            this.downloadCount = 0.0;
        }
        this.languages = dataBook.languages();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Double downloadCount) {
        this.downloadCount = downloadCount;
    }
    
    @Override
    public String toString() {
        return "\n" + "Libro: " + "\n" +
                "Title='" + title + "\n" +
                "Descargas=" + downloadCount + "\n";
    }

}
