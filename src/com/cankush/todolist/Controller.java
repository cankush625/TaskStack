package com.cankush.todolist;

import com.cankush.todolist.datamodel.TodoItem;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItems;

    //Initializing with some data
    public void intialize() {
        TodoItem item1 = new TodoItem("Mail birthday card", "Mail a birthday card to Ankush on his 21th birthday",
                LocalDate.of(2020, Month.FEBRUARY, 16));
        TodoItem item2 = new TodoItem("Devfest Event", "Devfest event for developers byb Google in Mumbai",
                LocalDate.of(2020, Month.MARCH, 24));
        TodoItem item3 = new TodoItem("Application deployment", "Deploy the TodoList app to the production",
                LocalDate.of(2020, Month.MAY, 20));
        TodoItem item4 = new TodoItem("Renew Passport", "Appointment for renewing passport at 10AM",
                LocalDate.of(2020, Month.MAY, 4));
        TodoItem item5 = new TodoItem("Finish the UI for WebApp", "Finish the UI of WebApp demanded by Mr. Smith",
                LocalDate.of(2020, Month.JULY, 2));

        todoItems = new ArrayList<TodoItem>();
        todoItems.add(item1);
        todoItems.add(item2);
        todoItems.add(item3);
        todoItems.add(item4);
        todoItems.add(item5);

    }
}
