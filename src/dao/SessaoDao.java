package dao;

import model.SalaProjecao;
import model.sessao.Sessao;
import model.ingresso.*;
import model.Filme;
import model.datahora.*;
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

    public boolean insert(Sessao sessao, SalaProjecao sala) {
        PreparedStatement stmt = null;

        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement(
                    "INSERT INTO " + this.Table +
                            "(horario, vagasOcupadas, idsalaprojecao, idfilme) VALUES(?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, sessao.getHorario().sqlDateTime());
            stmt.setInt(2, sessao.getVagasOcupadas());
            stmt.setInt(3, sala.getId());
            stmt.setInt(4, sessao.getFilme().getId());

            // Execução da SQL
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sessao.setId(rs.getInt(1));
            }

            for (IIngresso ingresso : sessao.getIngressos()) {
                IngressoDao iDao = new IngressoDao();
                iDao.insert(ingresso, sessao);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean update(Sessao sessao) {
        PreparedStatement stmt = null;

        try {
            stmt = this.conn.prepareStatement(
                    "UPDATE " + this.Table + " SET horario=?, vagasOcupadas=?, idfilme=? WHERE id=?");

            stmt.setString(1, sessao.getHorario().sqlDateTime());
            stmt.setInt(2, sessao.getVagasOcupadas());
            stmt.setInt(3, sessao.getFilme().getId());

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

            for (IIngresso ingresso : sessao.getIngressos()) {
                IngressoDao iDao = new IngressoDao();
                iDao.delete(ingresso);
            }

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
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Sessao sessao = new Sessao();

                sessao.setId(rs.getInt("id"));
                sessao.setHorario(new AdapterDataHora(rs.getString("horario"), "yyyy-MM-dd HH:mm:ss"));
                sessao.setVagasOcupadas(rs.getInt("vagasOcupadas"));

                Filme filme = new Filme();
                filme.setId(rs.getInt("idfilme"));
                FilmeDao fDao = new FilmeDao();
                sessao.setFilme(fDao.select(filme));

                IngressoDao iDao = new IngressoDao();
                sessao.setIngressos(iDao.select(sessao));
                sessoes.add(sessao);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sessoes;
    }

    public List<Sessao> select(SalaProjecao sala) {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Sessao> sessoes = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE idsalaprojecao=?");
            stmt.setInt(1, sala.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Sessao sessao = new Sessao();

                sessao.setId(rs.getInt("id"));
                sessao.setHorario(new AdapterDataHora(rs.getString("horario"), "yyyy-MM-dd HH:mm:ss"));
                sessao.setVagasOcupadas(rs.getInt("vagasOcupadas"));

                Filme filme = new Filme();
                filme.setId(rs.getInt("idfilme"));
                FilmeDao fDao = new FilmeDao();
                sessao.setFilme(fDao.select(filme));

                IngressoDao iDao = new IngressoDao();
                sessao.setIngressos(iDao.select(sessao));
                sessoes.add(sessao);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sessoes;
    }

    public List<Sessao> select(SalaProjecao sala, String datahora) {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Sessao> sessoes = new ArrayList<>();

        try {
            stmt = this.conn
                    .prepareStatement("SELECT * FROM " + this.Table + " WHERE idsalaprojecao=? and horario Like ?");
            stmt.setInt(1, sala.getId());
            stmt.setString(2, "%" + datahora + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Sessao sessao = new Sessao();

                sessao.setId(rs.getInt("id"));
                sessao.setHorario(new AdapterDataHora(rs.getString("horario"), "yyyy-MM-dd HH:mm:ss"));
                sessao.setVagasOcupadas(rs.getInt("vagasOcupadas"));

                Filme filme = new Filme();
                filme.setId(rs.getInt("idfilme"));
                FilmeDao fDao = new FilmeDao();
                sessao.setFilme(fDao.select(filme));

                IngressoDao iDao = new IngressoDao();
                sessao.setIngressos(iDao.select(sessao));
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
                sessao.setHorario(new AdapterDataHora(rs.getString("horario"), "yyyy-MM-dd HH:mm:ss"));
                sessao.setVagasOcupadas(rs.getInt("vagasOcupadas"));
                Filme filme = new Filme();
                filme.setId(rs.getInt("idfilme"));
                FilmeDao fDao = new FilmeDao();
                sessao.setFilme(fDao.select(filme));
                IngressoDao iDao = new IngressoDao();
                sessao.setIngressos(iDao.select(sessao));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sessao;
    }

}
