package com.example.asshittu_mybookwishlist;

import java.util.ArrayList;

public class UserWishlist {

    private ArrayList<Book> books;
    //represents the number of books inside of the wishlist
    private int bookCount;
    private int numReadBooks;

    UserWishlist(ArrayList<Book> bookArray){
        this.books = bookArray;
        this.bookCount = books.size();
    }

    public ArrayList<Book> getBooks(){
        return books;
    }

    public void setBooks(ArrayList<Book> books){
        this.books = books;
    }

    public Book getBookAtPostion(int position){

        return books.get(position);
    }

    public int getBookPosition(Book book){
        return this.books.indexOf(book);
    }

    public void addBook(Book book){
        books.add(book);
        bookCount = bookCount + 1;
    }
    public int getBookCount(){
        bookCount = books.size();
        return bookCount;
    }

    public int getNumReadBooks(){
        numReadBooks = 0;
        for (int i = 0; i < books.size(); i++){
            if (books.get(i).isRead()){
                numReadBooks++;
            }
        }
        return numReadBooks;
    }

    public void removeBook(Book book){
        books.remove(book);
    }


}