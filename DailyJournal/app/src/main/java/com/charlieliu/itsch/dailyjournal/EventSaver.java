package com.charlieliu.itsch.dailyjournal;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventSaver extends AsyncTask<Object, Void, Boolean>
{

    private final String TAG = "EventSaver";

    private WeakReference<Context> mContext;

    EventSaver (Context context){
        mContext = new WeakReference<>(context);
    }


    @Override
    protected Boolean doInBackground(Object[] params) {




        Gson gson = new Gson();
        String json = gson.toJson(Entry.EntriesList, new TypeToken<ArrayList<Entry>>(){}.getType());

        Log.d(TAG, json);
        FileIO.writeToFile(json, mContext.get());
        Log.d(TAG, mContext.get().getFilesDir().toString());

        return true;
    }
}
