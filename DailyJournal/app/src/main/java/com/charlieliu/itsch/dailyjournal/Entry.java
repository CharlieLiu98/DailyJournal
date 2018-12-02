package com.charlieliu.itsch.dailyjournal;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Entry
{

    public static ArrayList<Entry> EntriesList = new ArrayList<Entry>();

    private Calendar entryDate;

    private float entryRating;
    private String entryNotes;


//    An empty constructor should not be needed
//
//    Entry()
//    {
//        entryYear = 0;
//        entryMonth = 0;
//        entryDay = 0;
//
//        entryRating = 0;
//        entryNotes = "";
//    }

    Entry(Calendar date, float rating, String notes)
    {
        entryDate = date;

        entryRating = rating;
        entryNotes = notes;
    }

    public void setDate(Calendar date)
    {
        entryDate = date;
    }

    public void setRating(float rating)
    {
        entryRating = rating;
    }

    public void setNotes(String note)
    {
        entryNotes = note;
    }

    public Calendar getDate()
    {
        return entryDate;
    }

    public float getRating()
    {
        return entryRating;
    }

    public String getNotes()
    {
        return entryNotes;
    }

    public String toString()
    {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf.format(entryDate.getTime());
    }








}
