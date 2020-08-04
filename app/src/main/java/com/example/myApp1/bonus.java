package com.example.myApp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.room.DataBase;
import com.example.room.MyData;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    //retrofit();

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
        }).start();
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
                        MyData data = new MyData(name, phone, hobby, elseInfo);
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
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
                dialog.dismiss();
            }
        });
    }







    public void retrofit(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Post.FakeAPIService fakeAPIService = retrofit.create(Post.FakeAPIService.class);

//宣告 Call 連線服務
        Call<List<Post>> call = fakeAPIService.getPosts();

//執行連線服務，透過 Callback 來等待回傳成功或失敗的資料
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                // 連線成功，透過 getter 取得特定欄位資料

  List<Post> arrayList = new ArrayList<>();

               for(int i=0 ; i<response.body().size() ; i++)
               {
               arrayList.add(response.body().get(i));
               }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // 連線失敗，印出錯誤訊息
                Log.d("HKT", "response: " + t.toString());
            }
        });
    }
}








