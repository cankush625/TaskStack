package com.cankush.todolist.datamodel;

import java.time.LocalDate;

public class TodoItem {
    //Required fields for the TodoList item
    private String title;
    private String details;
    private LocalDate deadline;

    public TodoItem(String title, String details, LocalDate deadline) {
        this.title = title;
        this.details = details;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * The toString() method is used we are not implementing the cell factory.
     * But when we used the cell factory, we don't required to override the toString() method
     * @return
     */
//    @Override
//    public String toString() {
//        return title;
//    }
}
