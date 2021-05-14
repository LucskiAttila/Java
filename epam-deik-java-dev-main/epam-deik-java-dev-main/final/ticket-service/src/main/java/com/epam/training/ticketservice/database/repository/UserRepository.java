package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository <User, String> {
    @Query(value = "SELECT * FROM User" ,"countQuery = SELECT count(*) FROM User", nativeQuery = true)
    int getNumberOfUsers();

    @Query(value = "SELECT * FROM User WHERE User.isSignedIn = true")
    User findSingedIn();

    @Query(value = "SELECT * FROM User WHERE User.isAdmin = true")
    List<User> findSingedUpAdminsistrator();

    @Query(value = "SELECT * FROM User WHERE User.username = ?1 and User.password = ?2")
    User findByUserNameAndPassword(String username, String password);
}
