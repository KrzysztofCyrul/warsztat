package com.kris.warsztat.helpers;

public class CurrentUser {

    private static com.kris.warsztat.model.user user;

    private CurrentUser() {
    }

    public static com.kris.warsztat.model.user getCurrentUser() {
        return user;
    }

    public static void setCurrentUser(com.kris.warsztat.model.user currentUser) {
        user = currentUser;
    }
}
