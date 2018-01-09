package com.example.idealidea_03.alermtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.util.Calendar.MINUTE;

public class MainActivity extends AppCompatActivity {
    Calendar cal = Calendar.getInstance();
    Toast mToast;

    private static final String BASE_PATH = Environment.getExternalStorageDirectory() + "/myapp";
    private static final String NORMAL_PATH = BASE_PATH + "/normal";
    Button button4;
    private AlarmManager mAlarmManager;
    private ToggleButton _toggleSun, _toggleMon, _toggleTue, _toggleWed, _toggleThu, _toggleFri, _toggleSat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        button4 = (Button) findViewById(R.id.button4);
        _toggleSun = (ToggleButton) findViewById(R.id.toggle_sun);
        _toggleMon = (ToggleButton) findViewById(R.id.toggle_mon);
        _toggleTue = (ToggleButton) findViewById(R.id.toggle_tue);
        _toggleWed = (ToggleButton) findViewById(R.id.toggle_wed);
        _toggleThu = (ToggleButton) findViewById(R.id.toggle_thu);
        _toggleFri = (ToggleButton) findViewById(R.id.toggle_fri);
        _toggleSat = (ToggleButton) findViewById(R.id.toggle_sat);
    }

    public void onRegist(View v) {
        boolean[] week = { false, _toggleSun.isSelected(), _toggleMon.isSelected(), _toggleTue.isSelected(),
                _toggleWed.isSelected(), _toggleThu.isSelected(), _toggleFri.isSelected(),
                _toggleSat.isSelected() };

        boolean isRepeat = false;
        int len = week.length;
        for (int i = 0; i < len; i++)
        {
            if (week[i])
            {
                isRepeat = true;
                break;
            }
        }

        // 알람 등록
        Intent intent = new Intent(this, AlarmReceiver.class);

        long triggerTime = 0;
        long intervalTime = 24 * 60 * 60 * 1000;// 24시간
        if(isRepeat)
        {
            intent.putExtra("one_time", false);
            intent.putExtra("day_of_week", week);
            PendingIntent pending = getPendingIntent(intent);

            triggerTime = setTriggerTime();

            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, intervalTime, pending);
        }
        else
        {
            intent.putExtra("one_time", true);
            PendingIntent pending = getPendingIntent(intent);

            triggerTime = setTriggerTime();
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pending);
        }

        showToastMessage("알람등록완료");
    }

    private void showToastMessage(String message)
    {
        if(mToast == null)
        {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        else
        {
            mToast.setText(message);
        }
        mToast.show();
    }

    private long setTriggerTime()
    {
        // current Time
        long atime = System.currentTimeMillis();
        // timepicker
//        Calendar curTime = Calendar.getInstance();
//        curTime.set(Calendar.HOUR_OF_DAY, this.mAlarmData.getHour(this));
//        curTime.set(Calendar.MINUTE, this.mAlarmData.getMinute(this));
//        curTime.set(Calendar.SECOND, 0);
//        curTime.set(Calendar.MILLISECOND, 0);
        long btime = cal.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }

    private PendingIntent getPendingIntent(Intent intent)
    {
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }

    public void onUnregist(View v) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pending = getPendingIntent(intent);
        this.mAlarmManager.cancel(pending);
    }

    public void setAlertTime(View v) {
        new TimePickerDialog(MainActivity.this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(MINUTE), false).show();
    }

    public void showRingtonePickerDialog(View v) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select ringtone for notifications:");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        startActivityForResult(intent, 777);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 999:
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        String ringtonePath = uri.toString();
                        Toast.makeText(getApplicationContext(), "ringtone=" + ringtonePath, Toast.LENGTH_LONG).show();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            SimpleDateFormat myDateFormat = new SimpleDateFormat("a hh시 mm분");

            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            button4.setText(myDateFormat.format(cal.getTime()));
        }
    };
}
