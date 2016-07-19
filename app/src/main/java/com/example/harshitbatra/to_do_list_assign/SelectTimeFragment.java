package com.example.harshitbatra.to_do_list_assign;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Harshit Batra on 16-07-2016.
 */
public class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{

    public interface Settimelistener
    {
        public void populate_time(int hour,int minute);
    }

    Settimelistener mylistener;

    public SelectTimeFragment(Settimelistener sdl)
    {
        mylistener = sdl;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,min, DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min)
    {
        mylistener.populate_time(hour,min);
    }
}
