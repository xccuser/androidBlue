package com.example.administrator.bluetooth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainView extends AppCompatActivity {

    ImageView im,im2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        im= (ImageView) findViewById(R.id.imageView2);
        im2= (ImageView) findViewById(R.id.imageView);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainView.this,MainActivity.class);
                startActivity(i);
            }
        });

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainView.this,ShowView.class);
                startActivity(i);
            }
        });


    }





}
