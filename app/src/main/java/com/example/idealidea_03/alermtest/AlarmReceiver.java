package com.example.idealidea_03.alermtest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.util.Calendar;

/**
 * Created by IdealIdea_03 on 2018-01-09.
 */

public class AlarmReceiver extends BroadcastReceiver {
    Uri selectUri;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extra = intent.getExtras();
        selectUri = Uri.parse(extra.getString("uri"));
        if (extra != null) {
            boolean isOneTime = extra.getBoolean("one_time");
            if (isOneTime) {
//                AlarmDataManager.getInstance().setAlarmEnable(context, false);
                // 알람 울리기.
                Log.d("알람", "개빡치네");

                Intent i = new Intent(context, AlarmActivity.class);
                i.putExtra("uri", selectUri.toString());
                PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_ONE_SHOT);
                try {
                    pi.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }

//                try {
//                    MediaPlayer mPlayer = null;
//                    mPlayer = MediaPlayer.create(context, selectUri);
//                    mPlayer.start();
////                    if (OS의 사운드를 울리고 싶다면){
////
////                    }
////                    if (mPlayer != null) {
////
////                    }
//                } catch (IllegalStateException e) {
//
//                }

            } else {
                boolean[] week = extra.getBooleanArray("day_of_week");

                Calendar cal = Calendar.getInstance();

                if (!week[cal.get(Calendar.DAY_OF_WEEK)])
                    return;

                // 알람 울리기.
            }
        }
    }
}