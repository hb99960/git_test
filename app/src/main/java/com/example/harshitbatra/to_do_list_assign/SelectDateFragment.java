package com.example.harshitbatra.to_do_list_assign;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by Harshit Batra on 16-07-2016.
 */
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

    public interface Setdatelistener
    {
        public void populate_date(int year,int month,int date) throws ParseException;
    }

    Setdatelistener mylistener;

    public SelectDateFragment(Setdatelistener sdl)
    {
        mylistener = sdl;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);

        return new DatePickerDialog(getActivity(),this,year,month+1,date);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date)
    {
        month = month + 1;
        try
        {
            mylistener.populate_date(year, month, date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}
