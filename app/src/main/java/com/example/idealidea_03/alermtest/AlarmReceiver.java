package com.example.idealidea_03.alermtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.Calendar;

/**
 * Created by IdealIdea_03 on 2018-01-09.
 */

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle extra = intent.getExtras();
        if (extra != null)
        {
            boolean isOneTime = extra.getBoolean("one_time");
            if (isOneTime)
            {
//                AlarmDataManager.getInstance().setAlarmEnable(context, false);
                // 알람 울리기.
                Log.d("알람", "개빡치네");
            }
            else
            {
                boolean[] week = extra.getBooleanArray("day_of_week");

                Calendar cal = Calendar.getInstance();

                if (!week[cal.get(Calendar.DAY_OF_WEEK)])
                    return;

                // 알람 울리기.
            }
        }
    }
}