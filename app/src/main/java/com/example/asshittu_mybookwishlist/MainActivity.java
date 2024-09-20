package com.example.asshittu_mybookwishlist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.asshittu_mybookwishlist.R;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //This is the listview for all of the books inside of the wishlist
    ListView bookListView;

    //This will function as the array adapter for all of the books inside of the wishlist, will communicate with the application to represent the list
    ArrayList<Book> bookList;


    //This is the array adapter for the list of books inside of the wishlist
    ArrayAdapter<Book> bookArrayAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //TODO: Initialize the array of the wishlist;
    //TODO: design a custom list to represent the wishlist entries
    // - the custom list should have 5 fields for each of the required elements of a book
    // - implement logic for input validation when the user attempts to edit or add a new field
        bookListView = findViewById(R.id.city_list);
        ArrayList<Book> bookList = new ArrayList<>();




    }
}