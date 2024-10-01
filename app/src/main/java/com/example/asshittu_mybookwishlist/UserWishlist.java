package com.example.asshittu_mybookwishlist;

import java.util.ArrayList;

//Class representing the wishlist and all of its operations
public class UserWishlist {

    private ArrayList<Book> books;
    //represents the number of books inside of the wishlist
    private int bookCount;
    private int numReadBooks;

    //constructor for the books
    UserWishlist(ArrayList<Book> bookArray){
        this.books = bookArray;
        this.bookCount = books.size();
    }

    //retrieves the wishlist
    public ArrayList<Book> getBooks(){
        return books;
    }

    //sets a new wishlist
    public void setBooks(ArrayList<Book> books){
        this.books = books;
    }


    public Book getBookAtPosition(int position){
        return books.get(position);
    }

    //checks if the wishlist contains a specific book
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
    //retrieve the position of a specific book
    public int getBookPosition(Book book){
        return this.books.indexOf(book);
    }

    //adds a book to the wishlist
    public void addBook(Book book){
        books.add(book);
        bookCount = bookCount + 1;
    }
    //gets the number of books inside of the wishlist
    public int getBookCount(){
        bookCount = books.size();
        return bookCount;
    }

    //retrieves the number of read books in the wishlist
    public int getNumReadBooks(){
        numReadBooks = 0;
        for (int i = 0; i < books.size(); i++){
            if (books.get(i).isRead()){
                numReadBooks++;
            }
        }
        return numReadBooks;
    }
    //removes books from the wishlist
    public void removeBook(Book book){
        books.remove(book);
        if(bookCount > 0 ){
            bookCount -= 1;
        }
    }


}