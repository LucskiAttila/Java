package com.epam.training.ticketservice.database.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    private String userName;
    private String password;
    private Boolean isAdmin;
    private Boolean isSigned;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Book> book;

    protected User() {}

    public User(String username, String password, Boolean isAdmin, Boolean isSigned, List<Book> book) {
        this.userName = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isSigned = isSigned;
        this.book = book;
    }
    public String getUserName() {
        return userName;
    }
    public Boolean getIsAdmin() {
        return isAdmin;
    }
    public Boolean getIsSigned() {
        return isSigned;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(isAdmin, user.isAdmin) && Objects.equals(isSigned, user.isSigned) && Objects.equals(book, user.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, isAdmin, isSigned, book);
    }
}
