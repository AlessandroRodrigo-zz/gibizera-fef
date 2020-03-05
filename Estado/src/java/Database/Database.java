/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flavio Prado
 */
/*
Na Controller terá que fazer esse esquema antes de de chamar os métodos desta classe

        ConnectionFactory Conn = new ConnectionFactory();
        Connection Connection = Conn.getConnection();
        
        Database cDatabase = new Database(Connection);

Ou apenas Instanciar a classe, tratamento já foi feito pra esse caso
*/
public class Database {
    
    private Connection Connection;
    List<Fields> aFields = new ArrayList<>();

    public Database(Connection Connection) {
        this.Connection = Connection;
    }                

    public Database() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            this.Connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gibizera", "postgres", "postdba");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public static Connection getConnection() throws SQLException, Exception {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/gibizera", "postgres", "postdba");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    private static void Close(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static void CloseConnection(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        Close(conn, stmt, rs);
    }
    public static void CloseConnection(Connection conn, Statement stmt) throws Exception {
        Close(conn, stmt, null);
    }
    public static void CloseConnection(Connection conn) throws Exception {
        Close(conn, null, null);
    }
    
    public enum TypeFields{
        VARCHAR, NUMBER, DATE, INTEGER;
    }
    
    public void ClearFields(){
        aFields.clear();
    }
    
    public void AddFields(String Nome, String Valor, TypeFields Type){
        aFields.add(new Fields(Nome, Valor, Type));
    }
    
    public boolean InsertDB(String psTableName) throws SQLException{        
        String strSQL = ""; 
        String sColunas = "";
        String sValores = "";               

        PreparedStatement Stmt = null;
        
        for (int i = 0; i <= (aFields.size() - 1); i++) {            
            Fields iField = aFields.get(i);
            
            //Se já tiver valor, concatena uma vígula
            if (sColunas.length() > 0){ sColunas += ", "; }
            if (sValores.length() > 0){ sValores += ", "; }
            
            sColunas = sColunas + iField.Nome;
            
            if (iField.Tipo == TypeFields.VARCHAR){
                if (iField.Valor.length() > 0){
                    sValores = sValores + ("'" + iField.Valor + "'");
                }else{
                    sValores = sValores + iField.Valor;
                }
            }
        }               
                
        strSQL = "Insert Into " + psTableName + " (" + sColunas + ") Values (" + sValores + ")";
                    
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.execute();
            
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao cadastrar " + psTableName + "! Erro:" + ex.getMessage());
            ex.printStackTrace();
            
            return false;
        } finally {
            try {                
                CloseConnection(Connection, Stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar conexão de cadastro! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }        
    }
    
    public boolean UpdateDB(String psTableName, String psWhere) throws SQLException{        
        String strSQL = ""; 
        String sColunas = "";

        PreparedStatement Stmt = null;        
               
        for (int i = 0; i <= (aFields.size() - 1); i++) {            
            Fields iField = aFields.get(i);
                        
            //Se já tiver valor, concatena uma vígula
            if (sColunas.length() > 0){ sColunas += ", "; }
            
            sColunas += iField.Nome + " = ";
            
            if (iField.Tipo == TypeFields.VARCHAR){
                if (iField.Valor.length() > 0){
                    sColunas = sColunas + ("'" + iField.Valor + "'");
                }else{
                    sColunas = sColunas + iField.Valor;
                }
            }
        }
        
        strSQL = "Update " + psTableName + " Set " + sColunas + " " + psWhere;
                    
        try {
            Stmt = Connection.prepareStatement(strSQL);

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar " + psTableName + "! Erro:" + ex.getMessage());
            ex.printStackTrace();
            return false;

        } finally {

            try {
                ConnectionFactory.CloseConnection(Connection, Stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }        
    }
    
    public boolean DeleteDB(String psTableName, String psWhere) throws SQLException{        
        String strSQL = ""; 
        String sColunas = "";

        PreparedStatement Stmt = null;                               
        
        strSQL = "Delete " + psTableName + " " + psWhere;
                    
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.execute();
            
            return true;
        } catch (Exception e) {
            System.out.println("Problemas ao excluir " + psTableName +"! Erro: " + e.getMessage());
            return false;
        }        
    }        
    
}
