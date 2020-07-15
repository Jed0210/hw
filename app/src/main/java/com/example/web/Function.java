package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import static android.media.AudioManager.FLAG_PLAY_SOUND;

public class Function extends AppCompatActivity {

    public AudioManager am ;
    public SeekBar seekbar;
    private VolumeReceiver receiver;


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
        unregisterReceiver(receiver); }
        }

 