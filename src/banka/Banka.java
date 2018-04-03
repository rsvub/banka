/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banka;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Student
 */
public class Banka {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/banka";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "123456789";

    public static void main(String[] argv) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement preparedStatementInsert = null;
        PreparedStatement preparedStatementUpdate = null;

        String insertTableSQL = "INSERT INTO TRANSACTION "
                + "(IDACCOUNT, ACCOUNTMOVE) VALUES"
                + "(?,?)";

        String updateTableSQL = "UPDATE ACCOUNT SET BALANCE = BALANCE + ? "
                + "WHERE IDACCOUNT = ?";

        try {
            dbConnection = getDBConnection();

            dbConnection.setAutoCommit(false);

            preparedStatementInsert = dbConnection.prepareStatement(insertTableSQL);
            preparedStatementInsert.setInt(1, 1);
            preparedStatementInsert.setInt(2, 200);
            preparedStatementInsert.executeUpdate();

            preparedStatementUpdate = dbConnection.prepareStatement(updateTableSQL);
            preparedStatementUpdate.setInt(1, 200);
            preparedStatementUpdate.setInt(2, 1);
            preparedStatementUpdate.executeUpdate();

            dbConnection.commit();

            System.out.println("Done!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            dbConnection.rollback();

        } finally {

            if (preparedStatementInsert != null) {
                preparedStatementInsert.close();
            }

            if (preparedStatementUpdate != null) {
                preparedStatementUpdate.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = (Connection) DriverManager.getConnection(DB_URL, USER,PASS);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }
    
    private static String getDate(){
        LocalDate localDate = LocalDate.now();
        String cDate = localDate.toString();
        return cDate;
    }


}
