package com.cankush.todolist;

import com.cankush.todolist.datamodel.TodoData;
import com.cankush.todolist.datamodel.TodoItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;

    // Adding filtered list to show the item by applying some criteria
    private FilteredList<TodoItem> filteredList;

    // Adding predicate instances;
    private Predicate<TodoItem> wantAllItems;
    private Predicate<TodoItem> wantTodaysItems;

    /**
     * Initializing with some data
     */
    public void initialize() {
        // initializing context menu list
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        // Code for deleting the selected item
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });
        // Adding delete option to the context menu
        listContextMenu.getItems().addAll(deleteMenuItem);

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

        // Predicate for all items
        wantAllItems = new Predicate<TodoItem>() {
            @Override
            public boolean test(TodoItem item) {
                return true;
            }
        };

        // Predicate for today's items
        wantTodaysItems = new Predicate<TodoItem>() {
            @Override
            public boolean test(TodoItem item) {
                return (item.getDeadline().equals(LocalDate.now()));
            }
        };

        // Adding code for filteredList
        filteredList = new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), wantAllItems);

        // Sorting the TodoItems list according to the date
        // filteredList can be replaced by TodoData.getInstance().getTodoItems() if we are not implementing filteredList
        SortedList<TodoItem> sortedList = new SortedList<TodoItem>(filteredList,
                new Comparator<TodoItem>() {
                    @Override
                    public int compare(TodoItem o1, TodoItem o2) {
                        return o1.getDeadline().compareTo(o2.getDeadline());
                    }
                });

        // Adding the todos present in the todoItems to the todoListView
//        todoListView.setItems(TodoData.getInstance().getTodoItems());

        // Adding the todos using sorted list
        todoListView.setItems(sortedList);
        // By default user can select multiple items at a time
        // To avoid this, making user to select single item at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // Code to always make the first item of the list selected(Default selection)
        todoListView.getSelectionModel().selectFirst();

        // Implementing CellFactory
        // Setting the different colors to display the items whether deadline is today pr before today or deadline is tomorrow
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
                // Deleting the record
                cell.emptyProperty().addListener(
                        (obs, wadEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });

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

    /**
     * Writing a key event for todoListView
     * When the delete button on keyboard is get pressed then this method is get called and asks to delete the selected item
     *
     * @Param keyEvent accepts the key event
     */
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        // Checking if the item is get selected
        if (selectedItem != null) {
            // Checking if the delete is called
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                // Deleting the item
                deleteItem(selectedItem);
            }
        }
    }

    /**
     * Method to delete the selected item
     */
    public void deleteItem(TodoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delete item: " + item.getTitle());
        alert.setContentText("Are you sure? Press OK to confirm and cancel to back");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            TodoData.getInstance().deleteTodoItem(item);
        }
    }

    /**
     * Method to handle the filter button on main window
     */
    @FXML
    public void handleFilterButton() {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (filterToggleButton.isSelected()) {
            // If the deadline for todoItem is today then the todoItem is displayed once button is selected
            filteredList.setPredicate(wantTodaysItems);
            if (filteredList.isEmpty()) {
                itemDetailsTextArea.clear();
                deadLineLabel.setText("");
            } else if (filteredList.contains(selectedItem)) {
                todoListView.getSelectionModel().select(selectedItem);
            } else {
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            // If the button is not selected then showing all the items
            filteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }
    }

    /**
     * Event handler for exit option in file menu
     */
    @FXML
    public void handleExit() {
        Platform.exit();
    }
}

// by Ankush Chavan