/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * create and instance a connection with The Sqlite database
 * @author Danich Hang
 * @author Veaceslav Vlas
 */
public class DBConnection {
    private static Connection con;
    
    /**
     * Create connection, if the connection is already exist return the same connection
     * @return The Connection
     * @throws Exception 
     */
    public static Connection getSingleInstance() throws Exception {
        if (con == null) {
            con = getConnection();
        }
        return con;
    }
    
    /**
     * Connection to the Sqlite Database
     * @return
     * @throws Exception 
     */
    private static Connection getConnection() throws Exception {
        String url = "jdbc:sqlite:C:\\Users\\danic\\OneDrive\\Desktop\\Project_Cv\\ProgramingPat\\FinalProject\\Libray\\SQLITE\\Database\\data.db";
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection(url);
        return con;
    }
    
    /**
     * Execute The given Query
     * @param query is the given String to update or add a data in the database
     * @throws SQLException 
     */
    public static void executeUpdate(String query) throws SQLException{
        Statement statement = con.createStatement();
        statement.executeUpdate(query);
    }
    
    /**
     * Execute The given Query
     * @param query is the given string to retrieve the data from the database
     * @return The resultSet of the Execute query
     * @throws SQLException 
     */
    public static ResultSet excuteQuery(String query) throws SQLException{
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }
    

}
