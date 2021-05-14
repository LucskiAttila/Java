package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.Screening;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository <Book, Long>{
    @Query("value = SELECT * FROM Book WHERE Book.Screening = :screening", nativeQuery = true)
    List<Book> findByScreen(@Param("screening") Screening screening);
}
