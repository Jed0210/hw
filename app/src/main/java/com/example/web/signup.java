package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class signup extends AppCompatActivity implements View.OnClickListener {
    ImageView identifyingCode;
    String realCode;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        identifyingCode=(ImageView)findViewById(R.id.identifyingcode_image);
        identifyingCode.setOnClickListener(this);
        identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
        realCode=IdentifyingCode.getInstance().getCode().toLowerCase();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        final String[] lunch = {"台灣", "日本", "韓國", "美國", "馬來西亞", "香港"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(signup.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner.setAdapter(lunchList);


        Button nextPageBtn = (Button) findViewById(R.id.signup);
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, Login.class);

                startActivity(intent);


            }
        });
    }

    @Override
    public void onClick(View view) {
        identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
        realCode=IdentifyingCode.getInstance().getCode().toLowerCase();
    }
}

























