package com.example.dungeoncreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewSearch extends BaseAdapter {

    Context context;
    ArrayList<Integer> dungeons;
    DatabaseHelper db;

    public ListViewSearch(Context c, ArrayList<Integer> u) {
        context = c;
        dungeons = u;
        db = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return dungeons.size();
    }

    @Override
    public Object getItem(int position) {
        return dungeons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(DungeonSearch.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.your_dungeons_cell,null);
        }

        TextView Name = view.findViewById(R.id.tv_ydcell_name);
        TextView Category = view.findViewById(R.id.tv_ydcell_category);
        TextView Likes = view.findViewById(R.id.tv_ydcell_likes);
        TextView Dislikes = view.findViewById(R.id.tv_ydcell_dislikes);
        TextView Views = view.findViewById(R.id.tv_ydcell_views);

        Dungeon dungeon = db.getDungeonById(dungeons.get(position));

        Name.setText(""+dungeon.getName());
        Category.setText(""+DungeonData.getCategories()[dungeon.getCategory()]);
        Likes.setText("Likes:      "+dungeon.getLikes());
        Dislikes.setText("Dislikes: "+dungeon.getDislikes());
        Views.setText("Views: "+dungeon.getViews());



        return view;
    }
}
