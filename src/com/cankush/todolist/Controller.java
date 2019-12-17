package com.cankush.todolist;

import com.cankush.todolist.datamodel.TodoData;
import com.cankush.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        // Whenever the change is occur this will automatically trigger using change listener
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
        todoListView.setItems(TodoData.getInstance().getTodoItems());
        // By default user can select multiple items at a time
        // To avoid this, making user to select single item at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // Code to always make the first item of the list selected(Default selection)
        todoListView.getSelectionModel().selectFirst();

        // Implementing CellFactory
        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                ListCell<TodoItem> cell = new ListCell<TodoItem>() {

                    @Override
                    protected void updateItem(TodoItem item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                            // If the deadline is today, then the color of the todoItem will turn red
                            if (item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                                // If the deadline is tomorrow, then set the color of the todoItem to brown
                            } else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.BROWN);
                            }
                        }
                    }
                };
                return cell;
            }
        });
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
        // Adding title for our dialog
        dialog.setTitle("Add new Todo Item");
        // Setting the header text for the dialog
        dialog.setHeaderText("Use this dialog to create a new todo item");
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
            TodoItem newItem = controller.processResults();
            // Selecting the newly added TodoItem as the entry made
            todoListView.getSelectionModel().select(newItem);
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