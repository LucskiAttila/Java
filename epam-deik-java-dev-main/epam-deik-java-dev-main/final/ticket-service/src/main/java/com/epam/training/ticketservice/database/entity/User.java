package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class User {

    @Id
    private String userName;
    private String password;
    private Boolean isAdmin;
    private Boolean isSignedIn;
    @OneToOne
    private List<Book> book;

    protected User() {}

    public User(String username, String password, Boolean isAdmin, Boolean isSignedIn, List<Book> book) {
        this.userName = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isSignedIn = isSignedIn;
        this.book = book;
    }
    public String getUserName() {
        return userName;
    }
    public Boolean getIsAdmin() {
        return isAdmin;
    }
    public Boolean getIsSignedIn() {
        return isSignedIn;
    }
    public List<Book> getBook() {
        return book;
    }
    /*public void setBook(List<Book> books) {
        this.book = books;
    }*/
    public String getPassword() {
        return password;
    }
}
