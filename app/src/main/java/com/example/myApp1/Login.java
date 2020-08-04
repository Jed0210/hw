package com.example.myApp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    private ImageView identifyingCode;
    private String realCode;
    private EditText account,password,code;
    public CheckBox remember;

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

                SharedPreferences user =getSharedPreferences("signup",MODE_PRIVATE);
                Account=user.getString("account","");
                Password=user.getString("password","");
                Mail=user.getString("mail","");


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


        SharedPreferences user = getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
            if (remember.isChecked()) {
                edit.putString("account2",account.getText().toString());
                edit.putString("password2",password.getText().toString());
                edit.putBoolean("remember",true);

            } else {
                edit.remove("account2");
                edit.remove("password2");
                edit.putBoolean("remember",false);
            }
                edit.commit();
            }


    private void output(){
        SharedPreferences user =getSharedPreferences("remember",MODE_PRIVATE);
        String account2=user.getString("account2","");
        String password2=user.getString("password2","");
        boolean remember2=user.getBoolean("remember",false);

        account.setText(account2);
        password.setText(password2);
        remember.setChecked(remember2);

    }

}