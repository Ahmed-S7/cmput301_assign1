package com.example.asshittu_mybookwishlist;
//class representing the user who owns the book wishlist
public class User {
    UserWishlist bookWishList;

    User(UserWishlist wishlist){
        this.bookWishList = wishlist;
    }

    //returns the user's wishlist
    public UserWishlist getBookWishList() {
        return bookWishList;
    }
}

