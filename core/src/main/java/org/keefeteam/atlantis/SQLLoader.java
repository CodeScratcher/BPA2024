package org.keefeteam.atlantis;

import java.sql.*;
/**
 * This is the java class that loads the SQL database
 */
public class SQLLoader {

    private Statement statement;

    /**
     *
     * @param q This the SELECT statement to run
     */
    public SQLLoader(String q) {
        //Connect to MySQL
        //This is not easy
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/" + q;
        String user = "root";
        String password = "Keefe2012";

        // Initialize the connection object
        Connection connection = null;
        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param tQu This is the query
     * @return it returns the row of results
     */
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
