package com.charlieliu.itsch.dailyjournal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    private final String TAG = "SettingsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CardView themeColor = findViewById(R.id.themeColor);
        themeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO



            }
        });

        final CardView reminder = findViewById(R.id.reminderCardView);
        final TextView reminderTime = findViewById(R.id.reminderTime);


        String prefName = "DailyJournal";

        SharedPreferences sharedpreferences = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        reminderTime.setText( sharedpreferences.getString("alarmText", "None set"));



        final Intent intent1 = new Intent(SettingsActivity.this, AlarmReceiver.class);



        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        // TODO: add a service here
                        Log.d(TAG, "Time picked: " + selectedHour + ":" + selectedMinute);

                        // This is where we set the repeating alarm broadcast intent

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager am = (AlarmManager) SettingsActivity.this.getSystemService(ALARM_SERVICE);
                        if (am != null) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                            calendar.set(Calendar.MINUTE, selectedMinute);
                            calendar.set(Calendar.MILLISECOND, 0);





                            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                            //Setting the reminder and saving reminder time to preferences

                            String alarmText = String.format("Alarm set at %tI:%tM %tp daily", calendar, calendar, calendar);
                            reminderTime.setText(alarmText);
                            editor.putString("alarmText", alarmText);
                            editor.apply();
                        }
                        else
                        {
                            Log.e(TAG, "AlarmManager == null");
                        }

//                        try {
//                            // Perform the operation associated with our pendingIntent
//                            pendingIntent.send();
//                        } catch (PendingIntent.CanceledException e) {
//                            e.printStackTrace();
//                        }

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Daily Reminder Time:");
                mTimePicker.show();



            }
        });

        reminder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //TODO

                PendingIntent.getBroadcast(SettingsActivity.this, 0, intent1,
                        PendingIntent.FLAG_UPDATE_CURRENT).cancel();

                String temp = "None set";
                reminderTime.setText(temp);
                editor.putString("alarmText", temp);
                editor.apply();

                Toast.makeText(SettingsActivity.this, "Alarm removed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });




    }



}




