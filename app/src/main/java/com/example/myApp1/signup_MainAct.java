package com.example.myApp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class signup_MainAct extends AppCompatActivity implements View.OnClickListener {
    ImageView identifyingCode;
    String realCode;
    private EditText account;
    private EditText mail;
    private EditText password;
    private String Account,Mail, Password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
init();

        SharedPreferences user = getSharedPreferences("signup", MODE_PRIVATE);
        Boolean signed=user.getBoolean("signed",false);
if(signed==true){
    Intent intent = new Intent().setClass(signup_MainAct.this,Login.class);
    startActivity(intent);
    finish();
}
else {
    identifyingCode = (ImageView) findViewById(R.id.identifyingcode_image);
    identifyingCode.setOnClickListener(this);

    identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
    //先產生一開始就有的驗證碼
    //realCode = IdentifyingCode.getInstance().getCode().toLowerCase();

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    final String[] lunch = {"台灣", "日本", "韓國", "美國", "馬來西亞", "香港"};
    ArrayAdapter<String> lunchList = new ArrayAdapter<>(signup_MainAct.this,
            android.R.layout.simple_spinner_dropdown_item,
            lunch);
    spinner.setAdapter(lunchList);


    Button signup = (Button) findViewById(R.id.signup);
    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getEditString();

            if (TextUtils.isEmpty(Account)) {
                Toast.makeText(signup_MainAct.this, "沒輸入帳號哦", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(Mail)) {
                Toast.makeText(signup_MainAct.this, "請輸入電子信箱", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(Password)) {
                Toast.makeText(signup_MainAct.this, "請輸入密碼", Toast.LENGTH_SHORT).show();
                return;
            } else {


                Intent intent = new Intent();
                intent.setClass(signup_MainAct.this, Login.class);

                Toast.makeText(signup_MainAct.this, "註冊成功", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                input();
            }
        }

    });
    }
}


    private void init() {   //初始化介面使用到ㄉ東西
        account = findViewById(R.id.account);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
    }


    @Override
    public void onClick(View view) {
        identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());  //這個是刷新驗證碼
        realCode = IdentifyingCode.getInstance().getCode().toLowerCase();
    }



    private void getEditString(){    //將用戶的資料轉成字串
        Account=account.getText().toString().trim();
        Mail=mail.getText().toString().trim();
        Password=password.getText().toString().trim();
    }


    private void input() {
        SharedPreferences user = getSharedPreferences("signup", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("account", account.getText().toString());
        edit.putString("password", password.getText().toString());
        edit.putString("mail", mail.getText().toString());
        edit.putBoolean("signed",true);
        edit.commit();
    }
}

























