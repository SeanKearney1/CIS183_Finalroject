package com.example.dungeoncreator;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String database_name = "Dungeon.db";
    private static final String users_table_name = "Users";
    private static final String dungeon_header_table_name = "DHeader";
    private static final String dungeon_layout_table_name = "DLayout";
    private static final String dungeon_entities_table_name = "DEntities";

    private static final String[] hexChart = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

    public DatabaseHelper(Context c)
    {
        super(c, database_name, null, 14);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + users_table_name + " (userId integer primary key autoincrement not null, username varchar(64), fname varchar(64), lname varchar(64), favorites varchar(4096));");
        db.execSQL("CREATE TABLE " + dungeon_header_table_name + " (dungeonId integer primary key autoincrement not null, userId integer, name varchar(64), categoryId integer, views integer, likes integer, dislikes integer, foreign key (userId) references \" + users_table_name + \" (userId));");
        db.execSQL("CREATE TABLE " + dungeon_layout_table_name + " (dungeonId integer primary key autoincrement not null, theme integer(3), tiles varchar(4096));");
        db.execSQL("CREATE TABLE " + dungeon_entities_table_name + " (dungeonId integer primary key autoincrement not null, items varchar(4096), enemies varchar(4096), objects varchar(4096));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete the tables in the db if they exist
        db.execSQL("DROP TABLE IF EXISTS " + users_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + dungeon_header_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + dungeon_layout_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + dungeon_entities_table_name + ";");

        //recreate the tables
        onCreate(db);
    }

    public String getUserTableName()
    {
        return users_table_name;
    }
    public String getDungeonHeaderTableName()
    {
        return dungeon_header_table_name;
    }
    public String getDungeonLayoutTableName()
    {
        return dungeon_layout_table_name;
    }
    public String getDungeonEntitesTableName()
    {
        return dungeon_entities_table_name;
    }

    public void initAllTables()
    {
        initUsers();
    }

    private void initUsers()
    {

        if(countRecordsFromTable(users_table_name) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("INSERT INTO " + users_table_name + " (username, fname, lname, favorites) VALUES ('SPK21','Sean', 'Kearney', '');");
            db.execSQL("INSERT INTO " + users_table_name + " (username, fname, lname, favorites) VALUES ('AJK15','Alex', 'Kearney', '');");
            db.execSQL("INSERT INTO " + users_table_name + " (username, fname, lname, favorites) VALUES ('cometcoyote','Angela', 'Williams', '');");
            db.execSQL("INSERT INTO " + users_table_name + " (username, fname, lname, favorites) VALUES ('navidadtamarin','James', 'Smith', '');");
            db.execSQL("INSERT INTO " + users_table_name + " (username, fname, lname, favorites) VALUES ('jollypollution','Daniel', 'Jackson', '');");
            db.execSQL("INSERT INTO " + users_table_name + " (username, fname, lname, favorites) VALUES ('eastchart','David', 'Miller', '');");
            db.execSQL("INSERT INTO " + users_table_name + " (username, fname, lname, favorites) VALUES ('OcHart21','Micheal', 'OcHart', '');");

            db.close();
        }
    }





    public int countRecordsFromTable(String tableName)
    {
        //get an instance of the a readable database
        //we only need readable because we are not adding anything to the database with this action
        SQLiteDatabase db = this.getReadableDatabase();

        //count the number of entries in the table that was passed to the function
        //this is a built-in function
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);

        //whenever you open the database you need to close it
        db.close();

        return numRows;
    }


    public void addUserToDB(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + users_table_name + " (username, fname, lname, favorites) VALUES ('"+u.getUsername()+"','"+u.getFname()+"', '"+u.getLname()+"', '');");
        db.close();
    }


    public ArrayList<Integer> getUserIds() {
        ArrayList<Integer> usernames = new ArrayList<>();
        String selectStatement = "SELECT userId FROM " + users_table_name + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            do {
                usernames.add(Integer.parseInt(cursor.getString(0)));
            }
            while(cursor.moveToNext());
        }


        db.close();
        return usernames;
    }
    public String getUsernameById(int id) {

        String selectStatement = "SELECT username FROM " + users_table_name + " WHERE userId = '" + id + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);


        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        db.close();
        return "";
    }

    public User getUserById(int id) {
        User user = new User();
        String selectStatement = "SELECT * FROM " + users_table_name + " WHERE userId = '" + id + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setUsername(cursor.getString(1));
            user.setFname(cursor.getString(2));
            user.setLname(cursor.getString(3));

        }
        db.close();
        return user;
    }

    public int getIdByUsername(String username) {

        if (username.isEmpty()) { return -1; }

        int userId = -2;
        String selectStatement = "SELECT userId FROM " + users_table_name + " WHERE username = '" + username + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        db.close();


        return userId;
    }








    public Dungeon getDungeonById(int id) {
        Dungeon newDungeon = new Dungeon();
        SQLiteDatabase db = this.getReadableDatabase();
        newDungeon.setDungeonId(id);

        String selectStatement = "SELECT * FROM "+dungeon_header_table_name+" WHERE dungeonId = '"+id+"';";
        Cursor cursor = db.rawQuery(selectStatement,null);
        ArrayList<Integer> columns = new ArrayList<>();

        if (cursor.moveToFirst()) {

            columns.add(cursor.getColumnIndex("userId"));
            columns.add(cursor.getColumnIndex("name"));
            columns.add(cursor.getColumnIndex("categoryId"));
            columns.add(cursor.getColumnIndex("views"));
            columns.add(cursor.getColumnIndex("likes"));
            columns.add(cursor.getColumnIndex("dislikes"));
            columns.add(cursor.getColumnIndex("dungeonId"));
            if (columns.get(0) != -1) { newDungeon.setOwnerId(cursor.getInt(columns.get(0))); }
            if (columns.get(1) != -1) { newDungeon.setName(cursor.getString(columns.get(1))); }
            if (columns.get(2) != -1) { newDungeon.setCategory(cursor.getInt(columns.get(2))); }
            if (columns.get(3) != -1) { newDungeon.setViews(cursor.getInt(columns.get(3))); }
            if (columns.get(4) != -1) { newDungeon.setLikes(cursor.getInt(columns.get(4))); }
            if (columns.get(5) != -1) { newDungeon.setDislikes(cursor.getInt(columns.get(5))); }
        }


        selectStatement = "SELECT * FROM "+dungeon_layout_table_name+" WHERE dungeonId = '"+id+"';";
        cursor = db.rawQuery(selectStatement,null);
        columns = new ArrayList<>();

        Log.d("FUCKCCCC",selectStatement+"     "+cursor.getCount());

        if (cursor.moveToFirst()) {

            Log.d("HEY!!!!",cursor.getString(0));
            columns.add(cursor.getColumnIndex("theme"));
            columns.add(cursor.getColumnIndex("tiles"));

            Log.d("THE THEEMEE",""+cursor.getInt(columns.get(0)));
            Log.d("THE TILES",""+cursor.getInt(columns.get(1)));

            if (columns.get(0) != -1) { newDungeon.setTheme(cursor.getInt(columns.get(0))); }
            if (columns.get(1) != -1) { newDungeon.setTiles(GridDecompiler(cursor.getString(columns.get(1)))); }

        }



        selectStatement = "SELECT * FROM "+dungeon_entities_table_name+" WHERE dungeonId = '"+id+"';";
        cursor = db.rawQuery(selectStatement,null);
        columns = new ArrayList<>();

        if (cursor.moveToFirst()) {

            columns.add(cursor.getColumnIndex("items"));
            columns.add(cursor.getColumnIndex("enemies"));
            columns.add(cursor.getColumnIndex("objects"));

            if (columns.get(0) != -1)  { newDungeon.setItems(GridDecompiler(cursor.getString(columns.get(0)))); }
            if (columns.get(1) != -1) { newDungeon.setEnemies(GridDecompiler(cursor.getString(columns.get(1)))); }
            if (columns.get(2) != -1) { newDungeon.setObjects(GridDecompiler(cursor.getString(columns.get(2)))); }

        }

        cursor.close();
        db.close();



        return newDungeon;
    }

    public ArrayList<Integer> getDungeonsByOwnerId(int userId) {
        ArrayList<Integer> Dungeons = new ArrayList<>();
        String selectStatement = "SELECT dungeonId FROM " + dungeon_header_table_name + " WHERE userId = '" + userId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            do {
                Dungeons.add(cursor.getInt(0));
            }
            while (cursor.moveToNext());
        }
        db.close();

        return Dungeons;
    }

    public boolean isCurrentDungeonFavorited() {
        int dungeonId = DungeonData.getCurrentDungeon().getDungeonId();
        boolean isFavorited = false;
        String hex = IDToHex(dungeonId);

        String selectStatement = "SELECT favorites FROM " + users_table_name + " WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            isFavorited = isHexInFavorites(cursor.getString(0),hex);
        }
        db.close();

        return isFavorited;
    }


    private int hexToID(String hex) {
        int id = 0;
        int exp_counter = 0;
        for (int i = hex.length()-1; i >= 0;i--) {
            for (int q = 0; q < hexChart.length;q++) {
                if (hexChart[q].charAt(0) == hex.charAt(i)) {
                    id += q*(int)Math.pow(16,exp_counter);
                }
            }
            exp_counter++;
        }
        return id;
    }
    private String IDToHex(int id) {
        String hex = "";

        while (id > 0) {
            hex = hexChart[id%16] + hex;
            id/=16;
        }
        while (hex.length() < 4) { hex = "0"+hex; }
        return hex;
    }

    private boolean isHexInFavorites(String favorites, String hex) {

        if (favorites.isEmpty() || hex.isEmpty()) { return false; }

        for (int i = 0; i < favorites.length();i+=4) {
            if (favorites.substring(i,i+4).equals(hex)) {
                return true;
            }
        }


        return false;
    }



    public void favoriteCurrentDungeon(boolean fav) {
        // If fav = true, favorite this dungeon,
        // else unfavorite this dungeon.

        if (fav) {
            favdungeon();
        }
        else {
            unfavdungeon();
        }
    }

    private void favdungeon() {

        String newFav = "";
        if (!isCurrentDungeonFavorited()) {

            String selectStatement = "SELECT favorites FROM " + users_table_name + " WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectStatement,null);

            if (cursor.moveToFirst()) {
                newFav = cursor.getString(0);
                newFav += IDToHex(DungeonData.getCurrentDungeon().getDungeonId());
            }
            db.close();

            if (!newFav.isEmpty()) {
                db = this.getWritableDatabase();
                db.execSQL("UPDATE " + users_table_name + " SET favorites = '" + newFav + "' WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';");
            }
            db.close();

        }
    }



    private void unfavdungeon() {
        String fav = IDToHex(DungeonData.getCurrentDungeon().getDungeonId());
        String favorites = "";
        Boolean updatedfavorites = false;
        String selectStatement = "SELECT favorites FROM " + users_table_name + " WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            favorites = cursor.getString(0);
            for (int i = 0; i < favorites.length();i+=4) {
                if (favorites.substring(i,i+4).equals(fav)) {
                    favorites = favorites.substring(0,i) + favorites.substring(i+4);
                    updatedfavorites = true;
                    break;
                }
            }
        }
        cursor.close();
        db.close();

        if (updatedfavorites) {
            db = this.getWritableDatabase();
            db.execSQL("UPDATE " + users_table_name + " SET favorites = '" + favorites + "' WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';");
            db.close();
        }

    }



    public ArrayList<Integer> GetFavorites() {
        ArrayList<Integer> dungeons = new ArrayList<>();
        String favorites = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectStatement = "SELECT favorites FROM "+users_table_name+" WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";

        Cursor cursor = db.rawQuery(selectStatement,null);
        if (cursor.moveToFirst()) {
            favorites = cursor.getString(0);
        }
        cursor.close();
        db.close();

        for (int i = 0; i < favorites.length();i+=4) {
            dungeons.add(hexToID(favorites.substring(i,i+4)));
        }

        return dungeons;
    }








    public void saveNewDungeon() {

        Dungeon d = DungeonData.getCurrentDungeon();
        String items = GridCompiler(d.getItems());
        String enemies = GridCompiler(d.getEnemies());
        String objects = GridCompiler(d.getObjects());
        String tiles = GridCompiler(d.getTiles());


        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + dungeon_header_table_name + " (userId, name, categoryId, views, likes, dislikes) VALUES ('"+d.getOwnerId()+"','"+d.getName()+"','"+d.getCategory()+"','0','0','0');");
        db.execSQL("INSERT INTO " + dungeon_layout_table_name + " (theme,tiles) VALUES ('"+d.getTheme()+"','"+tiles+"');");
        db.execSQL("INSERT INTO " + dungeon_entities_table_name + " (items, enemies, objects) VALUES ('"+items+"','"+enemies+"','"+objects+"');");
        db.close();
    }




    public void saveDungeon() {

        Dungeon d = DungeonData.getCurrentDungeon();
        String str_tiles = GridCompiler(d.getTiles());
        String str_items = GridCompiler(d.getItems());
        String str_objects = GridCompiler(d.getObjects());
        String str_enemies = GridCompiler(d.getEnemies());


        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("UPDATE " + dungeon_header_table_name + " SET name = '"+d.getName()+"',categoryId = '"+d.getCategory()+"',views = '"+d.getViews()+"',likes = '"+d.getLikes()+"' WHERE dungeonId = "+d.getDungeonId()+";");
        db.execSQL("UPDATE " + dungeon_layout_table_name + " SET theme = '"+d.getTheme()+"', tiles = '"+str_tiles+"' WHERE dungeonId = "+d.getDungeonId()+";");
        db.execSQL("UPDATE " + dungeon_entities_table_name + " SET items = '"+str_items+"', enemies = '"+str_enemies+"', objects = '"+str_objects+"' WHERE dungeonId = "+d.getDungeonId()+";");

        db.close();

        debug__getTheme();

        //db = this.getReadableDatabase();
        //String selectStatement = "SELECT tiles FROM " + dungeon_layout_table_name + " WHERE EXISTS (SELECT dungeonId FROM "+dungeon_header_table_name+" WHERE dungeonID = '"+d.getDungeonId()+"');";
        //Cursor cursor = db.rawQuery(selectStatement,null);
        //if (cursor.moveToFirst()) { Log.d("IT WORKS","Length: "+cursor.getString(0).length()+" "+cursor.getString(0)); }
        //db.close();


    }


    public void deleteDungeon(int dungeonId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + dungeon_header_table_name + " WHERE dungeonId = "+dungeonId+";");
        db.execSQL("DELETE FROM " + dungeon_layout_table_name + " WHERE dungeonID = "+dungeonId+";");
        db.execSQL("DELETE FROM " + dungeon_entities_table_name + " WHERE dungeonID = "+dungeonId+";");
        db.close();

    }






    public String GridCompiler(int[][] grid) {
        String dbGrid = "";

        for (int i = 0; i < grid.length;i++) {
            for (int q = 0; q < grid[i].length;q++) {
                dbGrid += (char)(grid[i][q]+32);
            }
        }

        return dbGrid;
    }

    public int[][] GridDecompiler(String tiles) {

        int[][] grid = new int[DungeonData.getDungeonSize()][DungeonData.getDungeonSize()];
        int rowX = 0;
        int columnY = 0;



        if (tiles.isEmpty()) { return grid; }

        for (int i = 0; i < tiles.length();i++)  {
            grid[columnY][rowX] = tiles.charAt(i)-32;
            rowX++;
            if (rowX >= grid[columnY].length) {
                rowX = 0;
                columnY++;
            }
            if (columnY >= grid.length) { return grid; }
        }
        return grid;
    }




    public void giveView() {
        int dungeonId = DungeonData.getCurrentDungeon().getDungeonId();
        int oldViews = DungeonData.getCurrentDungeon().getViews();
        SQLiteDatabase db = this.getWritableDatabase();
        oldViews++;
        db.execSQL("UPDATE " + dungeon_header_table_name + " SET views = '"+oldViews+"' WHERE dungeonId = '"+dungeonId+"';");
        db.close();

    }

    public void likeDungeon(boolean IsLiking) {
        int dungeonId = DungeonData.getCurrentDungeon().getDungeonId();
        int like = DungeonData.getCurrentDungeon().getLikes();
        String likes = "likes";
        if (!IsLiking) {
            likes = "dislikes";
            like = DungeonData.getCurrentDungeon().getDislikes();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        like++;
        db.execSQL("UPDATE " + dungeon_header_table_name + " SET "+likes+" = '"+like+"' WHERE dungeonId = '"+dungeonId+"';");
        db.close();
    }









    public ArrayList<Integer> Search(Dungeon d) {
        ArrayList<Integer> dungeons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectStatement = "SELECT "+dungeon_header_table_name+".dungeonId FROM "+dungeon_header_table_name;

        selectStatement += " FULL JOIN "+dungeon_layout_table_name+" ON "+dungeon_layout_table_name+".dungeonId = "+dungeon_header_table_name+".dungeonId WHERE";



        if (d.getName().isEmpty()) {
            selectStatement += " "+dungeon_header_table_name+".name is not null AND";
        }
        else {
            selectStatement += " "+dungeon_header_table_name+".name = '" + d.getName() + "' AND";
        }

        if (d.getCategory() == -1) {
            selectStatement += " "+dungeon_header_table_name+".categoryId is not null AND";
        }
        else {
            selectStatement += " "+dungeon_header_table_name+".categoryId = '" + d.getCategory() + "' AND";
        }

        if (d.getOwnerId() == -1) {
            selectStatement += " "+dungeon_header_table_name+".userId is not null;";
        }
        else {
            selectStatement += " "+dungeon_header_table_name+".userId = '" + d.getOwnerId() + "';";
        }




        Log.d("selectStatement",selectStatement);

        Cursor cursor = db.rawQuery(selectStatement,null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("Hey","Hey");
                dungeons.add(cursor.getInt(0));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return dungeons;
    }
















    public void debug__printDatabase(String database_name) {
        String selectStatement = "SELECT * FROM " + database_name + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("",cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2));
            }
            while(cursor.moveToNext());
        }


        db.close();
    }


    private void debug__getDungeonNameFromId(int id) {
        String selectStatement = "SELECT name FROM " + dungeon_header_table_name + " WHERE dungeonId = '"+id+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("DungeodIDDDD",cursor.getString(0));
            }
            while(cursor.moveToNext());
        }
        db.close();
    }

    private void debug__printGrid(int[][] grid) {
        String line = "";
        for (int i = 0; i < grid.length;i++) {
            for (int q = 0; q < grid[i].length;q++) {
                line += grid[i][q]+" ";
            }
            Log.d("Grid",line);
            line = "";
        }
    }


    public void debug__showFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectStatement = "SELECT favorites FROM "+users_table_name+" WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";

        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            Log.d("FAVORITES","Favs:"+cursor.getString(0));
        }
        cursor.close();
        db.close();
    }

    public void debug__getTheme() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectStatement = "SELECT theme FROM "+dungeon_layout_table_name+";";

        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("THEMES","Themes:"+cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }


}
