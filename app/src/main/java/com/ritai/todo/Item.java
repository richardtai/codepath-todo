package com.ritai.todo;

import java.util.Date;

/**
 * Created by ritai on 12/21/15.
 */
public class Item {

    private int id;
    private String title;
    private Date dueDate;
    private int priority;
    private String notes;
    private boolean status;

    public Item() {}

    public Item(String title, Date dueDate, int priority, String notes, boolean status) {
        super();
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.notes = notes;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getNotes() {
        return this.notes;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(Date date) {
        this.dueDate = date;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String stringifiedItem = "itemId: " + this.id + "\ntitle: " + this.title + "\ndueDate: "
                + this.dueDate + "\npriority: " + this.priority + "\nnotes: " + this.notes +
                "\nstatus: " + this.status;
        return stringifiedItem;
    }

}
