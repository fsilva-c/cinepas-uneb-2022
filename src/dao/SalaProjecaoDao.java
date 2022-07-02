package dao;

import model.SalaProjecao;
import model.SalaProjecao3D;
import model.Cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaProjecaoDao {
 
    private Connection conn;
    private final String Table = "salaprojecao";

    public SalaProjecaoDao() {
        this.conn = Conexao.getInstance().conectar();
    }

    public boolean insert(SalaProjecao sala, Cinema cine) {
        PreparedStatement stmt = null;

        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement(
                "INSERT INTO " + this.Table + 
                "(numero, capacidade, tipo3d, equipamentos, idcinema) VALUES(?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, sala.getNumero());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala instanceof SalaProjecao3D ? 1 : 0);
            stmt.setString(4, sala instanceof SalaProjecao3D ? ((SalaProjecao3D) sala).getEquipamentos() : null);
            stmt.setInt(5, cine.getId());

            // Execução da SQL
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sala.setId(rs.getInt(1));
            }
            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean update(SalaProjecao sala, Cinema cine) {
        PreparedStatement stmt = null;

        try {
            stmt = this.conn.prepareStatement(
                "UPDATE " + this.Table + " SET numero = ?, capacidade = ?, tipo3d = ?, equipamentos = ?, idcinema = ?  WHERE id = ?"
            );

            stmt.setString(1, sala.getNumero());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala instanceof SalaProjecao3D ? 1 : 0);
            stmt.setString(4, sala instanceof SalaProjecao3D ? ((SalaProjecao3D) sala).getEquipamentos() : null);
            stmt.setInt(5, cine.getId());

            stmt.executeUpdate();

            this.conn.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

        return true;
    }

    public boolean delete(SalaProjecao sala) {
        PreparedStatement stmt = null;

        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("DELETE FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, sala.getId());

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public List<SalaProjecao> select() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<SalaProjecao> salas = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM" + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                SalaProjecao sala = new SalaProjecao();
                
                sala.setId(rs.getInt("id"));
                sala.setNumero(rs.getString("numero"));
                sala.setCapacidade(rs.getInt("capacidade"));

                if (rs.getInt("tipo3d") == 1) {
                    ((SalaProjecao3D) sala).setEquipamentos(rs.getString("equipamentos"));
                }
                
                salas.add(sala);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salas;
    }

    public SalaProjecao select(SalaProjecao sala) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE id = ?");

            stmt.setInt(1, sala.getId());
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                sala.setNumero(rs.getString("numero"));
                sala.setCapacidade(rs.getInt("capacidade"));
                if (sala instanceof SalaProjecao3D) {
                    ((SalaProjecao3D) sala).setEquipamentos(rs.getString("equipamentos"));
                }
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return sala;
    }

}
