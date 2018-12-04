package com.charlieliu.itsch.dailyjournal;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ListMonthFragment extends Fragment {


    private final String TAG = "ListMonthFragment";


    private OnFragmentInteractionListener mListener;

    public ListMonthFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ListMonthFragment newInstance() {
        ListMonthFragment fragment = new ListMonthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//
//            TextView tv = getActivity().findViewById(R.id.textView2);
//            tv.setText("Testing...");


        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new EventAdder(getActivity()).execute();
        View myFragmentView = inflater.inflate(R.layout.fragment_list_month, container, false);

        //setting the pointer to the current date
        MaterialCalendarView calendarView = myFragmentView.findViewById(R.id.CalendarView);
        CalendarDay cd = CalendarDay.from(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        calendarView.setDateSelected(cd, true);
        Log.d(TAG, cd.toString());
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(getContext(), "Working", Toast.LENGTH_SHORT).show();

            }
        });

        // Inflate the layout for this fragment
        return myFragmentView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

//            calendarView.setCurrentDate(CalendarDay.from(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH), true);


            Log.v(TAG, "onAttach");

            new EventLoader().execute();




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
    interface OnFragmentInteractionListener {
    }




}
