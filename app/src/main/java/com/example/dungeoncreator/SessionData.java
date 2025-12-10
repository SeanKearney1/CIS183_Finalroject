package com.example.dungeoncreator;

public class SessionData {

    private static User loggedInUser;
    private static int currentDungeonId;
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggenInUser) {
        SessionData.loggedInUser = loggenInUser;
    }

    public static int getCurrentDungeonId() { return currentDungeonId; }
    public static void setCurrentDungeonId(int dungeonId) {
        SessionData.currentDungeonId = dungeonId;
    }
}
