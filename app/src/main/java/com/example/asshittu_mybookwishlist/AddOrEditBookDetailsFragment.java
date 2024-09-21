package com.example.asshittu_mybookwishlist;

import android.widget.Toast;
import android.widget.CheckBox;
import android.widget.EditText;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddOrEditBookDetailsFragment extends DialogFragment {
    private EditText bookTitle;
    private EditText authorName;
    private EditText genre;
    private EditText publicationYear;
    private CheckBox readStatus;
    private OnFragmentInteractionListener listener;
    //for editing and deleting books
    private int publication_year_for_book_editing;
    private String bookTitleForBookEditing;
    private String genreForBookEditing;
    private String authorNameForEditing;
    private Boolean readStatusForEditing;
    private int selectedBook;
    //flags
    private boolean validTitle = false;
    private boolean validAuthor = false;
    private boolean validGenre = false;
    private boolean validPubYear = false;
    //for adding books
    private String title;
    private String author;
    private String genreName;
    private String publication_Year;




    public interface OnFragmentInteractionListener {

        //to be implemented for adding new books
        void onOkPressed(Book newBook);

        //to be implemented for the editing or deletion of an already existing book in the wishlist
        void onOkPressed2(Book newBook, int selectedBook);//additionally adds the index of the selected book so that it can be edited

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.book_details, null);
        bookTitle = view.findViewById(R.id.book_title_editText);
        authorName = view.findViewById(R.id.author_name_editText);
        genre = view.findViewById(R.id.genre_name_editText);
        publicationYear = view.findViewById(R.id.publication_Year_editText);
        readStatus = view.findViewById(R.id.read_status_checkbox);
        //to hold an integer version of the publication year


        //This conditional is invoked whenever the user clicks a book on the listview, because this means that arguments will be sent to the class
        if (getArguments() != null) {
            //sets all of the fields to the passed arguments from the book that was clicked in the wishlist
            //STORES ALL OF THE VALUES THAT ARE RELATED TO THE BOOK IN A VARIABLE SO THAT THEY CAN BE REUSED
            selectedBook = getArguments().getInt("book_Index");

            bookTitleForBookEditing = getArguments().getString("book_title");
            bookTitle.setText(bookTitleForBookEditing);


            authorNameForEditing = getArguments().getString("author_name");
            authorName.setText(authorNameForEditing);

            genreForBookEditing = getArguments().getString("genre_name");
            genre.setText(genreForBookEditing);


            publication_year_for_book_editing = getArguments().getInt("publication_year");
            publicationYear.setText(String.valueOf(publication_year_for_book_editing));

            readStatusForEditing = getArguments().getBoolean("read_status");
            readStatus.setChecked(readStatusForEditing);


        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("ADD/EDIT/DELETE BOOK")
                //TODO: .setNeutralButton() - make this the delete button later
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    //INPUT VALIDATION WILL TAKE PLACE DURING THIS STEP
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //handles the ok press for cases of editing or deleting a book
                        if (getArguments() != null) {
                            //calls the listener to indicate that the ok button was pressed, the adjustment of the listview will be handled in Main using Overrided implementations of the onOKClicked method
                            listener.onOkPressed2((new Book(bookTitleForBookEditing, authorNameForEditing, genreForBookEditing, publication_Year)), selectedBook);
                        } else {

                            title = bookTitle.getText().toString();
                            author = authorName.getText().toString();
                            genreName = genre.getText().toString();
                            publication_Year = publicationYear.getText().toString();


                            listener.onOkPressed(new Book(title, author, genreName, publication_Year));


                        }

                    }
                }).create();
    }

}

