package com.example.harshitbatra.to_do_list_assign;

/**
 * Created by Harshit Batra on 15-07-2016.
 */
public class List_table extends table_helper
{
    public static final String TABLE_NAME = "lists";
    public interface Columns
    {
        String ID = "id";
        String TASK = "task";
        String DATE = "date";
    }
    public static final String TABLE_CREATE_CMD =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + LBR
            + Columns.TASK + TYPE_TEXT_PK + COMMA
            + Columns.DATE + TYPE_INT
            + RBR + ";";
}
// 16/0712016 14:15:16