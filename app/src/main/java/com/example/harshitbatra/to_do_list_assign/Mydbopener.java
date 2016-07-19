package com.example.harshitbatra.to_do_list_assign;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Harshit Batra on 15-07-2016.
 */
public class Mydbopener extends SQLiteOpenHelper
{
    public static final String DB_NAME = "mydatabase";
    public static final int DB_VER = 1;
    private static Mydbopener my_db_opener = null;

    public Mydbopener(Context context)
    {
        super(context, DB_NAME, null, DB_VER);
    }

    public static SQLiteDatabase open_readable_database(Context c)
    {
        if(my_db_opener == null)
            my_db_opener = new Mydbopener(c);

        return my_db_opener.getReadableDatabase();
    }

    public static SQLiteDatabase open_writable_database(Context c)
    {
        if(my_db_opener == null)
            my_db_opener = new Mydbopener(c);

        return my_db_opener.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(List_table.TABLE_CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
