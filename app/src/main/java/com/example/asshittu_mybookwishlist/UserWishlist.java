package com.example.asshittu_mybookwishlist;

import java.util.ArrayList;

//Class representing the wishlist and all of its operations
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

    public Book getBookAtPosition(int position){

        return books.get(position);
    }

    public boolean containsBook(Book testBook) {
      boolean duplicate = false;

        for (int i = 0; i < books.size(); i++) {
            //syntax adjusted using Copilot: "can you adjust the brackets so that all of this works as one condition", https://www.bing.com/chat?q=giothiu+n&sendquery=1&FORM=SCCODX, 2024-09-25
            if (testBook.getTitle().equals(books.get(i).getTitle()) &&
                    testBook.getAuthorName().equals(books.get(i).getAuthorName()) &&
                    testBook.getGenre().equals(books.get(i).getGenre()) &&
                    testBook.getPublicationYear().equals(books.get(i).getPublicationYear())) {
                {

                    duplicate = true;
                }
            }
        }
        return duplicate;


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