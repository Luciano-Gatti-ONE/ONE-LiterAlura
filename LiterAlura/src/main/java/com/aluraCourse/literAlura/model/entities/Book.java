package com.aluraCourse.literAlura.model.entities;

import com.aluraCourse.literAlura.model.DataBook;
import com.aluraCourse.literAlura.model.entities.Author;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;

/**
* Entidad que representa un libro almacenado en la base de datos.
* Contiene información como el título, los idiomas disponibles, la cantidad de descargas
* y su autor.
*
* Se encuentra mapeada a la tabla "book" mediante JPA.
*
* @author Luciano E. Gatti Flekenstein
*/

@Entity
@Table(name = "book")
public class Book {
    //Atributos de la entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private Double downloadCount;
    
    @ElementCollection
    private List<String> languages;

    @ManyToOne
    private Author author;
    
    //Constructor por defecto requerido por JPA.
    public Book(){}

    /**
    * Constructor que inicializa un libro a partir de un objeto {@link DataBook},
    * extrayendo el título, las descargas (si no puede parsear, asigna 0.0) y los idiomas.
    *
    * @param dataBook el objeto con los datos extraídos desde la API.
    */
    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        try{
            this.downloadCount = Double.valueOf(dataBook.downloadCount());
        }catch (NumberFormatException e){
            this.downloadCount = 0.0;
        }
        this.languages = dataBook.languages();
    }

    @Override
    public String toString() {
        return "\n" + "Libro: " + "\n" +
                "Title='" + title + "\n" +
                "Descargas=" + downloadCount + "\n";
    }
    
    //Getters y Setters
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
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
}
