package com.example.techspark;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
public class startimage extends AppCompatActivity {

    private final int CAMERA_REQ_CODE = 1;
    ImageView start_pic_img;
    Button start_pic_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startimage);

        start_pic_img = findViewById(R.id.start_pic_img); // Fix here
        start_pic_btn = findViewById(R.id.start_pic_btn);
        start_pic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, CAMERA_REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            if(requestCode==CAMERA_REQ_CODE){

                Bitmap img = (Bitmap) (data.getExtras().get("data"));
                start_pic_img.setImageBitmap(img);

                start_pic_btn.setText("Retake");


            }

        }
    }

    public void gotomainpage(View view) {
        Intent i = new Intent(this, mainpage.class);
        startActivity(i);
    }
}