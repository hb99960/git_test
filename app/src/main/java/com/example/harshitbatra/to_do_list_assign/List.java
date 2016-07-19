package com.example.harshitbatra.to_do_list_assign;

import java.util.ArrayList;

/**
 * Created by Harshit Batra on 15-07-2016.
 */
public class List
{
   // int id;
    String task;
    String date;

   /* public List(int id,String task,String date)
    {
        this.id = id;
        this.task = task;
        this.date = date;
    }*/

    public List(String task,String date)
    {
        this.task = task;
        this.date = date;
    }
    public List()
    {

    }

    /*public int getId()
    {
        return id;
    }*/
    public String getTask()
    {
        return task;
    }
    public String getDate()
    {
        return date;
    }

    ArrayList<List> task_list = new ArrayList<>();

    public void add_item(String task,String date)
    {
        task_list.add(new List(task,date));
    }
    public ArrayList<List> getList()
    {
        return task_list;
    }
}
