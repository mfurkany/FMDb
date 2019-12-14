package com.furkan.fmdbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private Button titleButton;
    private Button keywordButton;
    private Button imdbIdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        titleButton=findViewById(R.id.titleButton);
        keywordButton=findViewById(R.id.keywordButton);
        imdbIdButton=findViewById(R.id.imdbIdButton);

        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        keywordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this,"coming soon",Toast.LENGTH_SHORT).show();
            }
        });

        imdbIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImdbIdActivity();
            }
        });

    }

    public void openMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openImdbIdActivity(){
        Intent intent=new Intent(this,ImdbIdActivity.class);
        startActivity(intent);
    }
}
