package me.authbot.BoT;
import java.sql.*;

public class SQLManager {

    private String url = "url goes here";

    /**
     * SQLManager Constructor
     */
    public SQLManager(){
        connect(); //connecting to url
        createTable(); //creating table
    }

    /**
     * Connect to the SQLite Database
     */
    private void connect(){
        Connection connection = null;

        try{
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

    /**
     * Creates a tabled called "players" with columns "IGN" and "Verified"
     */
    private void createTable(){

        String sql = "CREATE TABLE IF NOT EXISTS players (\n"
                + " ign text PRIMARY KEY, \n"
                + " verified booelan NOT NULL\n"
                +");";

        try{
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    /**
     * Inserts records into the table
     * @param ign - Player's in game name
     * @param verified - whether or not the player is verified
     */
    public void insert(String ign, boolean verified){

        String sql = "INSERT INTO players(ign, verified) VALUES(?,?)";

        try{
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, ign);
            pstmt.setBoolean(2, verified);


        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

