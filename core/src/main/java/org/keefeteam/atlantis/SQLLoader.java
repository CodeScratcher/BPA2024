package org.keefeteam.atlantis;

import java.sql.*;

public class SQLLoader {
    Statement statement;
    public SQLLoader(String q) {
        //Connect to MySQL
        //This is not easy
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/" + q;
        String user = "root";
        String password = "Roman2008@";//I have no clue what to put here

        // Initialize the connection object
        Connection connection = null;
        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();

            // If connection is successful
            System.out.println("Connected to the database!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Include the driver in your library path.");
            e.printStackTrace();
            System.out.println("error");

        } catch (SQLException e) {
            System.out.println("Connection failed! Check output console.");
            e.printStackTrace();
            System.out.println("error");
        }

    }
    public ResultSet select(String tQu) {
        //This executes the query and returns rows
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(tQu);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
}
