package com.example.mymedicine;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureGoToSearchBtn();
        configureGoToProfileBtn();

        //deleteFile("userProfile");
        //deleteFile("userHistory");
    }

    private void configureGoToSearchBtn(){
        Button goToSearchBtn = findViewById(R.id.goToSearchBtn);
        goToSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                startActivity(new Intent(MainActivity.this, SymptomSearchActivity.class));
            }
        });
    }
    private void configureGoToProfileBtn(){
        Button goToProfileBtn = findViewById(R.id.goToProfileBtn);
        goToProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                startActivity(new Intent(MainActivity.this, ProfileHistoryActivity.class));
            }
        });
    }

}
