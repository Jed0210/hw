package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

        Button nextPageBtn = findViewById(R.id.open);
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(QR.this, Scanner.class);
                startActivity(intent);
            }
        });

        Button nextPageBtn2 = findViewById(R.id.show);
        nextPageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(QR.this, showQR.class);
                startActivity(intent);


            }
        });

        Button  nextPageBtn3 = findViewById(R.id.produce);
        nextPageBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(QR.this, Generator.class);
                startActivity(intent);


            }
        });
    }
}