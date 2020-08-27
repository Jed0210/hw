package com.example.myApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myApp1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit extends AppCompatActivity {
RecyclerView recyclerView;
RetrofitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(Retrofit.this));


        retrofit();





    }



    public void retrofit(){


        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
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
                List<Post> list = response.body();

                Log.d("gg", "onResponse: "+response.body());
                Log.d("list", list.get(0).getTitle());
                adapter = new RetrofitAdapter(Retrofit.this, list);
                recyclerView.setAdapter(adapter);
               // adapter.notifyDataSetChanged();
                Toast.makeText(Retrofit.this, "成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("HKT", "response: " + t.toString());

                Toast.makeText(Retrofit.this, "失敗", Toast.LENGTH_SHORT).show();
            }
        });


    }

//    private void setRecyclerView(List<Post>list){
//        recyclerView = findViewById(R.id.recyclerView2);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new RetrofitAdapter(this, list);
//        recyclerView.setAdapter(adapter);
//    }
}