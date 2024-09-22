package com.example.asshittu_mybookwishlist;

import android.widget.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DisplayList extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    private Context context;

    public DisplayList(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View view = convertView;

        if (view == null) {
            //Content.xml spacing was adjusted using copilot:"how do I make al of these text fields evenly spaced?"https://www.bing.com/chat?q=github&sendquery=1&FORM=SCCODX - 2024-22-09
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        Book book = books.get(position);

        TextView bookTitle = view.findViewById(R.id.book_title);
        TextView authorName = view.findViewById(R.id.author_name);
        TextView genre = view.findViewById(R.id.genre);
        CheckBox readStatus = view.findViewById(R.id.read_status);


        bookTitle.setText(book.getTitle());
        authorName.setText(book.getAuthorName());
        genre.setText(book.getGenre());
        readStatus.setChecked(book.isRead());

        return view;
    }
}



