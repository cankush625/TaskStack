package com.cankush.todolist;

import com.cankush.todolist.datamodel.TodoData;
import com.cankush.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItems; // Making todoItems generic
    @FXML
    private ListView<TodoItem> todoListView; // Making todoListView generic
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadLineLabel;

    /**
     * Initializing with some data
     */
    public void initialize() {
//        TodoItem item1 = new TodoItem("Mail birthday card", "Mail a birthday card to Ankush on his 21th birthday",
//                LocalDate.of(2020, Month.FEBRUARY, 16));
//        TodoItem item2 = new TodoItem("Devfest Event", "Devfest event for developers byb Google in Mumbai",
//                LocalDate.of(2020, Month.MARCH, 24));
//        TodoItem item3 = new TodoItem("Application deployment", "Deploy the TodoList app to the production",
//                LocalDate.of(2020, Month.MAY, 20));
//        TodoItem item4 = new TodoItem("Renew Passport", "Appointment for renewing passport at 10AM",
//                LocalDate.of(2020, Month.MAY, 4));
//        TodoItem item5 = new TodoItem("Finish the UI for WebApp", "Finish the UI of WebApp demanded by Mr. Smith",
//                LocalDate.of(2020, Month.JULY, 2));
//
//        todoItems = new ArrayList<TodoItem>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);
//        todoItems.add(item5);
//
//        // Setting the data from here to the List in the TodoData file
//        TodoData.getInstance().setTodoItems(todoItems);

        // Adding change listener to select the item that is changed
        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
                if (newValue != null) {
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    // Adding DateTimeFormatter to format the date
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d YYYY");
                    deadLineLabel.setText(df.format(item.getDeadline())); //formatting the date
                }
            }
        });

        // Adding the todos present in the todoItems to the todoListView
        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        // By default user can select multiple items at a time
        // To avoid this, making user to select single item at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Code to always make the first item of the list selected(Default selection)
        todoListView.getSelectionModel().selectFirst();
    }

    /**
     * This handleClickListView code is replaced by Overriding the change method in the initialize function
     * As the generic event handler will trigger whenever the change is occurred
     */
    public void handleClickListView() {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//        System.out.println("The selected item is " + item);
        itemDetailsTextArea.setText(item.getDetails());
        deadLineLabel.setText(item.getDeadline().toString());
        //The line no. 77 can be replaced by StringBuilder
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due: ");
//        sb.append(item.getDeadline().toString());
//        itemDetailsTextArea.setText(sb.toString()); // Displaying details on the text area of the window
    }
}

// by Ankush Chavan