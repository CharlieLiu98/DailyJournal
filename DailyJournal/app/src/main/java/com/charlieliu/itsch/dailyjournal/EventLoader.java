package com.charlieliu.itsch.dailyjournal;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class EventLoader extends AsyncTask<Object, Void, Boolean>
{
    private final String TAG = "EventLoader";
    private String input;

    private WeakReference<Context> mContext;

    EventLoader (Context context){
        mContext = new WeakReference<>(context);
    }

    @Override
    protected Boolean doInBackground(Object[] params) {
        input = FileIO.readFromFile(mContext.get());


        if (input != "") {
            Gson gson = new Gson();
            Entry.EntriesList = gson.fromJson(input, new TypeToken<ArrayList<Entry>>() {
            }.getType());

        }
        return true;

    }
}
