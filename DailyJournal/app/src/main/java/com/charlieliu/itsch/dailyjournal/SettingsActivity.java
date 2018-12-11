package com.charlieliu.itsch.dailyjournal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

    public static boolean themeIsLight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CardView themeColor = findViewById(R.id.themeColor);
        final ImageView themeColorImage = findViewById(R.id.themeColorImage);



        final String prefNameTheme = "theme";
        final SharedPreferences themePreferences = getSharedPreferences(prefNameTheme, Context.MODE_PRIVATE);

        SettingsActivity.themeIsLight = themePreferences.getBoolean(prefNameTheme, true);


        if (!SettingsActivity.themeIsLight)
        {
            themeColorImage.setImageResource(R.color.darkPrimary);
        }


        themeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO




                String message = "";
                if (SettingsActivity.themeIsLight)
                {
                    message = "Change to dark theme?";

                }
                else
                {
                    message = "Change to light theme?";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage(message);
                builder .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = themePreferences.edit();

                                if (SettingsActivity.themeIsLight)
                                {
                                    editor.putBoolean(prefNameTheme, false);
                                    themeColorImage.setImageResource(R.color.darkPrimary);

                                }
                                else
                                {
                                    editor.putBoolean(prefNameTheme, true);
                                    themeColorImage.setImageResource(R.color.colorPrimary);

                                }

                                editor.apply();



                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertdialog = builder.create();
                alertdialog.show();

            }
        });

        final CardView reminder = findViewById(R.id.reminderCardView);
        final TextView reminderTime = findViewById(R.id.reminderTime);


        String prefName = "DailyJournal";

        final SharedPreferences sharedpreferences = getSharedPreferences(prefName, Context.MODE_PRIVATE);

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
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("alarmText", alarmText);
                            editor.apply();

                            Toast.makeText(SettingsActivity.this, "Alarm set", Toast.LENGTH_SHORT).show();
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
                final SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("alarmText", temp);
                editor.apply();

                Toast.makeText(SettingsActivity.this, "Alarm removed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });




    }



}




