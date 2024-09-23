package com.example.asshittu_mybookwishlist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
//Directs the program logic
public class MainActivity extends AppCompatActivity implements AddOrEditBookDetailsFragment.FragmentButtonsListener {

    private ListView bookListView;
    private ArrayAdapter<Book> bookArrayAdapter;
    private User newUser;
    private UserWishlist myWishList;
    private ArrayList<Book> myBooks;
    private TextView bookCount;
    private TextView readCount;
    private int numBooks;
    private int numReadBooks;


    //Holds all of the operations for the initial creation of the the app on startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookListView = findViewById(R.id.book_list);
        myBooks = new ArrayList<>();
        myWishList = new UserWishlist(myBooks);
        newUser = new User(myWishList);
        myBooks = myWishList.getBooks();
        bookArrayAdapter = new DisplayList(this, myBooks);
        bookListView.setAdapter(bookArrayAdapter);


        //TEXTVIEWS TO HOLD THE NUMBER OF INDIVIDUAL BOOKS AS WELL AS READ BOOKS
        bookCount = findViewById(R.id.book_count);
        readCount = findViewById(R.id.read_count);



        readCount.setText(String.valueOf(myWishList.getNumReadBooks()));
        bookCount.setText(String.valueOf(myWishList.getBookCount()));


        FloatingActionButton addBookButton = findViewById(R.id.add_book_button);
        addBookButton.setOnClickListener((v) -> {
            new AddOrEditBookDetailsFragment().show(getSupportFragmentManager(), "ADD/EDIT_CITY");
        });

        //sends a bundle with all relevant information about the book in a bundle for use inside of the fragment
        //all operations follow any clicks on a book item using the OnItemClickListener

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View book, int bookPosition, long l) {
                Bundle bookInfo = new Bundle();
                Book chosenBook = (Book) adapterView.getItemAtPosition(bookPosition);
                bookInfo.putInt("book_index", bookPosition);//indexes the book
                bookInfo.putString("book_title", chosenBook.getTitle());//stores the book title
                bookInfo.putString("book_author", chosenBook.getAuthorName());//stores the author
                bookInfo.putString("book_genre", chosenBook.getGenre());//stores genre
                bookInfo.putString("publication_year", chosenBook.getPublicationYear());//stores publication year
                bookInfo.putBoolean("read_status", chosenBook.isRead());//stores the read status
                AddOrEditBookDetailsFragment AddOrEditBook = new AddOrEditBookDetailsFragment();
                AddOrEditBook.setArguments(bookInfo);
                AddOrEditBook.show(getSupportFragmentManager(), "ADD/EDIT/DELETE BOOK");
            }
        });
    }

    //method that is invoked for new book additions
    @Override
    public void onOkPressed(Book newBook, boolean readStatus) {

            //add the book
            newUser.getBookWishList().addBook(newBook);
            //update the book count
            numBooks = newUser.getBookWishList().getBookCount();
            //updates the text view for the book count
            bookCount.setText(String.valueOf(numBooks));
            int bookPos = myWishList.getBooks().indexOf(newBook);
            //updates the read status of the new book (it will be false because this is a new book)
            myWishList.getBooks().get(bookPos).setRead(readStatus);
            //gets the number of read books
            numReadBooks = myWishList.getNumReadBooks();
            //updates the textview for the read count
            readCount.setText(String.valueOf(numReadBooks));
            //updates the array adapter of the changes in the ArrayList
            bookArrayAdapter.notifyDataSetChanged();

    }

    //method that is invoked when a book is edited
    @Override
    public void onOkPressed2(Book newBook, Integer selectedBook, boolean readStatus) {


            //finds the current book, replaces its info with that of the new book
            Book currentBook = myWishList.getBookAtPosition(selectedBook);
            //Adjusts the fields of the book
            currentBook.setTitle(newBook.getTitle());
            currentBook.setRead(readStatus);
            currentBook.setAuthorName(newBook.getAuthorName());
            currentBook.setGenre(newBook.getGenre());
            currentBook.setPublicationYear(newBook.getPublicationYear());

            newBook = null;//to clear the newBook storage

            //Adjusts the count of read books
            numReadBooks = myWishList.getNumReadBooks();
            //updates the textview for the read count
            readCount.setText(String.valueOf(numReadBooks));
            bookArrayAdapter.notifyDataSetChanged();

    }

    //method that is invoked when a book is deleted
    @Override
    public void onDeletePressed(int selectedBook) {

        Book bookToRemove = myWishList.getBooks().get(selectedBook);

        if (selectedBook >= 0 && selectedBook < myBooks.size()) {
            Toast confirm = new Toast(this);
            confirm.setText("Book: '" + bookToRemove.getTitle() + "' deleted");
            confirm.setDuration(Toast.LENGTH_SHORT);
            confirm.show();

            //deletes the book
            myWishList.removeBook(bookToRemove);
            //updates the book count
            bookCount.setText(String.valueOf(myWishList.getBookCount()));
            //updates the read count
            readCount.setText(String.valueOf(myWishList.getNumReadBooks()));
            bookArrayAdapter.notifyDataSetChanged();
        }


    }
}
