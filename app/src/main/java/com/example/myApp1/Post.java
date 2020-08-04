package com.example.myApp1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class Post {
    private int id;
    private String title;
    private String body;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }




    public interface FakeAPIService {
        //取得單筆資料
        @GET("/posts/1") //annotation 註解宣告方式定義 HTTP 連線獲取資料方法與指定API後網址
        Call<Post> getPost();

        //取得多筆資料
        @GET("/posts")
        Call<List<Post>> getPosts();
    }
    }




