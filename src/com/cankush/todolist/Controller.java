package com.cankush.todolist;

import com.cankush.todolist.datamodel.TodoData;
import com.cankush.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<TodoItem> todoItems; // Making todoItems generic
    @FXML
    private ListView<TodoItem> todoListView; // Making todoListView generic
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadLineLabel;
    @FXML
    private BorderPane mainBorderPane;

    /**
     * Initializing with some data
     */
    public void initialize() {
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
     * This showNewItemDialog() method will show the dialog to submit input to create new TodoItem
     */
    @FXML
    public void showNewItemDialog() {
        // Initializing Dialog of ButtonType
        Dialog<ButtonType> dialog = new Dialog<>();
        // Loading FXML here
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        // Initializing FXMLLoader
        FXMLLoader fxmlLoader = new FXMLLoader();
        // Getting the FXML resource that loads the dialog pop up
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            // Setting the root i.e. todoItemDialog.fxml as a content for dialog by using fxmlLoader
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        // Adding OK Button
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        // Adding CANCEL Button
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // Showing which button is pressed in the terminal
        Optional<ButtonType> result = dialog.showAndWait();
        // If result is present and the result contains data of ButtonType.OK the print ON pressed else CANCEL pressed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Executing DialogController
            DialogController controller = fxmlLoader.getController();
            controller.processResults();
            System.out.println("OK pressed");
        } else {
            System.out.println("CANCEL pressed");
        }
    }

    /**
     * This handleClickListView code is replaced by Overriding the change method in the initialize function
     * As the generic event handler will trigger whenever the change is occurred
     */
    @FXML
    public void handleClickListView() {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//        System.out.println("The selected item is " + item);
        itemDetailsTextArea.setText(item.getDetails());
        deadLineLabel.setText(item.getDeadline().toString());
    }
}

// by Ankush Chavan