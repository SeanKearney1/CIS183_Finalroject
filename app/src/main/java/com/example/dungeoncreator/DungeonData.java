package com.example.dungeoncreator;

public class DungeonData {

    private static final int dungeonSize = 32;
    private static final String[] categories = {"Normal","Frozen","Desert","Void"};
    private static final String[] items = {"Sword","Key"};
    private static final String[] enemies = {"Slime","Skull"};
    private static final String[] objects = {"LeverOff","LeverOn","Bridge","DoorClosed","DoorOpened","FinalDoorClosed","FinalDoorOpened","SpawnerOn","SpawnerOff"};

    private static Dungeon currentDungeon;

    public static String[] getCategories() {
        return categories;
    }
    public static String[] getItems() {
        return items;
    }
    public static String[] getEnemies() {
            return enemies;
    }
    public static String[] getObjects() {
        return objects;
    }
    public static int getDungeonSize() {
        return dungeonSize;
    }

    public static Dungeon getCurrentDungeon() {
        return currentDungeon;
    }
    public static void setCurrentDungeon(Dungeon d) {
        currentDungeon = d;
    }
}
