package com.charlieliu.itsch.dailyjournal;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

public class AllEntriesActivity extends AppCompatActivity {

    private final String TAG = "AllEntriesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_entries);




        //theme
        try {
            if (!SettingsActivity.themeIsLight) {
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(AllEntriesActivity.this, R.color.darkPrimary)));
                findViewById(R.id.MainLayout).setBackgroundColor(ContextCompat.getColor(AllEntriesActivity.this, R.color.darkPrimaryDark));
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        new PreviewNotesAsyncTask(AllEntriesActivity.this).execute();
    }

}
