package com.example.dungeoncreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DungeonSearch extends AppCompatActivity {

    ListViewSearch LVAdapter;
    DatabaseHelper db;
    Button btn_back;
    Button btn_search;
    ListView lv_search_dungeons;
    EditText et_name;
    EditText et_creator;
    Spinner sp_category;
    ConstraintLayout main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dungeon_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        btn_back = findViewById(R.id.btn_search_back);
        btn_search = findViewById(R.id.btn_search_search);
        lv_search_dungeons = findViewById(R.id.lv_search_dungeons);

        et_name = findViewById(R.id.et_search_name);
        et_creator = findViewById(R.id.et_search_creator);

        sp_category = findViewById(R.id.sp_search_category);
        main = findViewById(R.id.main);
        main.getBackground().setFilterBitmap(false);
        OnClickListeners();
        fillSpinner();
    }

    private void OnClickListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DungeonSearch.this,MainMenu.class));
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Search();
            }
        });
        lv_search_dungeons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Dungeon dungeon = db.getDungeonById((int)lv_search_dungeons.getAdapter().getItem(position));
                DungeonData.setCurrentDungeon(dungeon);

                if (SessionData.getLoggedInUser().getId() == DungeonData.getCurrentDungeon().getOwnerId()) {
                    startActivity(new Intent(DungeonSearch.this, CreateDungeonHeader.class));
                }
                else {
                    startActivity(new Intent(DungeonSearch.this, DungeonViewer.class));
                }

            }
        });
    }

    private void fillSpinner() {

        ArrayList<String> Categories = new ArrayList<>();

        Categories.add("- None -");

        for (int i = 0; i < DungeonData.getCategories().length;i++) { Categories.add(DungeonData.getCategories()[i]); }


        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,Categories);
        sp_category.setAdapter(spinnerAdapter1);

    }


    private void Search() {
        Dungeon dungeon = new Dungeon();

        dungeon.setName(et_name.getText().toString());
        dungeon.setOwnerId(db.getIdByUsername(et_creator.getText().toString()));
        dungeon.setCategory(sp_category.getSelectedItemPosition()-1);

        ArrayList<Integer> dungeonIds = db.Search(dungeon);
        LVAdapter = new ListViewSearch(this, dungeonIds);
        lv_search_dungeons.setAdapter(LVAdapter);
    }

}