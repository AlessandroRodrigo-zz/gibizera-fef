/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Flavio Prado
 */
public class ConnectionFactory {
    
    public static Connection getConnection() throws SQLException, Exception{
        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5433/gibizera", "postgres", "docker");
        } catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    private static void Close(Connection conn, Statement stmt, ResultSet rs) throws Exception{
        try{
            if (rs != null){
                rs.close();
            }
            if (stmt != null){
                stmt.close();
            }
            if (conn != null){
                conn.close();
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    public static void CloseConnection(Connection conn, Statement stmt, ResultSet rs) throws Exception{
        Close(conn, stmt, rs);
    }
    public static void CloseConnection(Connection conn, Statement stmt) throws Exception{
        Close(conn, stmt, null);
    }
    public static void CloseConnection(Connection conn) throws Exception{
        Close(conn, null, null);
    }
}
