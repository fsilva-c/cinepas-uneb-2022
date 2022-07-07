package dao;

import model.SalaProjecao;
import model.sessao.Sessao;
import model.Filme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessaoDao {
    
    private Connection conn;
    private final String Table = "sessao";

    public SessaoDao() {
        this.conn = Conexao.getInstance().conectar();
    }

    public boolean insert(Sessao sessao, SalaProjecao sala, Filme filme) {
        PreparedStatement stmt = null;

        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement(
                "INSERT INTO " + this.Table + 
                "(horario, vagasOcupadas, idsalaprojecao, idfilme) VALUES(?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, sessao.getHorario());
            stmt.setInt(2, sessao.getVagasOcupadas());
            stmt.setInt(3, sala.getId());
            stmt.setInt(4, filme.getId());

            // Execução da SQL
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sessao.setId(rs.getInt(1));
            }
            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean update(Sessao sessao, SalaProjecao sala, Filme filme) {
        PreparedStatement stmt = null;

        try {
            stmt = this.conn.prepareStatement(
                "UPDATE " + this.Table + " SET horario=?, vagasOcupadas=?, idsalaprojecao=?, idfilme=? WHERE id=?"
            );

            stmt.setString(1, sessao.getHorario());
            stmt.setInt(2, sessao.getVagasOcupadas());
            stmt.setInt(3, sala.getId());
            stmt.setInt(4, filme.getId());

            stmt.executeUpdate();

            this.conn.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

        return true;
    }

    public boolean delete(Sessao sessao) {
        PreparedStatement stmt = null;

        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("DELETE FROM " + this.Table + " WHERE id=?");
            stmt.setInt(1, sessao.getId());

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public List<Sessao> select() {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Sessao> sessoes = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM" + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Sessao sessao = new Sessao();
                
                sessao.setId(rs.getInt("id"));
                sessao.setHorario(rs.getString("horario"));
                sessao.setVagasOcupadas(rs.getInt("vagasOcupadas"));
                
                sessoes.add(sessao);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sessoes;
    }

    public Sessao select(Sessao sessao) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE id=?");

            stmt.setInt(1, sessao.getId());
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                sessao.setHorario(rs.getString("horario"));
                sessao.setVagasOcupadas(rs.getInt("vagasOcupadas"));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sessao;
    }

}
