package com.example.myApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myApp1.R;
import com.example.room.DataBase;
import com.example.room.MyData;
import com.facebook.stetho.Stetho;

import java.util.List;

public class bonus extends AppCompatActivity implements View.OnClickListener {


    MyData nowSelectedData;//取得在畫面上顯示中的資料內容

    Dialog dialog;
    Dialog dialog2;
    Button dia,add;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    EditText Name,Phone,Hobby,Else;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bonus);


        Stetho.initializeWithDefaults(this);//chrome://inspect/#devices

        dia = (Button) findViewById(R.id.dia);
        dia.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        Name = findViewById(R.id.Name);
        Phone = findViewById(R.id.Phone);
        Hobby = findViewById(R.id.Hobby);
        Else = findViewById(R.id.Else);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //adapter = new RecycleViewAdapter(this,arrayList);
        //recyclerView.setAdapter(adapter);
        //adapter.recyclerviewAction(recyclerView, arrayList, adapter);

       new Thread(new Runnable() {
            @Override
            public void run() {
                List<MyData> data = DataBase.getInstance(bonus.this).getDataUao().displayAll();
                adapter = new RecycleViewAdapter(bonus.this, data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                        adapter.recyclerviewAction(recyclerView);
                        adapter.setOnItemClickListener(new RecycleViewAdapter.OnItemClickListener(){
                            @Override
                            public void onItemClick(MyData myData) {
                                nowSelectedData = myData;
                                Name.setText(myData.getName());
                                Phone.setText(myData.getPhone());
                                Hobby.setText(myData.getHobby());
                                Else.setText(myData.getElseInfo());
                            }
                        });
                    }
                });

            }
        }) .start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dia:
                dialog1();
                break;

            case R.id.add:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = Name.getText().toString();
                        String phone = Phone.getText().toString();
                        String hobby = Hobby.getText().toString();
                        String elseInfo = Else.getText().toString();
                        if (name.length() == 0) return;//如果名字欄沒填入任何東西，則不執行下面的程序
                        MyData data = new MyData();
                        data.setName(name);
                        data.setPhone(phone);
                        data.setHobby(hobby);
                        data.setElseInfo(elseInfo);
                        DataBase.getInstance(bonus.this).getDataUao().insertData(data);
                        runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               adapter.refreshView();
                               Name.setText("");
                               Phone.setText("");
                               Hobby.setText("");
                               Else.setText("");
                           }
                        });
                    }
                }).start();
                break;
        }


    }


    public void dialog1() {
        dialog = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.dialog1, null);
        dialog.setContentView(v);
        dialog.setCancelable(false);
        dialog.show();

        Button next = v.findViewById(R.id.next);
        EditText Name = v.findViewById(R.id.name);
        EditText Phone = v.findViewById(R.id.phone);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = Name.getText().toString();
                        String phone = Phone.getText().toString();

                        if (name.length() == 0) return;//如果名字欄沒填入任何東西，則不執行下面的程序
                        MyData data = new MyData();
                        data.setName(name);
                        data.setPhone(phone);
                        DataBase.getInstance(bonus.this).getDataUao().insertData(data);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.refreshView();
                            }
                        });

                    }
                }).start();

                dialog2();
            }
        });

        Button cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    public void dialog2() {
        dialog2 = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.dialog2, null);
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
        EditText Hobby=v.findViewById(R.id.hobby);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String hobby = Hobby.getText().toString();


                            MyData data = new MyData();
                            data.setHobby(hobby);
                            DataBase.getInstance(bonus.this).getDataUao().insertData(data);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.refreshView();
                                }
                            });


                        }
                    }).start();

                dialog2.dismiss();
                dialog.dismiss();
            }
        });
    }








}








