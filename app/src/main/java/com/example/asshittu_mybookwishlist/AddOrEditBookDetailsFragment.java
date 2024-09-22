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
    private String bookTitleForBookEditing;
    private String genreForBookEditing;
    private String authorNameForEditing;
    private Boolean readStatusForEditing;
    public int selectedBook;
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
    private Boolean editing = false;


    //Interface used for all of the different interactions with the fragment's buttons
    //the bottom two methods include an integer that is used for the index because they are implemented for use upon clicking of the listView
    public interface OnFragmentInteractionListener {

        //to be implemented for adding new books
        void onOkPressed(Book book,boolean readStatus);

        //to be implemented for the editing or deletion of an already existing book in the wishlist
        void onOkPressed2(Book newBook, Integer selectedBook, boolean readStatus);//additionally adds the index of the selected book so that it can be edited

        //to be implemented for deletions
        void OnDeletePressed(int selectedBook);

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
            editing = true;
            //sets all of the fields to the passed arguments from the book that was clicked in the wishlist
            //STORES ALL OF THE VALUES THAT ARE RELATED TO THE BOOK IN A VARIABLE SO THAT THEY CAN BE REUSED
            selectedBook = getArguments().getInt("book_index");

            bookTitleForBookEditing = getArguments().getString("book_title");
            bookTitle.setText(bookTitleForBookEditing);


            authorNameForEditing = getArguments().getString("book_author");
            authorName.setText(authorNameForEditing);

            genreForBookEditing = getArguments().getString("book_genre");
            genre.setText(genreForBookEditing);


            publicationYear.setText(getArguments().getString("publication_year"));

            readStatusForEditing = getArguments().getBoolean("read_status");
            readStatus.setChecked(readStatusForEditing);


        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("ADD/EDIT/DELETE BOOK")
                //TODO: .setNeutralButton() - make this the delete button later
                .setNegativeButton("Cancel", null)
                .setNeutralButton("DELETE", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Condition derived from Copilot search: "my delete button is deleting elements even when the edittexts are empty, how do I prevent that?" - 2024-09-22
                        if (!bookTitle.getText().toString().isEmpty() &&
                                !authorName.getText().toString().isEmpty() &&
                                !genre.getText().toString().isEmpty() &&
                                !publicationYear.getText().toString().isEmpty()) {
                            listener.OnDeletePressed(selectedBook);
                        } else {
                            Toast.makeText(getContext(), "Cannot delete. Fields are empty.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    //INPUT VALIDATION WILL TAKE PLACE DURING THIS STEP
                    public void onClick(DialogInterface dialogInterface, int i) {



                        title = bookTitle.getText().toString();
                        author = authorName.getText().toString();
                        genreName = genre.getText().toString();
                        publication_Year = publicationYear.getText().toString();
                        readStatusForEditing = readStatus.isChecked();

                        //handles the ok press for cases of editing or deleting a book


                        //TODO: validate all 4 entries during the next step, **remember to keep read status as false for the newly added entries**
                        if (title.length() > 50) {

                                    Toast titleTooLong = new Toast(getContext());
                                    titleTooLong.setText("Could not add book, book title must be under 50 characters");
                                    titleTooLong.setDuration(Toast.LENGTH_SHORT);
                                    titleTooLong.show();


                                    bookTitle.setText("");


                        } else if (title.strip().equals("")) {
                            Toast titleEmpty = new Toast(getContext());
                            titleEmpty.setText("Could not add book, book title must be under 50 characters");
                            titleEmpty.setDuration(Toast.LENGTH_SHORT);
                            titleEmpty.show();



                            bookTitle.setText("");
                        }else {
                                validTitle = true;
                        }



                        if (author.length() > 50) {

                                    Toast authorTooLong = new Toast(getContext());
                                    authorTooLong.setText("Could not add book, author name must be under 30 characters");
                                    authorTooLong.setDuration(Toast.LENGTH_SHORT);
                                    authorTooLong.show();

                                    authorName.setText("");

                        } else if (author.isEmpty()){

                                    Toast authorEmpty = new Toast(getContext());
                                    authorEmpty.setText("Could not add book, author name must be entered");
                                    authorEmpty.setDuration(Toast.LENGTH_SHORT);
                                    authorEmpty.show();

                                    authorName.setText("");


                        }else{
                                    validAuthor = true;
                                }



                                //REGEX condition derived from copilot https://www.bing.com/chat?q=what+is+an+implicit+downcase+in+Java&sendquery=1&FORM=SCCODX- 2024-09-20
                        if ((!publication_Year.isEmpty()) && !publication_Year.matches("\\d{4}")) {

                                    Toast pubYearTooLong = new Toast(getContext());
                                    pubYearTooLong.setText("Could not add book, publication year must be a 4 digit integer");
                                    pubYearTooLong.setDuration(Toast.LENGTH_SHORT);
                                    pubYearTooLong.show();

                                    publicationYear.setText("");


                        } else if ((publication_Year.isEmpty())){

                                Toast pubYearEmpty = new Toast(getContext());
                                pubYearEmpty.setText("Could not add book, publication year must entered");
                                pubYearEmpty.setDuration(Toast.LENGTH_SHORT);
                                pubYearEmpty.show();

                                publicationYear.setText("");


                        } else {
                                    validPubYear = true;
                                }

                        if ((genreName.isEmpty())){

                                    Toast genreNameEmpty = new Toast(getContext());
                                    genreNameEmpty.setText("Could not add book, genre name must entered");
                                    genreNameEmpty.setDuration(Toast.LENGTH_SHORT);
                                    genreNameEmpty.show();

                                    genre.setText("");


                        }else{
                                validGenre = true;
                                }


                        //ok button press which takes places after all validation is complete
                        if (validTitle && validAuthor && validPubYear && validGenre) {
                            if (editing) {
                                //ok button that is invoked for editing a book's details
                                listener.onOkPressed2((new Book(title, author, genreName, publication_Year)), selectedBook, readStatusForEditing);

                            }else {
                                //ok button that is invoked for new book additions
                                listener.onOkPressed(new Book(title, author, genreName, publication_Year), readStatusForEditing);
                        }

                        }

                    }
                }).create();
    }

}
