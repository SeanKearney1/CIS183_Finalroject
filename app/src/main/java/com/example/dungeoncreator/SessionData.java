package com.example.dungeoncreator;

public class SessionData {

    private static User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggenInUser) {
        SessionData.loggedInUser = loggenInUser;
    }
}
