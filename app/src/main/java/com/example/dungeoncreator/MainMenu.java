package com.example.dungeoncreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainMenu extends AppCompatActivity {


    Button btn_search;
    Button btn_createDungeon;
    Button btn_myDungeons;
    Button btn_back;
    TextView tv_menu_welcomeMessage;

    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_search = findViewById(R.id.btn_menu_search);
        btn_createDungeon = findViewById(R.id.btn_menu_createDungeon);
        btn_myDungeons = findViewById(R.id.btn_menu_myDungeons);
        btn_back = findViewById(R.id.btn_menu_back);
        tv_menu_welcomeMessage = findViewById(R.id.tv_menu_welcomeMessage);
        main = findViewById(R.id.main);
        main.getBackground().setFilterBitmap(false);

        OnClickListeners();

        tv_menu_welcomeMessage.setText("Welcome, "+SessionData.getLoggedInUser().getUsername()+"!");



    }


    private void OnClickListeners() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,DungeonSearch.class));
            }
        });
        btn_createDungeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,CreateDungeonHeader.class));
            }
        });
        btn_myDungeons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,YourDungeons.class));
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,MainActivity.class));
            }
        });
    }




}