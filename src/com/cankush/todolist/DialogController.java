package com.cankush.todolist;

import com.cankush.todolist.database.MysqlConnect;
import com.cankush.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        String deadline = deadlineValue.toString();
        TodoItem newItem = new TodoItem(title, details, deadlineValue);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formatting the date
        LocalDate date = LocalDate.parse(deadline, formatter);

        // Adding newItem to the database/datafile
        String sql = "insert into taskstack.todo" + " values('" + title + "'" + "," + "'" + details + "'" + "," + "'" + date + "')";
        MysqlConnect.mysqlUpdate(sql);
        return newItem;
    }
}
