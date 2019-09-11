package me.authbot.BoT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager {
    public static void connect(){
        Connection connection = null;

        try{
            String url = "url"; //write SQLite URL here

            connection = DriverManager.getConnection(url);

            System.out.println("Connection established to " + url);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(connection != null){
                    connection.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args){
        connect();
    }
}

