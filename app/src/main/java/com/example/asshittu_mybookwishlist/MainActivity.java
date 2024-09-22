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

public class MainActivity extends AppCompatActivity implements AddOrEditBookDetailsFragment.OnFragmentInteractionListener {

    private ListView bookListView;
    private ArrayList<Book> bookList;
    private ArrayAdapter<Book> bookArrayAdapter;
    private Wishlist myWishList;
    private ArrayList<Book> myBooks;
    private TextView bookCount;
    private TextView readCount;
    int numBooks;
    int numReadBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        bookListView = findViewById(R.id.book_list);
        bookList = new ArrayList<>();
        myWishList = new Wishlist(bookList);
        myBooks = myWishList.getBooks();
        bookArrayAdapter = new DisplayList(this, myBooks);
        bookListView.setAdapter(bookArrayAdapter);

        bookCount = findViewById(R.id.book_count);
        readCount = findViewById(R.id.read_count);

        //TEST DATA
        /*
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "1925");
        book1.setRead(true);


        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", "1960");
        book2.setRead(false);

        Book book3 = new Book("1984", "George Orwell", "Dystopian", "1949");
        book3.setRead(true);

        Book book4 = new Book("The Search", "Gene Luen Yang", "Graphic Novel", "2013");
        book4.setRead(true);
        Book book5 = new Book("The Rift", "Gene Luen Yang", "Graphic Novel", "2014");
        book5.setRead(true);

        Book book6 = new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", "1997");
        book6.setRead(true);

        Book book7 = new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Fantasy", "1998");
        book3.setRead(true);

        Book book8 = new Book("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", "Fantasy", "1999");


        Book book9 = new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling", "Fantasy", "2000");
        book3.setRead(true);

        Book book10 = new Book("Harry Potter and the Order of the Phoenix", "J.K. Rowling", "Fantasy", "2003");

        myWishList.addBook(book1);
        myWishList.addBook(book2);
        myWishList.addBook(book3);
        myWishList.addBook(book4);
        myWishList.addBook(book5);
        myWishList.addBook(book6);
        myWishList.addBook(book7);
        myWishList.addBook(book8);
        myWishList.addBook(book9);
        myWishList.addBook(book10);
        */

        readCount.setText(String.valueOf(myWishList.getNumReadBooks()));
        bookCount.setText(String.valueOf(myWishList.getBookCount()));


        FloatingActionButton addBookButton = findViewById(R.id.add_book_button);
        addBookButton.setOnClickListener((v) -> {
            new AddOrEditBookDetailsFragment().show(getSupportFragmentManager(), "ADD/EDIT_CITY");
        });

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

    @Override
    public void onOkPressed(Book newBook, boolean readStatus) {
        if (!newBook.getTitle().strip().isEmpty() && !newBook.getAuthorName().strip().isEmpty()) {
            //add the book
            myWishList.addBook(newBook);
            //update the book count
            numBooks = myWishList.getBookCount();
            //updates the text view for the book count
            bookCount.setText(numBooks);
            int bookPos = myWishList.getBooks().indexOf(newBook);
            //updates the read status of the new book (it will be false because this is a new book)
            myWishList.getBooks().get(bookPos).setRead(readStatus);

            bookArrayAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onOkPressed2(Book newBook, Integer selectedBook, boolean readStatus) {


            //finds the current book, replaces its info with that of the new book
            myWishList.getBooks().set(selectedBook, newBook);
            //Adjusts the read status of the book
            myWishList.getBookAtPostion(selectedBook).setRead(readStatus);
            //Adjusts the count of read books
            numReadBooks = myWishList.getNumReadBooks();
            //updates the textview for the read count
            readCount.setText(String.valueOf(numReadBooks));


            bookArrayAdapter.notifyDataSetChanged();

    }

    @Override
    public void OnDeletePressed(int selectedBook) {

        Book bookToRemove = myWishList.getBooks().get(selectedBook);

        if (selectedBook >= 0 && selectedBook < bookList.size()) {
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
