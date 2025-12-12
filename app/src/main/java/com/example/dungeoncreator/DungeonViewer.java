package com.example.dungeoncreator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DungeonViewer extends AppCompatActivity {

    Dungeon dungeon = DungeonData.getCurrentDungeon();
    DatabaseHelper db;
    Button btn_back;
    Button btn_favorite;
    Button btn_like;
    Button btn_dislike;
    Button btn_zoomIn;
    Button btn_zoomOut;
    Button btn_moveUp;
    Button btn_moveDown;
    Button btn_moveLeft;
    Button btn_moveRight;
    EditText et_name;
    EditText et_creator;
    EditText et_theme;
    EditText et_category;
    TextView tv_views;
    TextView tv_likes;
    TextView tv_dislikes;
    GridViewEditor GVAdapter;
    GridView gv_grid;

    Boolean IsDungeonFavorited = false;
    int zoomMax = 6;
    int zoomLevel = 1;
    int directionMax = 450; // 409
    int directionSize = 25;
    int directionX = 25;
    int directionY = 25;
    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dungeon_viewer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DatabaseHelper(this);

        IsDungeonFavorited = db.isCurrentDungeonFavorited();
        db.giveView();

        btn_back = findViewById(R.id.btn_viewer_back);
        btn_favorite = findViewById(R.id.btn_viewer_favorite);
        btn_like = findViewById(R.id.btn_viewer_like);
        btn_dislike = findViewById(R.id.btn_viewer_dislike);

        et_name = findViewById(R.id.et_viewer_name);
        et_creator = findViewById(R.id.et_viewer_creator);
        et_theme = findViewById(R.id.et_viewer_theme);
        et_category = findViewById(R.id.et_viewer_category);
        tv_views = findViewById(R.id.tv_viewer_views_title);
        tv_likes = findViewById(R.id.tv_viewer_likes_title);
        tv_dislikes = findViewById(R.id.tv_viewer_dislikes_title);

        gv_grid = findViewById(R.id.gv_viewer_grid);

        btn_zoomIn = findViewById(R.id.btn_viewer_zoomIn);
        btn_zoomOut = findViewById(R.id.btn_viewer_zoomOut);
        btn_moveUp = findViewById(R.id.btn_viewer_moveUp);
        btn_moveDown = findViewById(R.id.btn_viewer_moveDown);
        btn_moveLeft = findViewById(R.id.btn_viewer_moveLeft);
        btn_moveRight = findViewById(R.id.btn_viewer_moveRight);
        main = findViewById(R.id.main);
        main.getBackground().setFilterBitmap(false);
        fillInfo();
        fillTiles();
        setOnClickListeners();
        updateFavoriteButton();
    }



    private void setOnClickListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DungeonViewer.this,MainMenu.class));
            }
        });
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteDungeon();
                updateFavoriteButton();
            }
        });
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("reddd","Hi");
                db.likeDungeon(true);
                btn_like.setEnabled(false);
                btn_dislike.setEnabled(false);
            }
        });
        btn_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.likeDungeon(false);
                btn_like.setEnabled(false);
                btn_dislike.setEnabled(false);
            }
        });
        btn_zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Zoom(true);
                fillTiles();
            }
        });
        btn_zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Zoom(false);
                fillTiles();
            }
        });

        btn_moveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Move(false,1);
            }
        });
        btn_moveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Move(false,-1);
            }
        });
        btn_moveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Move(true,1);
            }
        });
        btn_moveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Move(true,-1);
            }
        });
    }

    private void fillInfo() {
        Dungeon dungeon = DungeonData.getCurrentDungeon();
        et_name.setText(dungeon.getName());
        et_category.setText(DungeonData.getCategories()[dungeon.getCategory()]);
        et_theme.setText(DungeonData.getThemes()[dungeon.getTheme()]);
        et_creator.setText(db.getUsernameById(dungeon.getOwnerId()));
        tv_likes.setText("Likes: "+dungeon.getLikes());
        tv_dislikes.setText("Dislikes: "+dungeon.getDislikes());
        tv_views.setText("Views: "+(dungeon.getViews()+1));
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








    private void Move(boolean IsX, int direction) {
        //-1 is Down and Right.
        if (IsX) {
            directionX += directionSize*direction;
            if (directionX > directionMax) { directionX = directionMax; }
        }
        else {
            directionY += directionSize*direction;
            if (directionY > directionMax) { directionY = directionMax; }
        }
        fillTiles();
    }
    private void Zoom(boolean IsZoomIn) {
        // this was switched because how the math works,
        // so 5(max) is fully zoomed out, and 1 is fully zoomed in.
        if (!IsZoomIn) {
            if (zoomLevel > 1) {
                zoomLevel--;
            }
        }
        else {
            if (zoomLevel < zoomMax) {
                zoomLevel++;
            }
        }
    }






    private void fillTiles() {




        gv_grid.setNumColumns(DungeonData.getDungeonSize());
        gv_grid.setColumnWidth(36);

        gv_grid.setScaleX(zoomLevel);
        gv_grid.setScaleY(zoomLevel);

        GVAdapter = new GridViewEditor(this, dungeon, (int)Math.pow(2,zoomLevel));
        gv_grid.setAdapter(GVAdapter);


        gv_grid.setPadding(directionX,directionY,0,0);

    }
}