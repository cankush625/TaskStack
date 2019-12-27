package com.cankush.todolist.database;

import com.cankush.todolist.datamodel.TodoItem;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class MysqlConnect {
    private static ObservableList<TodoItem> todoItems;
    private static DateTimeFormatter formatter;

    public static void mysqlUpdate(String sql) {
        System.out.println("Inserting values in mysql database table!");

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String db = "taskstack";
        try {
            conn = DriverManager.getConnection(url + db, "cankush", "ankush");
            try {
                Statement st = conn.createStatement();
                System.out.println(sql);
                int val = st.executeUpdate(sql);
                System.out.println("1 row affected + return value: " + val);
            } catch(SQLException s) {
                System.out.println("SQL statement is not executable! Error is: " + s.getMessage());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void mysqlDelete(String sql) {
        System.out.println("Deleting values from mysql database table!");

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String db = "taskstack";
        try {
            conn = DriverManager.getConnection(url + db, "cankush", "ankush");
            try {
                Statement st = conn.createStatement();
                System.out.println(sql);
                boolean val = st.execute(sql);
                System.out.println("1 row affected + return value: " + val);
            } catch(SQLException s) {
                System.out.println("SQL statement is not executable! Error is: " + s.getMessage());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet mysqlDisplay() {
        Connection conn = null;
        Statement st;
        String url = "jdbc:mysql://localhost:3306/";
        String db = "taskstack";
        try {
            //Open a connection
            conn = DriverManager.getConnection(url + db, "cankush", "ankush");
            try {
                st = conn.createStatement();
                String sql = "select * from taskstack.todo";
                System.out.println(sql);
                return st.executeQuery(sql);

            } catch(SQLException s) {
                System.out.println("SQL statement is not executable! Error is: " + s.getMessage());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
