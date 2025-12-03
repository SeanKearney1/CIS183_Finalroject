package com.example.dungeoncreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewUsernames extends BaseAdapter {

    Context context;
    ArrayList<Integer> userIds;
    DatabaseHelper db;

    public ListViewUsernames(Context c, ArrayList<Integer> u) {
        context = c;
        userIds = u;
        db = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return userIds.size();
    }

    @Override
    public Object getItem(int position) {
        return userIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.username_cell,null);
        }

        TextView Username = view.findViewById(R.id.tv_usernameCell_username);

        Username.setText(db.getUsernameById(userIds.get(position)));



        return view;
    }
}
