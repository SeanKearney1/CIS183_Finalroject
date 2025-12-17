package com.example.dungeoncreator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class YourDungeons extends AppCompatActivity {

    DatabaseHelper db;
    ListViewYourDungeons LVAdapter;
    Button btn_back;
    Button btn_favorites;
    ConstraintLayout main;
    ListView lv_dungeons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_your_dungeons);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DatabaseHelper(this);

        btn_back = findViewById(R.id.btn_myDungeons_back);
        btn_favorites = findViewById(R.id.btn_myDungeons_favorites);
        lv_dungeons = findViewById(R.id.lv_favorites_dungeons);
        main = findViewById(R.id.main);
        main.getBackground().setFilterBitmap(false);
        OnClickListeners();

        updateList();
    }

    private void OnClickListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YourDungeons.this,MainMenu.class));
            }
        });
        btn_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YourDungeons.this,FavoriteDungeons.class));
            }
        });
        lv_dungeons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("FUCK",""+(int)lv_dungeons.getAdapter().getItem(position));
                Dungeon dungeon = db.getDungeonById((int)lv_dungeons.getAdapter().getItem(position));
                DungeonData.setCurrentDungeon(dungeon);

                if (SessionData.getLoggedInUser().getId() == DungeonData.getCurrentDungeon().getOwnerId()) {
                    startActivity(new Intent(YourDungeons.this, CreateDungeonHeader.class));
                }
                else {
                    startActivity(new Intent(YourDungeons.this, DungeonViewer.class));
                }

            }
        });
        lv_dungeons.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Dungeon dungeon = db.getDungeonById((int)lv_dungeons.getAdapter().getItem(position));
                db.deleteDungeon(dungeon.getDungeonId());
                updateList();
                return false;
            }
        });

    }

    private void updateList() {
        ArrayList<Integer> dungeons = db.getDungeonsByOwnerId(SessionData.getLoggedInUser().getId());
        LVAdapter = new ListViewYourDungeons(this, dungeons);
        lv_dungeons.setAdapter(LVAdapter);
    }
}