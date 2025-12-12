package com.example.dungeoncreator;

import android.content.Intent;
import android.os.Bundle;
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

public class FavoriteDungeons extends AppCompatActivity {

    ListViewFavorites LVAdapter;
    DatabaseHelper db;
    Button btn_back;

    ListView lv_dungeons;
    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite_dungeons);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DatabaseHelper(this);

        db.debug__showFavorites();

        btn_back = findViewById(R.id.btn_favorites_back);
        lv_dungeons = findViewById(R.id.lv_favorites_dungeons);
        main = findViewById(R.id.main);
        main.getBackground().setFilterBitmap(false);
        OnClickListeners();

        ArrayList<Integer> dungeons = db.GetFavorites();
        LVAdapter = new ListViewFavorites(this, dungeons);
        lv_dungeons.setAdapter(LVAdapter);

    }



    private void OnClickListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteDungeons.this,YourDungeons.class));
            }
        });
        lv_dungeons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Dungeon dungeon = db.getDungeonById((int)lv_dungeons.getAdapter().getItem(position));
                DungeonData.setCurrentDungeon(dungeon);

                // DEBUG
                //startActivity(new Intent(FavoriteDungeons.this, DungeonViewer.class));


                if (SessionData.getLoggedInUser().getId() == DungeonData.getCurrentDungeon().getOwnerId()) {
                    startActivity(new Intent(FavoriteDungeons.this, CreateDungeonHeader.class));
                }
                else {
                    startActivity(new Intent(FavoriteDungeons.this, DungeonViewer.class));
                }
            }
        });
    }
}