package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class bonus extends AppCompatActivity implements View.OnClickListener {

    Dialog dialog;
    Dialog dialog2;
    Button dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bonus);

       dia = (Button) findViewById(R.id.dia);
        dia.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dia:
                dialog1();
                break;

        }
    }




    public void dialog1(){
        dialog = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.dialog1,null);
        dialog.setContentView(v);
        dialog.setCancelable(false);

        dialog.show();

        Button next=v.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2();            }
        });

        Button cancel= v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    public void dialog2() {
        dialog2 = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.dialog2,null);
        dialog2.setContentView(v);
        dialog2.setCancelable(false);

        dialog2.show();


        Button previous = v.findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        Button send = v.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
                dialog.dismiss();
            }
        });


    }


}


