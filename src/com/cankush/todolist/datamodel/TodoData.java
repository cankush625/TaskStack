package com.cankush.todolist.datamodel;

import com.cankush.todolist.database.MysqlConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoData {
    // Creating the instance of the class so that making the class Singleton and later declaring constructor as private
    private static TodoData instance = new TodoData();
    private static String filename = "TodoListItems.txt";

    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter formatter;

    @FXML
    private TextField titleField;
    @FXML
    private TextArea detailsArea;
    @FXML
    private DatePicker deadLinePicker;

    // Getting the instance
    public static TodoData getInstance() {
        return instance;
    }

    // Creating a private constructor so that the another objects of this class are not allowed to create any more
    private TodoData () {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formatting the date
    }

    // Adding getter for TodoItems
    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }

    // Adding TodoItem to the list of TodoItems
    public void addTodoItem(TodoItem todoItem) {
        todoItems.add(todoItem);
    }

    // Loading TodoItems
    // Taking the item from the user and add it to the todoItems list
    public void displayTodoItem() throws SQLException {
        todoItems = FXCollections.observableArrayList();
        ResultSet val = MysqlConnect.mysqlDisplay();
            while (val.next()) {
                System.out.println("Inside val");
                String title = val.getString("title");
                String details = val.getString("details");
                String deadline = val.getString("deadline");

                LocalDate date = LocalDate.parse(deadline, formatter);
                TodoItem todoItem = new TodoItem(title, details, date);
                // Adding todoItem from database/file to the todoItems ObservableList
                todoItems.add(todoItem);
            }
    }
}

//by Ankush Chavan