package com.example.miyu1.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private TextView name;
    private TextView level;
    private TextView Curr_exp;
    private TextView Needed_exp;
    private String postName;
    private String postLevel;
    private String postCurrExp;
    private String postNeededExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent prevIntent = getIntent();

        name = findViewById(R.id.UNValue);
        level = findViewById(R.id.levelValue);
        Curr_exp = findViewById(R.id.CurrExpValue);
        Needed_exp = findViewById(R.id.NeededExpValue);

        //set strings to get post data
        postName = prevIntent.getStringExtra("username");
        postLevel = prevIntent.getStringExtra("level");
        postCurrExp = prevIntent.getStringExtra("current_exp");
        postNeededExp = prevIntent.getStringExtra("needed_exp");

        //set post data to text view
        name.setText(postName);
        level.setText(postLevel);
        Curr_exp.setText(postCurrExp);
        Needed_exp.setText(postNeededExp);
    }
}
