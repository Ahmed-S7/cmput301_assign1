package com.example.asshittu_mybookwishlist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public abstract class MainActivity extends AppCompatActivity implements AddOrEditBookDetailsFragment.OnFragmentInteractionListener {

    //This is the listview for all of the books inside of the wishlist
    ListView bookListView;

    //This will function as the array adapter for all of the books inside of the wishlist, will communicate with the application to represent the list
    ArrayList<Book> bookList;

    //This is the array adapter for the list of books inside of the wishlist
    ArrayAdapter<Book> bookArrayAdapter;

    Wishlist wishlist;



    //TODO: Initialize the array of the wishlist;
    //TODO: design a custom list to represent the wishlist entries
    // - the custom list should have 5 fields for each of the required elements of a book
    // - implement logic for input validation when the user attempts to edit or add a new field


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookListView = findViewById(R.id.city_list);

        bookList = new ArrayList<>();

        Book HarryPotterBook = new Book("Harry Potter And The Philosopher's Stone","JK Rowling", "Fantasy", "1997");

        //TESTING
        bookArrayAdapter.add((HarryPotterBook));

        //operations from here on out will be carried out by editing values inside the wishlist's bookArray
        wishlist = new Wishlist(bookList);

        bookArrayAdapter = new DisplayList(this, wishlist.getBooks());

        bookListView.setAdapter(bookArrayAdapter);

        final FloatingActionButton addBookButton = findViewById(R.id.add_book_button);


        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View book, int bookPosition, long l) {
               Bundle bookInfo = new Bundle();

               Book chosenBook = (Book) adapterView.getItemAtPosition(bookPosition);

               bookInfo.putInt("book_index", bookPosition);

               bookInfo.putString("book_title", chosenBook.getTitle());

               bookInfo.putString("book_genre", chosenBook.getGenre());

               bookInfo.putString("publication_year", chosenBook.getPublicationYear());

               bookInfo.putBoolean("read_status", chosenBook.getRead());

               AddOrEditBookDetailsFragment AddOrEditBook = new AddOrEditBookDetailsFragment();

               AddOrEditBook.setArguments(bookInfo);

               AddOrEditBook.show(getSupportFragmentManager(),"ADD/EDIT/DELETE BOOK");

            }
        });

        addBookButton.setOnClickListener((view) -> {
            new AddOrEditBookDetailsFragment().show(getSupportFragmentManager(),"ADD/EDIT/DELETE BOOK");
        });

    }
    //invoked method for whenever the AddCity button it clicked
    @Override
    public void onOkPressed(Book newBook){
        //Adds non-empty strings into the list view (if at least on field is non-empty, otherwise nothing will be added to the list
        if(!((newBook.getTitle().strip().equals("") && newBook.getAuthorName().strip().equals("")))){
           wishlist.addBook(newBook);
            bookArrayAdapter.add(newBook);
            bookArrayAdapter.notifyDataSetChanged();
        }
    }

    //invoked method for whenever a city on the list gets clicked
    @Override
    public void onOkPressed2(Book newBook, int selectedBook){

        //remove the city being edited
        //this also serves the additional functionality of deleting any cities that are left empty while editing them after clicking "ok"

        Book book_to_delete = bookArrayAdapter.getItem(selectedBook);
        bookArrayAdapter.remove(book_to_delete);
        bookArrayAdapter.notifyDataSetChanged();//fixed by copilot: 2024-09-20

        //inserts the city and province entered by the user in its place
        if(!((newBook.getTitle().strip().equals("") && newBook.getAuthorName().strip().equals("")))){
            bookArrayAdapter.insert(newBook,selectedBook);
        }


    }
}
