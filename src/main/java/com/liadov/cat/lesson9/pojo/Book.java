package com.liadov.cat.lesson9.pojo;

import com.liadov.cat.lesson9.annotations.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Book
 *
 * @author Aleksandr Liadov on 4/20/2021
 */
@Entity
public class Book {
    private static final Logger log = LoggerFactory.getLogger(Book.class);

    private int size;
    private String author;
    private String title;

    public Book() {
        log.info("Book object initialization");
    }


    @Override
    public String toString() {
        return "Book{" +
                "size=" + size +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
