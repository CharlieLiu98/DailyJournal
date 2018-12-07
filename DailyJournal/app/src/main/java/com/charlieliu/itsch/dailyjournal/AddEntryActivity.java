package com.charlieliu.itsch.dailyjournal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEntryActivity extends AppCompatActivity {

    private final String TAG = "AddEntryActivity";
    private int idx;

    private Calendar entryDate = Calendar.getInstance();
    TextView dateTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        Button addEntryBtn = findViewById(R.id.addEntryBtn);
        Button clearBtn = findViewById(R.id.clearBtn);


        dateTV = findViewById(R.id.dateTV);

        entryDate = (Calendar) getIntent().getSerializableExtra("cal");
        updateLabel();

        final EditText notesET = findViewById(R.id.notesET);
        final RatingBar ratingBar = findViewById(R.id.ratingBar);

        //Getting things from intent
        notesET.setText(getIntent().getStringExtra("notes"));
        ratingBar.setRating(getIntent().getFloatExtra("rating", 0f));
        idx = getIntent().getIntExtra("idx", -1);


        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingBar.setRating(0);
                notesET.setText("");
            }
        });

        addEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (idx != -1)
                {
                    Entry.EntriesList.set(idx, new Entry(entryDate, ratingBar.getRating(), notesET.getText().toString()));
                }

                else {
                    Entry.EntriesList.add(new Entry(entryDate, ratingBar.getRating(), notesET.getText().toString()));
                    Log.v(TAG, Entry.EntriesList.get(0).toString());
                }

                new EventSaver(getApplicationContext()).execute();
                finish();
            }
        });

        // setting the calendar with the picked date
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                entryDate.set(Calendar.YEAR, year);
                entryDate.set(Calendar.MONTH, monthOfYear);
                entryDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        //showing date picker dialog
        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Date clicked");
                new DatePickerDialog(AddEntryActivity.this, dateSetListener, entryDate
                        .get(Calendar.YEAR), entryDate.get(Calendar.MONTH),
                        entryDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //updating date picker
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateTV.setText(sdf.format(entryDate.getTime()));
    }
}
