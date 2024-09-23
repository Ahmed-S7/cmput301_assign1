package com.example.asshittu_mybookwishlist;

public class User {
    UserWishlist bookWishList;

    User(UserWishlist wishlist){
        this.bookWishList = wishlist;
    }
    public UserWishlist getBookWishList() {
        return bookWishList;
    }

    public void setBookWishList(UserWishlist bookWishList) {
        this.bookWishList = bookWishList;
    }
}

