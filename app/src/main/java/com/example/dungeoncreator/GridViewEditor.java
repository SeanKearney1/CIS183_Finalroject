package com.example.dungeoncreator;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.Array;

public class GridViewEditor extends BaseAdapter {

    Context context;
    Dungeon dungeon;
    int gridSize;
    int viewLevel = 0; // 0-2, used to decide if enemy/object/item has render priority.
    int pixelWidth = 409;

    public GridViewEditor(Context c, Dungeon d, int gs) {
        context = c;
        dungeon = d;
        gridSize = gs;
    }




    @Override
    public int getCount() {
        return (int)Math.pow(DungeonData.getDungeonSize(),2);
    }

    @Override
    public Object getItem(int position) {
        return dungeon;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(DungeonEditor.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.grid_cell,null);
        }
        int[] tiles = DungeonData.getDungeonTiles(DungeonData.getCurrentDungeon().getCategory());
        int[] items = DungeonData.getDungeonItems(DungeonData.getCurrentDungeon().getCategory());
        int[] objects = DungeonData.getDungeonObjects(DungeonData.getCurrentDungeon().getCategory());
        int[] enemies = DungeonData.getDungeonEnemies(DungeonData.getCurrentDungeon().getCategory());
        int posY = position/dungeon.getTiles().length;
        int posX = position%dungeon.getTiles()[0].length;



        ImageView imageview = view.findViewById(R.id.iv_gridCell_image);
        imageview.setBackgroundResource(tiles[dungeon.getTiles()[posY][posX]]);
        imageview.getBackground().setFilterBitmap(false);




        if (dungeon.getEnemies()[posY][posX] != 0) {
            imageview.setImageResource(enemies[dungeon.getEnemies()[posY][posX]]);
            imageview.getDrawable().setFilterBitmap(false);
        }
        else if (dungeon.getItems()[posY][posX] != 0) {
            imageview.setImageResource(items[dungeon.getItems()[posY][posX]]);
            imageview.getDrawable().setFilterBitmap(false);
        }
        else if (dungeon.getObjects()[posY][posX] != 0) {
            imageview.setImageResource(objects[dungeon.getObjects()[posY][posX]]);
            imageview.getDrawable().setFilterBitmap(false);
        }


        return view;
    }
}
