package com.example.asshittu_mybookwishlist;

import com.example.asshittu_mybookwishlist.Book;

import java.util.ArrayList;

public class Wishlist extends ArrayList{

    private ArrayList<Book> books;
    private int bookCount;

    Wishlist( ArrayList<Book> bookArray){
        this.books = bookArray;
        this.bookCount = 0;
    }

    public ArrayList<Book> getBooks(){
        return books;
    }

    public void setBooks(ArrayList<Book> books){
        this.books = books;
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void removeBook(Book book){
        books.remove(book);
    }


}
