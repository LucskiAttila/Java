package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM User" ,countQuery = "SELECT count(*) FROM User", nativeQuery = true)
    int getNumberOfUsers();

    @Query(value = "SELECT * FROM User WHERE User.isSignedIn = true", nativeQuery = true)
    User findBySingedIn();

    @Query(value = "SELECT * FROM User WHERE User.isAdmin = true", nativeQuery = true)
    List<User> findSingedUpAdministrator();

    @Query(value = "SELECT * FROM User WHERE User.username = ?1 and User.password = ?2", nativeQuery = true)
    User findByUserNameAndPassword(String username, String password);

    User findByUserName(String username);

    User findByIsAdmin(boolean isAdmin);

    User findByIsSigned(boolean isSigned);


}
