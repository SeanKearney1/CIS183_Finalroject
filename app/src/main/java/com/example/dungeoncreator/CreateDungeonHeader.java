package com.example.dungeoncreator;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateDungeonHeader extends AppCompatActivity {

    DatabaseHelper db;
    Button btn_back;
    Button btn_save;
    Button btn_editor;

    EditText et_name;
    Spinner sp_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_dungeon_header);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        btn_back = findViewById(R.id.btn_createDungeonHeader_back);
        btn_save = findViewById(R.id.btn_createDungeonHeader_save);
        btn_editor = findViewById(R.id.btn_createDungeonHeader_editor);
        et_name = findViewById(R.id.et_createDungeonHeader_name);
        sp_category = findViewById(R.id.sp_createDungeonHeader_category);

        OnClickListeners();

        // Fill spinner.
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,DungeonData.getCategories());
        sp_category.setAdapter(spinnerAdapter);
    }


    private void OnClickListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateDungeonHeader.this,MainMenu.class));
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(CreateDungeonHeader.this,MainMenu.class));
            }
        });
        btn_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateDungeonHeader.this,DungeonEditor.class));
            }
        });
    }

}