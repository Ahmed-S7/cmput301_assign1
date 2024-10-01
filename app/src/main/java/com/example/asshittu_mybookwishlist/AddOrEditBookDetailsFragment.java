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

import java.util.Calendar;


public class AddOrEditBookDetailsFragment extends DialogFragment {
    //or user interaction
    private EditText bookTitle;
    private EditText authorName;
    private EditText genre;
    private EditText publicationYear;
    private CheckBox readStatus;
    private FragmentButtonsListener listener;
    //for editing and deleting books
    private String bookTitleForBookEditing;
    private String genreForBookEditing;
    private String authorNameForEditing;
    private Boolean readStatusForEditing;
    public int selectedBook;
    //flags
    private boolean validTitle = false;
    private boolean validAuthor = false;
    private boolean validPubYear = false;
    //for adding books
    private String title;
    private String author;
    private String genreName;
    private String publication_Year;
    private Boolean editing = false;


    //Interface used for all of the different interactions with the fragment's buttons
    //the bottom two methods include an integer that is used for the index because they are implemented for use upon clicking of the listView
    public interface FragmentButtonsListener {

        //for adding new books
        void onOkPressed(Book book,boolean readStatus);

        //for the editing or deletion of an already existing book in the wishlist
        void onOkPressed2(Book newBook, Integer selectedBook, boolean readStatus);//additionally adds the index of the selected book so that it can be edited

        //for deletions
        void onDeletePressed(int selectedBook);

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentButtonsListener){
            listener = (FragmentButtonsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement FragmentButtonsListener");
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


        //This condition is invoked whenever the user clicks a book on the listview, because this means that arguments will be sent to the class
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

            //if the book being edited has an unspecified genre, the edit Text will be empty to reflect this
            if(genreForBookEditing.equals("  N/A  ")){
                genre.setText("");
            }else {
                genre.setText(genreForBookEditing);
            }

            publicationYear.setText(getArguments().getString("publication_year"));

            readStatusForEditing = getArguments().getBoolean("read_status");
            readStatus.setChecked(readStatusForEditing);


        }

        //Builds the alert dialog
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
                                !publicationYear.getText().toString().isEmpty()) {
                            listener.onDeletePressed(selectedBook);
                        } else {
                            Toast.makeText(getContext(), "Cannot delete. Required Fields are empty.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    //INPUT VALIDATION WILL TAKE PLACE DURING THIS STEP
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Calendar cal = Calendar.getInstance();
                        title = bookTitle.getText().toString();
                        author = authorName.getText().toString();
                        genreName = genre.getText().toString();
                        publication_Year = publicationYear.getText().toString();
                        readStatusForEditing = readStatus.isChecked();

                        //handles the ok press for cases of adding or editing a book
                        //validates all 4 entries during the next step
                        //only proper entries are allowed to be entered to the wishlist, otherwise the user is notified that their entry has failed
                        if (title.length() > 50) {

                                    Toast titleTooLong = new Toast(getContext());
                                    titleTooLong.setText("Could not add book, book title must be under 50 characters");
                                    titleTooLong.setDuration(Toast.LENGTH_SHORT);
                                    titleTooLong.show();


                                    bookTitle.setText("");


                        } else if (title.strip().equals("")) {
                            Toast titleEmpty = new Toast(getContext());
                            titleEmpty.setText("Could not add book, book title field cannot be empty");
                            titleEmpty.setDuration(Toast.LENGTH_SHORT);
                            titleEmpty.show();



                            bookTitle.setText("");
                        }else {
                                validTitle = true;
                        }



                        if (author.length() > 30) {

                                    Toast authorTooLong = new Toast(getContext());
                                    authorTooLong.setText("Could not add book, author name must be under 30 characters");
                                    authorTooLong.setDuration(Toast.LENGTH_SHORT);
                                    authorTooLong.show();

                                    authorName.setText("");

                        } else if (author.isEmpty()){

                                    Toast authorEmpty = new Toast(getContext());
                                    authorEmpty.setText("Could not add book, author name field cannot be empty");
                                    authorEmpty.setDuration(Toast.LENGTH_SHORT);
                                    authorEmpty.show();

                                    authorName.setText("");


                        }else{
                                    validAuthor = true;
                        }



                                //REGEX condition derived from copilot https://www.bing.com/chat?q=what+is+an+implicit+downcase+in+Java&sendquery=1&FORM=SCCODX- 2024-09-20
                        if ((!publication_Year.isEmpty()) && !publication_Year.matches("\\d{4}")) {

                                    Toast pubYearTooLong = new Toast(getContext());
                                    pubYearTooLong.setText("Could not add book, please enter a valid year");
                                    pubYearTooLong.setDuration(Toast.LENGTH_SHORT);
                                    pubYearTooLong.show();

                                    publicationYear.setText("");


                        } else if ((publication_Year.isEmpty())){

                                Toast pubYearEmpty = new Toast(getContext());
                                pubYearEmpty.setText("Could not add book, publication year field cannot be empty");
                                pubYearEmpty.setDuration(Toast.LENGTH_SHORT);
                                pubYearEmpty.show();

                                publicationYear.setText("");


                        } else if((publication_Year.matches("\\d{4}")) && ((Integer.parseInt(publication_Year) > cal.get(Calendar.YEAR)))){

                            Toast pubYearInFuture = new Toast(getContext());
                            pubYearInFuture.setText("Could not add book, publication year field cannot be later than the current year");
                            pubYearInFuture.setDuration(Toast.LENGTH_SHORT);
                            pubYearInFuture.show();

                            publicationYear.setText("");

                        } else {
                                    validPubYear = true;
                        }
                        //If the user enters no genre, the field will be labelled as "unspecified"
                        if (genreName.strip().equals("")){
                            genreName = "  N/A  ";
                        }

                        //ok button press which takes places after all validation is complete
                        if (validTitle && validAuthor && validPubYear) {
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
