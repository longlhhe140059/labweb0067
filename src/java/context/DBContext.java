/*
 * Copyright(C) 2021,  LongLHHE140059
 * J3.L.P0065
 * DBContext
 *
 * Record of change:
 * DATE           Version    AUTHOR                   DESCRIPTION
 * 10/12/2021      1.0       LongLH       
 */
package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Connect with the database and close all of connection
 * <p>
 * Bugs: None
 *
 * @author LongLH
 */
public class DBContext {
    /**
     * constructor of DBContext
     */
    public DBContext() {
    }
    /**
     * To connect with database
     * @return connection
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FeastBooking;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String pass = "123456";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }

    /**
     * Close ResultSet,Prepared Statement and Connection
     *
     * @param rs is the ResultSet. It is a 
     * <code>java.sql.ResultSet</code> object 
     * @param ps is the PreparedStatement. It is a
     * <code>java.sql.PreparedStatement</code> object
     * @param connection the Connection. It is a 
     * <code>java.sql.Connection</code> object
     * @throws SQLException
     */
    public void closeConnection(Connection connection, PreparedStatement ps, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

}
