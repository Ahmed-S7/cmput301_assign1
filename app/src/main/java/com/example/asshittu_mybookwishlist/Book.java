package com.example.asshittu_mybookwishlist;

import androidx.annotation.NonNull;

public class Book {
private String title;
private String authorName;
private String genre;
private int publicationYear;
private Boolean read;

Book (@NonNull String title, String authorName, String genre, int publicationYear){
    this.title = title;
    this.authorName = authorName;
    this.genre = genre;
    this.publicationYear = publicationYear;
    read = false;
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

    public int getPublicationYear(){
        return publicationYear;
    }

    public Boolean getRead(){
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

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setRead(Boolean read){
        this.read = read;
    }
}
