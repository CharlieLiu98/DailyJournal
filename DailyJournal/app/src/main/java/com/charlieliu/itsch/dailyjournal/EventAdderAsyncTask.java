package com.charlieliu.itsch.dailyjournal;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventAdderAsyncTask extends AsyncTask<Void, Void, List<CalendarDay>>
{

    private WeakReference<Activity> mActivity;

    EventAdderAsyncTask(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    private final ArrayList<CalendarDay> dates = new ArrayList<>();



    @Override
    protected List<CalendarDay> doInBackground(@NonNull Void... voids) {



        for (int i = 0; i < Entry.EntriesList.size(); ++i)
        {
            dates.add(CalendarDay.from(Entry.EntriesList.get(i).getDate()));
        }
        return dates;
    }
    @Override
    protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {

        // May produce an error due to WeakReference but has not happened yet
        MaterialCalendarView calendarView = mActivity.get().findViewById(R.id.CalendarView);
        calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
    }
}
