package com.example.idealidea_03.alermtest;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class AlarmActivity extends AppCompatActivity {
    Uri selectUri;
    Button finishBtn;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
//        selectUri = Uri.parse(extra.getString("uri"));

        finishBtn = findViewById(R.id.finishBtn);

        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        player = new MediaPlayer();

        try {

            player.setDataSource(this, alert);

        } catch (IllegalArgumentException e1) {

            e1.printStackTrace();

        } catch (SecurityException e1) {

            e1.printStackTrace();

        } catch (IllegalStateException e1) {

            e1.printStackTrace();

        } catch (IOException e1) {

            e1.printStackTrace();

        }

        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {

            player.setAudioStreamType(AudioManager.STREAM_ALARM);

            player.setLooping(true);

            try {

                player.prepare();

            } catch (IllegalStateException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            player.start();
        }

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player.isPlaying()){
                    player.stop();
                    player.release();
                    finish();
                }
            }
        });
    }
}
