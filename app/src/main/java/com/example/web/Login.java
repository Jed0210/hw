package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    private ImageView identifyingCode;
    private String realCode;
    private EditText account,password,code;
    private CheckBox remember;

    private String Account,Mail,Password,Account2,Password2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        account =findViewById(R.id.account2);
        password=findViewById(R.id.password2);
        code=findViewById(R.id.code);
        remember=findViewById(R.id.remember);
        output();

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Account2=account.getText().toString().trim();
                Password2=password.getText().toString().trim();

                Bundle bundle = getIntent().getExtras();
                Account=bundle.getString("帳號");
                Mail=bundle.getString("信箱");
                Password=bundle.getString("密碼");

                if (TextUtils.isEmpty(Account2)){
                    Toast.makeText(Login.this, "請輸入帳號或信箱", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(Password2)) {
                    Toast.makeText(Login.this, "請輸入密碼", Toast.LENGTH_SHORT).show();
                    return;
                }else if((account.getText().toString().equals(Account) ||
                          account.getText().toString().equals(Mail) )&&
                         password.getText().toString().equals(Password) ){

                    Intent intent = new Intent();
                    intent.setClass(Login.this, Menu.class);
                    startActivity(intent);

                   input();


                    return;
                }else {
                    Toast.makeText(Login.this, "輸入有誤，請重新確認", Toast.LENGTH_SHORT).show();
                    LinearLayout layout = findViewById(R.id.layout);
                    layout.setVisibility(View.VISIBLE);
                    identifyingCode = findViewById(R.id.identifyingcode_image);
                    identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap()); //先產生一開始就有的驗證碼
                    realCode = IdentifyingCode.getInstance().getCode().toLowerCase();
                    if ( (account.getText().toString().equals(Account) ||
                            account.getText().toString().equals(Mail) )&&
                            password.getText().toString().equals(Password)&&
                            code.getText().toString().toLowerCase().equals(realCode) ) {
                                                              Intent intent = new Intent();
                                                              intent.setClass(Login.this, Menu.class);
                                                              startActivity(intent);
                                                              }

                }
                }

        });
    }



    private void input() {


        SharedPreferences user = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
            if (remember.isChecked()) {
                edit.putString("account", account.getText().toString());
                edit.putString("password", password.getText().toString());
                edit.putBoolean("remember",true);
            } else {
                edit.clear();
            }
                edit.commit();
            }


    private void output(){
        SharedPreferences user =getSharedPreferences("test",MODE_PRIVATE);
        String account1=user.getString("account","");
        String password1=user.getString("password","");
        boolean remember1=user.getBoolean("remember",false);

        account.setText(account1);
        password.setText(password1);
        remember.setChecked(remember1);

    }

}