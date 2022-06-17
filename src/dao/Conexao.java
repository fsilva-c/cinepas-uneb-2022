package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class Conexao {
    
    private static Conexao instance = new Conexao();

    private Conexao() { 
        
    }
    
    public static Connection conectar() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/cinepas","root", "123456");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void desconectar(Connection con){
        try {
            if (con != null) {
                con.close();
            } 
        } catch (SQLException ex) {
                throw new RuntimeException(ex);
        }
    }
    
    public static void desconectar(Connection con, PreparedStatement stmt){
        desconectar(con);
        
        try {
            if (stmt != null) {    //Significa que a conexão está aberto
                stmt.close();
            } 
        }catch (SQLException ex) {
                throw new RuntimeException(ex);
        }
    }
    
    public static void desconectar(Connection con, PreparedStatement stmt, ResultSet rs){
        desconectar(con, stmt);
        
        try {
            if (rs != null) {    //Significa que a conexão está aberto
                rs.close();
            } 
        }catch (SQLException ex) {
                throw new RuntimeException(ex);
        }
    }

    public static Conexao getInstance() {
        return instance;
    }
}