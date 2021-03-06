package com.charlieliu.itsch.dailyjournal;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListWeekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ListWeekFragment extends Fragment {

    private final String TAG = "ListWeekFragment";

    private OnFragmentInteractionListener mListener;

    MaterialCalendarView calendarView = null;

    public ListWeekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.fragment_list_week, container, false);

        //setting the pointer to the current date
        calendarView = myFragmentView.findViewById(R.id.CalendarView);
        CalendarDay cd = CalendarDay.from(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        calendarView.setDateSelected(cd, true);
        Log.d(TAG, cd.toString());

        AddEntryActivity.currDay = cd.getCalendar();


        new PreviewNotesAsyncTask(getActivity(), cd).execute();


        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d(TAG, "Date chosen: " + date.toString());
                new PreviewNotesAsyncTask(getActivity(), date).execute();
                AddEntryActivity.currDay = date.getCalendar();

                AddEntryActivity.currDay = date.getCalendar();

            }
        });


        //theme color

        try {
            if (!SettingsActivity.themeIsLight) {

                CardView cv = myFragmentView.findViewById(R.id.cardView3);
                cv.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.darkPrimary));
//                cv.getRootView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.darkPrimaryDark));
                calendarView.setDateTextAppearance(R.style.TextAppearance_AppCompat_Inverse);
                calendarView.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Inverse);
                calendarView.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Inverse);

            }
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

        // Inflate the layout for this fragment
        return myFragmentView;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Log.d(TAG, "onResume");



        new EventAdderAsyncTask(getActivity()).execute();
        new PreviewNotesAsyncTask(getActivity(), CalendarDay.from(AddEntryActivity.currDay)).execute();


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
