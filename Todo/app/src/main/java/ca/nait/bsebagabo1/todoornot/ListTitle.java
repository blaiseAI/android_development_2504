package ca.nait.bsebagabo1.todoornot;

import java.util.Date;

public class ListTitle
{
    // fields
    private int id;
    private String list_name;

    // constructor

    public ListTitle()
    {
    }

    public ListTitle(int id, String list_name)
    {
        this.id = id;
        this.list_name = list_name;
    }

    // Properties
    // setter
    public void setId(int id)
    {
        this.id = id;
    }

    public void setList_name(String list_name)
    {
        this.list_name = list_name;
    }

    // getter

    public int getId()
    {
        return id;
    }

    public String getList_name()
    {
        return list_name;
    }
}
