package com.example.dungeoncreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAccount extends AppCompatActivity {

    DatabaseHelper db;
    Button btn_back;
    Button btn_makeAccount;

    TextView tv_error1;
    EditText et_username;
    EditText et_fname;
    EditText et_lname;
    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DatabaseHelper(this);

        btn_back = findViewById(R.id.btn_createAccount_back);
        btn_makeAccount = findViewById(R.id.btn_createAccount_makeAccount);
        tv_error1 = findViewById(R.id.tv_createAccount_error1);
        et_username = findViewById(R.id.et_createAccount_name);
        et_fname = findViewById(R.id.et_createAccount_fname);
        et_lname = findViewById(R.id.et_createAccount_lname);
        main = findViewById(R.id.main);
        main.getBackground().setFilterBitmap(false);

        setOnClickListeners();
    }



    private void setOnClickListeners() {
        btn_makeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateAccount()) {
                    AddAccount();
                    startActivity(new Intent(CreateAccount.this, MainActivity.class));
                }
                else {
                    tv_error1.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this,MainActivity.class));
            }
        });
    }

    private boolean ValidateAccount() {
        if (et_username.getText().toString().isEmpty()) { return false; }
        if (et_fname.getText().toString().isEmpty())    { return false; }
        if (et_lname.getText().toString().isEmpty())    { return false; }
        return true;
    }

    private void AddAccount() {
        User user = new User();
        user.setUsername(et_username.getText().toString());
        user.setFname(et_fname.getText().toString());
        user.setLname(et_lname.getText().toString());
        db.addUserToDB(user);
    }






}