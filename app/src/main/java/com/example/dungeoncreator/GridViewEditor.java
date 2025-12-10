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

import java.lang.reflect.Array;

public class GridViewEditor extends BaseAdapter {

    Context context;
    int[][] grid;
    int gridSize;

    int pixelWidth = 409;

    public GridViewEditor(Context c, int[][] newGrid, int gs) {
        context = c;
        grid = newGrid;
        gridSize = gs;
    }



    @Override
    public int getCount() {
        return grid.length*grid[0].length;
    }

    @Override
    public Object getItem(int position) {
        return grid[position];
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

        ImageView imageview = view.findViewById(R.id.iv_gridCell_image);


        imageview.getDrawable().setFilterBitmap(false);

        //imageview.setImageResource(R.drawable.ic_launcher_background);

        return view;
    }
}
