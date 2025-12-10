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

    public DatabaseHelper(Context c)
    {
        super(c, database_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + users_table_name + " (userId integer primary key autoincrement not null, username varchar(64), fname varchar(64), lname varchar(64), favorites varchar(4096));");
        db.execSQL("CREATE TABLE " + dungeon_header_table_name + " (dungeonId integer primary key autoincrement not null, userId varchar(64), name varchar(64), categoryId integer, views integer, likes integer, dislikes integer, foreign key (userId) references \" + users_table_name + \" (userId));");
        db.execSQL("CREATE TABLE " + dungeon_layout_table_name + " (dungeonId integer, size integer(3), theme integer(3), tiles varchar(4096), foreign key (dungeonId) references \" + dungeon_header_table_name + \" (dungeonId));");
        db.execSQL("CREATE TABLE " + dungeon_entities_table_name + " (dungeonId integer, items varchar(4096), enemies varchar(4096), objects varchar(4096), foreign key (dungeonId) references \" + dungeon_header_table_name + \" (dungeonId));");
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

    public Dungeon getDungeonById(int id) {
        Dungeon newDungeon = new Dungeon();
        String selectStatement = "SELECT * FROM " + dungeon_header_table_name + " JOIN " + dungeon_layout_table_name + " on 'dungeonId' = 'dungeonId' WHERE dungeonId = '" + id + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            Log.d("",cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2));
        }
        db.close();

        return newDungeon;
    }





















    public void debug__printDatabase(String database_name) {
        String selectStatement = "SELECT * FROM " + database_name + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("",cursor.getString(1));
            }
            while(cursor.moveToNext());
        }


        db.close();
    }

}
