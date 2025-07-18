/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.aluraCourse.literAlura.repository;

import com.aluraCourse.literAlura.model.entities.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author usuario
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleIgnoreCaseAndAuthor_NameIgnoreCase(String title, String authorName);
    long countByLanguagesContaining(String language);
    List<Book> findTop10ByOrderByDownloadCountDesc();
}
