package com.example.dungeoncreator;

public class DungeonInfoDisplay {

    private int dungeonId;
    private String name;
    private int category;
    private int views;
    private int likes;
    private int dislikes;

    public DungeonInfoDisplay() {}

    public DungeonInfoDisplay(int di, String n, int c, int v, int l, int d) {
        dungeonId = di;
        name = n;
        category = c;
        views = v;
        likes = l;
        dislikes = d;
    }

    public int getDungeonId() { return dungeonId; }
    public String getName() { return name; }
    public int getCategory() { return category; }
    public int getViews() { return views; }
    public int getLikes() { return likes; }
    public int getDislikes() { return dislikes; }

    public void setDungeonId(int di) { dungeonId = di; }
    public void setName(String n) { name = n; }
    public void setCategory(int c) { category = c; }
    public void setViews(int v) { views = v; }
    public void setLikes(int l) { likes = l; }
    public void setDislikes(int d) { dislikes = d; }

}
