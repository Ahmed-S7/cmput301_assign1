import com.example.asshittu_mybookwishlist.Book;

import java.util.ArrayList;

public class Wishlist {
    private ArrayList<Book> books;
    private int bookCount;

    Wishlist( ArrayList<Book> bookArray){
        this.books = bookArray;
        this.bookCount = 0;
    }

}
