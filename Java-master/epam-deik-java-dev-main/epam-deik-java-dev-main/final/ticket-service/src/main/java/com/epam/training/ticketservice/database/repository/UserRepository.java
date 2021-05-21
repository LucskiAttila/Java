package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserName(String username);

    User findByIsAdmin(boolean isAdmin);

    User findByIsSigned(boolean isSigned);


}
