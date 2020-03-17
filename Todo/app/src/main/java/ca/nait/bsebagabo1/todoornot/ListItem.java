package ca.nait.bsebagabo1.todoornot;

/*
* This is a todos item class
* */

public class ListItem
{
    // Fields
    private int id;
    private String listItemContent;
    private int status;
    private String created_at;
    // constructor

    public ListItem()
    {
    }

    public ListItem(int id, String listItemContent, int status, String created_at)
    {
        this.id = id;
        this.listItemContent = listItemContent;
        this.status = status;
        this.created_at = created_at;
    }
    // Properties Setters
    public void setId(int id)
    {
        this.id = id;
    }

    public void setListItemContent(String listItemContent)
    {
        this.listItemContent = listItemContent;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    // Getters

    public int getId()
    {
        return id;
    }

    public String getListItemContent()
    {
        return listItemContent;
    }

    public int getStatus()
    {
        return status;
    }

    public String getCreated_at()
    {
        return created_at;
    }
}
