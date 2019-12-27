package com.cankush.todolist;

import com.cankush.todolist.datamodel.TodoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        primaryStage.setTitle("Todo List");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
//        try {
//            // Storing the items to the file/database
////            TodoData.getInstance().storeTodoItems();
//            TodoData.getInstance().addNewItem();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

    // Every time the application starts this will load the list of todoItems
    @Override
    public void init() throws Exception {
        try {
            // Loading the items from the file/database at the starting of the application
//            TodoData.getInstance().loadTodoItems();
        TodoData.getInstance().displayTodoItem();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
