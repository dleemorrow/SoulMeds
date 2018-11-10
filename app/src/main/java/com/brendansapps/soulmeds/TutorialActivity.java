package com.brendansapps.soulmeds;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.Objects;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Tutorial");

        final VideoView videoView = findViewById(R.id.videoView);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.tutorial);

        // https://stackoverflow.com/questions/3686729/mediacontroller-positioning-over-videoview/24529711
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        MediaController mediaController = new MediaController(TutorialActivity.this);
                        videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

        videoView.setVideoURI(uri);
        videoView.requestFocus();

        Button mBtnPlay = findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        assert am != null;
        if(am.getStreamVolume(AudioManager.STREAM_MUSIC) == 0){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Your device is muted!");
            alertDialog.setMessage("Please enable media volume for the video tutorial. ");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}