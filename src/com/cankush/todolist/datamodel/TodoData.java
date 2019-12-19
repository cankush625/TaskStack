package com.cankush.todolist.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class TodoData {
    // Creating the instance of the class so that making the class Singleton and later declaring constructor as private
    private static TodoData instance = new TodoData();
    private static String filename = "TodoListItems.txt";

    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter formatter;

    // Getting the instance
    public static TodoData getInstance() {
        return instance;
    }

    // Creating a private constructor so that the another objects of this class are not allowed to create any more
    private TodoData () {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyy"); // Formatting the date
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
    public void loadTodoItems() throws IOException {
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename); // Path of the file
        BufferedReader br = Files.newBufferedReader(path); // Reading from the file

        String input;
        try {
            while ((input = br.readLine()) != null) {
                // Separating the input by \t. The first element is title, second element is details, and the third element is date
                String[] itemPieces = input.split("\t");

                String title = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                TodoItem todoItem = new TodoItem(title, details, date);
                // Adding todoItem from database/file to the todoItems ObservableList
                todoItems.add(todoItem);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    // Storing the TodoItem
    public void storeTodoItems() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            // Initializing iterator
            Iterator<TodoItem> iter = todoItems.iterator();
            while(iter.hasNext()) {
                TodoItem item = iter.next();
                bw.write(String.format("%s\t%s\t%s\t", // Separating the fields by tab space
                        item.getTitle(),
                        item.getDetails(),
                        item.getDeadline().format(formatter))); // Using date formatter
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    // Removing the item / Deleting the item
    public void deleteTodoItem(TodoItem item) {
        todoItems.remove(item);
    }
}

//by Ankush Chavan