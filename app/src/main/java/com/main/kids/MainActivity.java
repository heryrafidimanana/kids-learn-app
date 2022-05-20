package com.main.kids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = (TextView) findViewById(R.id.txtUsername);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Token", Context.MODE_PRIVATE);
        String x_access_token = sp.getString("x-access-token", "");

        Bundle extras = getIntent().getExtras();
        String username;

        if(extras != null){
            username = extras.getString("username");
//            txtUsername.setText("Welcome " + username);
            txtUsername.setText("Welcome " + x_access_token);

        }
    }
}