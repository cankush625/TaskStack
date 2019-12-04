package com.cankush.todolist;

import com.cankush.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItems; // Making todoItems generic
    @FXML
    private ListView<TodoItem> todoListView; // Making todoListView generic
    @FXML
    private TextArea itemDetailsTextArea;

    //Initializing with some data
    public void initialize() {
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

        // Adding the todos present in the todoItems to the todoListView
        todoListView.getItems().setAll(todoItems);
        // By default user can select multiple items at a time
        // To avoid this, making user to select single item at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void handleClickListView() {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//        System.out.println("The selected item is " + item);
//        itemDetailsTextArea.setText(item.getDetails());
        //The line no. 51 can be replaced by StringBuilder
        StringBuilder sb = new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Due: ");
        sb.append(item.getDeadline().toString());
        itemDetailsTextArea.setText(sb.toString()); // Displaying details on the text area of the window
    }
}

// by Ankush Chavan