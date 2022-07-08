package dao;

import model.SalaProjecao;
import model.SalaProjecao3D;
import model.Cinema;
import model.sessao.*;
import model.Filme;
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

            for (Sessao sessao : sala.getSessoes()) {
                SessaoDao sDao = new SessaoDao();
                sDao.insert(sessao, sala);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean update(SalaProjecao sala) {
        PreparedStatement stmt = null;

        try {
            stmt = this.conn.prepareStatement(
                    "UPDATE " + this.Table
                            + " SET numero = ?, capacidade = ?, tipo3d = ?, equipamentos = ?, idcinema = ?  WHERE id = ?");

            stmt.setString(1, sala.getNumero());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala instanceof SalaProjecao3D ? 1 : 0);
            stmt.setString(4, sala instanceof SalaProjecao3D ? ((SalaProjecao3D) sala).getEquipamentos() : null);

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

            for (Sessao sessao : sala.getSessoes()) {
                SessaoDao sDao = new SessaoDao();
                sDao.delete(sessao);
            }

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
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                SalaProjecao sala;
                if (rs.getInt("tipo3d") == 1) {
                    sala = new SalaProjecao3D();
                    ((SalaProjecao3D) sala).setEquipamentos(rs.getString("equipamentos"));
                } else {
                    sala = new SalaProjecao();
                }

                sala.setId(rs.getInt("id"));
                sala.setNumero(rs.getString("numero"));
                sala.setCapacidade(rs.getInt("capacidade"));

                SessaoDao sDao = new SessaoDao();
                sala.setSessoes(sDao.select(sala));

                salas.add(sala);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salas;
    }

    public List<SalaProjecao> select(Cinema cinema) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<SalaProjecao> salas = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE idcinema = ?");

            stmt.setInt(1, cinema.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                SalaProjecao sala;
                if (rs.getInt("tipo3d") == 1) {
                    sala = new SalaProjecao3D();
                    ((SalaProjecao3D) sala).setEquipamentos(rs.getString("equipamentos"));
                } else {
                    sala = new SalaProjecao();
                }

                sala.setId(rs.getInt("id"));
                sala.setNumero(rs.getString("numero"));
                sala.setCapacidade(rs.getInt("capacidade"));

                SessaoDao sDao = new SessaoDao();
                sala.setSessoes(sDao.select(sala));

                salas.add(sala);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salas;
    }

    public SalaProjecao select(Sessao sessao) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        SalaProjecao sala = null;
        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table
                    + " WHERE id = (SELECT idsalaprojecao FROM sessao WHERE id = ?)");
            // stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " INNER
            // JOIN sessao ON sessao.idsalaprojecao = id WHERE sessao.id = ?");

            stmt.setInt(1, sessao.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {

                if (rs.getInt("tipo3d") == 1) {
                    sala = new SalaProjecao3D();
                    ((SalaProjecao3D) sala).setEquipamentos(rs.getString("equipamentos"));
                } else {
                    sala = new SalaProjecao();
                }

                sala.setId(rs.getInt("id"));
                sala.setNumero(rs.getString("numero"));
                sala.setCapacidade(rs.getInt("capacidade"));

                SessaoDao sDao = new SessaoDao();
                sala.setSessoes(sDao.select(sala));

            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sala;
    }

    public List<SalaProjecao> select(String datahora) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<SalaProjecao> salas = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement(
                    "SELECT DISTINCT salaprojecao.id, salaprojecao.numero, salaprojecao.capacidade, salaprojecao.tipo3d, salaprojecao.equipamentos FROM "
                            + this.Table
                            + " INNER JOIN sessao ON salaprojecao.id = sessao.idsalaprojecao WHERE sessao.horario LIKE ?");
            // stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " INNER
            // JOIN sessao ON sessao.idsalaprojecao = id WHERE sessao.id = ?");

            stmt.setString(1, "%" + datahora + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                SalaProjecao sala;
                if (rs.getInt("tipo3d") == 1) {
                    sala = new SalaProjecao3D();
                    ((SalaProjecao3D) sala).setEquipamentos(rs.getString("equipamentos"));
                } else {
                    sala = new SalaProjecao();
                }

                sala.setId(rs.getInt("id"));
                sala.setNumero(rs.getString("numero"));
                sala.setCapacidade(rs.getInt("capacidade"));

                SessaoDao sDao = new SessaoDao();
                sala.setSessoes(sDao.select(sala, datahora));

                salas.add(sala);

            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salas;
    }

    public List<SalaProjecao> select(Filme filme) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<SalaProjecao> salas = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement(
                    "SELECT DISTINCT salaprojecao.id, salaprojecao.numero, salaprojecao.capacidade, salaprojecao.tipo3d, salaprojecao.equipamentos FROM "
                            + this.Table
                            + " INNER JOIN sessao ON salaprojecao.id = sessao.idsalaprojecao WHERE sessao.idfilme = ?");
            // stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " INNER
            // JOIN sessao ON sessao.idsalaprojecao = id WHERE sessao.id = ?");

            stmt.setInt(1, filme.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                SalaProjecao sala;
                if (rs.getInt("tipo3d") == 1) {
                    sala = new SalaProjecao3D();
                    ((SalaProjecao3D) sala).setEquipamentos(rs.getString("equipamentos"));
                } else {
                    sala = new SalaProjecao();
                }

                sala.setId(rs.getInt("id"));
                sala.setNumero(rs.getString("numero"));
                sala.setCapacidade(rs.getInt("capacidade"));

                SessaoDao sDao = new SessaoDao();
                sala.setSessoes(sDao.select(sala));

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
                SessaoDao sDao = new SessaoDao();
                sala.setSessoes(sDao.select(sala));

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
