package com.example.dungeoncreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView lv_main_usernames;

    ListViewUsernames LVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv_main_usernames = findViewById(R.id.lv_main_usernames);

        dbHelper = new DatabaseHelper(this);

        //initialize all of the tables with dummy data
        //there is logic in this function to ensure this is not done more than once.
        dbHelper.initAllTables();

        dbHelper.debug__printDatabase(dbHelper.getUserTableName());

        OnClickListeners();

        FillUsernames();

    }


    private void OnClickListeners() {
        lv_main_usernames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user = dbHelper.getUserById((Integer)lv_main_usernames.getAdapter().getItem(position));
                SessionData.setLoggedInUser(user);
                startActivity(new Intent(MainActivity.this,MainMenu.class));
            }
        });
    }

    private void FillUsernames() {
        ArrayList<Integer> userIds = new ArrayList<>();

        userIds = dbHelper.getUserIds();

        LVAdapter = new ListViewUsernames(this,userIds);
        lv_main_usernames.setAdapter(LVAdapter);
    }
}