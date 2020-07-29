package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.media.AudioManager.FLAG_PLAY_SOUND;

public class Function extends AppCompatActivity {

    public AudioManager am ;
    public SeekBar seekbar;
    public VolumeReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function);


        seekbar = findViewById(R.id.seekbar);

         am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

         int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //獲取系統最大音量
         seekbar.setMax(maxVolume);

         int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);//獲取當前音量
         seekbar.setProgress(currentVolume);


         seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 if (fromUser) {
                     am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, FLAG_PLAY_SOUND);
                     int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                     seekbar.setProgress(currentVolume);
                 }
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) { }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) { }

            });

        receiver = new VolumeReceiver();
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("android.media.VOLUME_CHANGED_ACTION") ;
        registerReceiver(receiver, filter) ;





        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Post.FakeAPIService fakeAPIService = retrofit.create(Post.FakeAPIService.class);

//宣告 Call 連線服務
        Call<Post> call = fakeAPIService.getPost();

//執行連線服務，透過 Callback 來等待回傳成功或失敗的資料
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                // 連線成功，透過 getter 取得特定欄位資料
                Log.d("HKT", "id: " + response.body().getId());
                Log.d("HKT", "title: " + response.body().getTitle());
                Log.d("HKT", "body: " + response.body().getBody());
                Log.d("HKT", "userId: " + response.body().getUserId());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // 連線失敗，印出錯誤訊息
                Log.d("HKT", "response: " + t.toString());
            }
        });
    }


    private class VolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")){
                    int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                    seekbar.setProgress(currentVolume); }
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

 