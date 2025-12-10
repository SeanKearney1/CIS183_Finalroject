package com.example.dungeoncreator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DungeonEditor extends AppCompatActivity {

    DatabaseHelper db;
    Button btn_zoomIn;
    Button btn_zoomOut;
    Button btn_back;
    GridView gv_grid;


    Button btn_moveUp;
    Button btn_moveDown;
    Button btn_moveLeft;
    Button btn_moveRight;

    GridViewEditor GVAdapter;
    int zoomMax = 6;
    int zoomLevel = 1;

    int directionMax = 409;
    int directionSize = 25;
    int directionX = 0;
    int directionY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dungeon_editor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        btn_zoomIn = findViewById(R.id.btn_duengeonEditor_zoomIn);
        btn_zoomOut = findViewById(R.id.btn_duengeonEditor_zoomOut);
        btn_back = findViewById(R.id.btn_dungeonEditor_back);
        gv_grid = findViewById(R.id.gv_dungeonEditor_grid);

        btn_moveUp = findViewById(R.id.btn_duengeonEditor_moveUp);
        btn_moveDown = findViewById(R.id.btn_duengeonEditor_moveDown);
        btn_moveLeft = findViewById(R.id.btn_duengeonEditor_moveLeft);
        btn_moveRight = findViewById(R.id.btn_duengeonEditor_moveRight);

        OnClickListeners();
        fillTiles();
    }

    private void OnClickListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DungeonData.setCurrentDungeon(null);
                startActivity(new Intent(DungeonEditor.this,CreateDungeonHeader.class));
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

        Dungeon dungeon = new Dungeon();
        //String baseTiles = "";
        if (SessionData.getCurrentDungeonId() != -1) {
            //baseTiles = db.getTilesByDungeonId(SessionData.getCurrentDungeonId());
            dungeon = db.getDungeonById(SessionData.getCurrentDungeonId());
        }

        //int[][] grid = tileDecompiler(new int[gridSize][gridSize],baseTiles);
        //debug__printGrid(grid);


        gv_grid.setNumColumns(DungeonData.getDungeonSize());
        gv_grid.setColumnWidth(36);

        gv_grid.setScaleX(zoomLevel);
        gv_grid.setScaleY(zoomLevel);

        GVAdapter = new GridViewEditor(this,dungeon.getTiles(), (int)Math.pow(2,zoomLevel));
        gv_grid.setAdapter(GVAdapter);


        gv_grid.setPadding(directionX,directionY,0,0);

    }

    private int[][] tileDecompiler(int[][] grid, String tiles) {

        int rowX = 0;
        int columnY = 0;

        while (!tiles.isEmpty()) {

            grid[columnY][rowX] = tiles.charAt(0)-32;
            tiles = tiles.substring(1);
            rowX++;
            if (rowX > grid.length) {
                rowX = 0;
                columnY++;
            }
        }

        return grid;
    }









    private void debug__printGrid(int[][] grid) {
        String gridLine = "";
        for (int i = 0; i < grid.length;i++) {
            for (int q = 0; q < grid[i].length;q++) {
                gridLine += " "+grid[i][q];
                //gridLine += " "+q;
            }
            Log.d("Grid "+i,gridLine);
            gridLine = "";
        }
    }
}