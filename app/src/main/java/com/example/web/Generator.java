package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Generator extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);


        Button btn=(Button)findViewById(R.id.produce);
        btn.setOnClickListener(this);
    }


    public void getCode(){
        ImageView ivCode=(ImageView)findViewById(R.id.imageView);
        EditText etContent=(EditText)findViewById(R.id.editText);
        BarcodeEncoder encoder = new BarcodeEncoder();
        try{
            Bitmap bit = encoder.encodeBitmap(etContent.getText()
                    .toString(), BarcodeFormat.QR_CODE,250,250);
            ivCode.setImageBitmap(bit);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        getCode();
    }
}