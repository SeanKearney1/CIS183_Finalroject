package com.example.dungeoncreator;

public class Dungeon {
    private int category = 0;
    private int ownerId = -1;
    private int dungeonId = -1;
    private String name = "NEW DUNGEON";
    private int views = 0;
    private int likes = 0;
    private int dislikes = 0;
    private int theme;
    private int[][] tiles = new int[DungeonData.getDungeonSize()][DungeonData.getDungeonSize()];
    private int[][] objects = new int[DungeonData.getDungeonSize()][DungeonData.getDungeonSize()];
    private int[][] enemies = new int[DungeonData.getDungeonSize()][DungeonData.getDungeonSize()];
    private int[][] items = new int[DungeonData.getDungeonSize()][DungeonData.getDungeonSize()];

    public Dungeon() {}

    public Dungeon(int c, int oi, int di, String n, int v, int l, int dl, int[][] t, int[][] ob, int[][] en, int[][] ite, int th) {
        category = c;
        ownerId = oi;
        dungeonId = di;
        name = n;
        views = v;
        likes = l;
        dislikes = dl;
        tiles = t;
        objects = ob;
        enemies = en;
        items = ite;
        theme = th;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(int dungeonId) {
        this.dungeonId = dungeonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    public int[][] getObjects() {
        return objects;
    }

    public void setObjects(int[][] objects) {
        this.objects = objects;
    }

    public int[][] getEnemies() {
        return enemies;
    }

    public void setEnemies(int[][] enemies) {
        this.enemies = enemies;
    }

    public int[][] getItems() {
        return items;
    }

    public void setItems(int[][] items) {
        this.items = items;
    }

    public void setTheme(int t) {
        theme = t;
    }
    public int getTheme() {
        return theme;
    }
}
