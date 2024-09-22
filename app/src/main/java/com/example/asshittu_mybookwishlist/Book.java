package com.example.asshittu_mybookwishlist;

import androidx.annotation.NonNull;

public class Book {
private String title;
private String authorName;
private String genre;
private String publicationYear;
private Boolean read;
//Class is responsible for attributes and functionalities pertaining to the book class

Book (@NonNull String title,  @NonNull String authorName,  String genre, @NonNull String publicationYear){
    this.title = title;
    this.authorName = authorName;
    this.genre = genre;
    this.publicationYear = publicationYear;
    this.read = false;
}

    //Getters for all fields of a given book
    public String getTitle() {
        return title;
    }

    public String getAuthorName(){
        return authorName;
    }

    public String getGenre(){
        return genre;
    }

    public String getPublicationYear(){
        return publicationYear;
    }

    public Boolean isRead(){
        return read;
    }

    //Setters for all of the fields of a given book
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setRead(Boolean Read){
        read = Read;
    }
}
