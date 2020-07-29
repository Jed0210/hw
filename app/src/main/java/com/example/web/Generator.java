package com.example.web;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Generator extends Fragment implements View.OnClickListener{

    public ImageView ivCode;
    public EditText etContent;
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=View.inflate(getActivity(),R.layout.generator,null);



        Button btn=(Button)view.findViewById(R.id.produce);
        btn.setOnClickListener(this);

            return view;
        }



    public void getCode(){


            ImageView ivCode=(ImageView)view.findViewById(R.id.imageView);
            EditText etContent=(EditText)view.findViewById(R.id.editText);

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