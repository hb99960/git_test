package com.example.harshitbatra.to_do_list_assign;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity
{

    ListView lvlist;
    SQLiteDatabase my_db;
    public static final String TAG = "check";
    SharedPreferences sharePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText ettask,etdate,etdate2,ettime2;
        Button btnadd,btndate,btntime;

        ettask = (EditText) findViewById(R.id.ettask);
        btnadd = (Button) findViewById(R.id.btnadd);
        btndate = (Button) findViewById(R.id.btndate);
        btntime = (Button) findViewById(R.id.btntime);
        etdate2 = (EditText) findViewById(R.id.etdate2);
        ettime2 = (EditText) findViewById(R.id.ettime2);

        my_db = Mydbopener.open_writable_database(this);
        lvlist = (ListView) findViewById(R.id.lv1);
        Log.d(TAG, "onCreate:Database is created!\n");

        sharePreferences = getSharedPreferences("myPreferences",Context.MODE_PRIVATE);
        if(sharePreferences.contains("task"))
        {
            ettask.setText(sharePreferences.getString("task",""));
        }
        if(sharePreferences.contains("date"))
        {
            etdate2.setText(sharePreferences.getString("date",""));
        }
        if(sharePreferences.contains("time"))
        {
            ettime2.setText(sharePreferences.getString("time",""));
        }

        update_list();

        btnadd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String task = ettask.getText().toString();
               // String date = etdate.getText().toString();
                String date = etdate2.getText().toString();
                String time = ettime2.getText().toString();
                String date2 = etdate2.getText().toString() +" " + ettime2.getText().toString();

                if(task.isEmpty() || date2.isEmpty()  )
                    Toast.makeText(MainActivity.this, "Enter all fields!", Toast.LENGTH_SHORT).show();
                else
                    try
                    {
                        sharePreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharePreferences.edit();
                        editor.putString("task",task);
                        editor.putString("date",date);
                        editor.putString("time",time);
                        editor.commit();
                        Date newDate;
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        newDate = format.parse(date2);
                        Log.d(TAG, "onClick: newDate = " + newDate);
                        long newDateToSec = newDate.getTime();
                        Log.d(TAG, "onClick: newDateToSec = " + newDateToSec);
                        add_task(task,newDateToSec);
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
            }
        });

        btndate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment newfragment = new SelectDateFragment(new SelectDateFragment.Setdatelistener()
                {

                    @Override
                    public void populate_date(int year, int month, int date) throws ParseException
                    {
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                        etdate2.setText(sdf2.format(sdf2.parse(date + "/" + month + "/" + year)));
                    }
                });

                newfragment.show(getFragmentManager(),"DatePicker");
            }
        });

        btntime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment newfragment = new SelectTimeFragment(new SelectTimeFragment.Settimelistener()
                {
                    @Override
                    public void populate_time(int hour, int minute)
                    {
                        ettime2.setText(hour + ":" + minute);
                    }

                });
                newfragment.show(getFragmentManager(),"TimePicker");

            }
        });
    }

    Date mdate;

    public boolean check_date(long date) throws ParseException
    {
       /* String given_date_string =date;
        if(given_date_string == null)
            return false;

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            mdate = sdf2.parse(given_date_string);

        Calendar current = Calendar.getInstance();
        long current_seconds = currentTimeMillis();
        long mdate_sec  = mdate.getTime();
        Log.d(TAG, "add_task: date = " + mdate.getTime() + "Current time = " + current_seconds);*/

        long current_seconds = currentTimeMillis();
        long mdate_sec  = date;
        if(current_seconds < mdate_sec )
            return true;
        else
            return false;
    }

    public void add_task(String task,long date) throws ParseException
    {

        if( check_date(date) )
        {
            if(task != null)
            {
                //List list_item = new List(task,String.valueOf(mdate_sec));
                ContentValues value = new ContentValues();
                value.put(List_table.Columns.TASK,task);
                value.put(List_table.Columns.DATE,date);

                long id = my_db.insert(List_table.TABLE_NAME,null, value);

                if(id == -1)
                {
                    Toast.makeText(MainActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "add_task:Item Not Inserted in Database!\n");
                }
                else
                {
                    //list_item.add_item(task,date);
                    update_list();
                    Toast.makeText(MainActivity.this, "New Task Added", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(MainActivity.this, "Enter Task!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(MainActivity.this, "Enter Valid Date!", Toast.LENGTH_SHORT).show();
    }

    ArrayList<String> task_list;

    public void update_list()
    {
        Log.d(TAG, "update_list: ");
        String[] projection ={ List_table.Columns.TASK, List_table.Columns.DATE };
        Cursor c = my_db.query
                (List_table.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        List_table.Columns.DATE);

        task_list = new ArrayList<>();
        while(c.moveToNext())
        {
            String task = c.getString(c.getColumnIndex(List_table.Columns.TASK));
            task_list.add(task);
        }

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,task_list);
        lvlist.setAdapter(myadapter);

        lvlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
              //  ListView lv = (ListView) adapterView;
                TextView row = (TextView) view;
                row.setPaintFlags(row.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                my_db.delete(List_table.TABLE_NAME,List_table.Columns.TASK  + "='" + lvlist.getItemAtPosition(position).toString() + "'",null);
               // my_db.execSQL("delete from lists where task =" +lvlist.getItemAtPosition(position).toString() );
                Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                update_list();
            }
        });
    }
}
