package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByScreening(Screening screening);
}
