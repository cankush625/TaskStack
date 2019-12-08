package com.cankush.todolist;

import com.cankush.todolist.datamodel.TodoData;
import com.cankush.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField titleField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private DatePicker deadlinePicker;

    public TodoItem processResults() {
        String title = titleField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadlinePicker.getValue();

        TodoItem newItem = new TodoItem(title, details, deadlineValue);
        // Adding newItem to the database/datafile
        TodoData.getInstance().addTodoItem(newItem);
        return newItem;
    }
}
