package com.example.dungeoncreator;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateDungeonHeader extends AppCompatActivity {

    DatabaseHelper db;
    Button btn_back;
    Button btn_save;
    Button btn_favorite;
    Button btn_editor;
    TextView tv_error1;
    EditText et_name;


    Spinner sp_category;

    Spinner sp_theme;
    ConstraintLayout main;

    boolean IsDungeonFavorited = false;

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
        btn_favorite = findViewById(R.id.btn_createDungeonHeader_favorite);
        btn_editor = findViewById(R.id.btn_createDungeonHeader_editor);
        et_name = findViewById(R.id.et_createDungeonHeader_name);
        sp_category = findViewById(R.id.sp_createDungeonHeader_category);
        sp_theme = findViewById(R.id.sp_createDungeonHeader_theme);
        tv_error1 = findViewById(R.id.tv_createDungeonHeader_category_error1);
        main = findViewById(R.id.main);
        main.getBackground().setFilterBitmap(false);
        if (DungeonData.getCurrentDungeon() != null) {
            IsDungeonFavorited = db.isCurrentDungeonFavorited();
        }
        else { btn_favorite.setEnabled(false); }

        // Fill spinners.
        ArrayAdapter spinner1Adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,DungeonData.getCategories());
        ArrayAdapter spinner2Adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,DungeonData.getThemes());
        sp_category.setAdapter(spinner1Adapter);
        sp_theme.setAdapter(spinner2Adapter);


        OnClickListeners();
        updateFavoriteButton();
        fillInfo();
    }


    private void OnClickListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DungeonData.setCurrentDungeon(null);
                startActivity(new Intent(CreateDungeonHeader.this,MainMenu.class));
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!et_name.getText().toString().isEmpty()) {
                    saveDungeon();
                    DungeonData.setCurrentDungeon(null);
                    startActivity(new Intent(CreateDungeonHeader.this, MainMenu.class));
                }
                else {
                    tv_error1.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteDungeon();
                updateFavoriteButton();
            }
        });
        btn_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo();
                startActivity(new Intent(CreateDungeonHeader.this,DungeonEditor.class));
            }
        });
    }



    private void fillInfo() {
        if (DungeonData.getCurrentDungeon() != null) {
            et_name.setText(DungeonData.getCurrentDungeon().getName());
            sp_category.setSelection(DungeonData.getCurrentDungeon().getCategory());
            sp_theme.setSelection(DungeonData.getCurrentDungeon().getTheme());
        }
        else {
            DungeonData.setCurrentDungeon(new Dungeon());
        }
    }
    private void setInfo() {
        DungeonData.getCurrentDungeon().setName(et_name.getText().toString());
        DungeonData.getCurrentDungeon().setCategory(sp_category.getSelectedItemPosition());
    }



    private void saveDungeon() {

        DungeonData.getCurrentDungeon().setName(et_name.getText().toString());
        DungeonData.getCurrentDungeon().setCategory(sp_category.getSelectedItemPosition());
        DungeonData.getCurrentDungeon().setTheme(sp_theme.getSelectedItemPosition());

        DungeonData.getCurrentDungeon().setOwnerId(SessionData.getLoggedInUser().getId());
        // New dungeon
        if (DungeonData.getCurrentDungeon().getDungeonId() == -1) {
            db.saveNewDungeon();
        }
        // Saved dungeon
        else {
            db.saveDungeon();
        }
    }



    private void updateFavoriteButton() {
        if (IsDungeonFavorited) {
            btn_favorite.setText("UnFavorite");
            btn_favorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart,0,0,0);
        }
        else {
            btn_favorite.setText("Favorite");
            btn_favorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_hollow,0,0,0);
        }
    }
    private void favoriteDungeon() {

        db.favoriteCurrentDungeon(!IsDungeonFavorited);
        IsDungeonFavorited = !IsDungeonFavorited;
    }

}