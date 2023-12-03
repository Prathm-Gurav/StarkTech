package com.example.techspark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class tokengeneration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokengeneration);
    }

    public void startpicactivity(View view) {
        Intent i = new Intent(this, startimage.class);
        startActivity(i);
    }
}