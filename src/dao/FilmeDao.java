package dao;

import model.Filme;
import model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmeDao {
    private Connection conn;
    private final String Table = "filme";

    public FilmeDao() {
        this.conn = Conexao.getInstance().conectar();
    }

    public boolean update(Filme filme) {
        PreparedStatement stmt = null;
        try {
            stmt = this.conn.prepareStatement("UPDATE " + this.Table
                    + " SET titulo = ?, diretor = ?, atorPrincial = ?, duracao = ?, classicacao = ?, idcategoria = ? WHERE id = ?");
            stmt.setString(1, filme.getTitulo());
            stmt.setString(2, filme.getDiretor());
            stmt.setString(3, filme.getAtorPrincipal());
            stmt.setInt(4, filme.getDuracao());
            stmt.setInt(5, filme.getClassificacaoEtaria());
            stmt.setInt(6, filme.getCategoria().getId());
            stmt.setInt(7, filme.getId());
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return true;
    }

    public boolean insert(Filme filme) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement(
                    "INSERT INTO " + this.Table
                            + "(titulo,diretor,atorPrincipal,duracao,classificacao,idcategoria) VALUES(?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, filme.getTitulo());
            stmt.setString(2, filme.getDiretor());
            stmt.setString(3, filme.getAtorPrincipal());
            stmt.setInt(4, filme.getDuracao());
            stmt.setInt(5, filme.getClassificacaoEtaria());
            stmt.setInt(6, filme.getCategoria().getId());

            // Execução da SQL
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                filme.setId(rs.getInt(1));
            }
            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean delete(Filme filme) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("DELETE FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, filme.getId());

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public List<Filme> select() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Filme> filmes = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM" + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Filme filme = new Filme();
                filme.setId(rs.getInt("id"));
                filme.setTitulo(rs.getString("titulo"));
                filme.setDiretor(rs.getString("diretor"));
                filme.setAtorPrincipal(rs.getString("atorPrincipal"));
                filme.setDuracao(rs.getInt("duracao"));
                filme.setClassificacaoEtaria(rs.getInt("classificacao"));

                Categoria cat = new Categoria();
                cat.setId(rs.getInt("idcategoria"));
                CategoriaDao catDao = new CategoriaDao();

                filme.setCategoria(catDao.select(cat));
                filmes.add(filme);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmes;
    }

    public Filme select(Filme filme) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, filme.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                filme.setId(rs.getInt("id"));
                filme.setTitulo(rs.getString("titulo"));
                filme.setDiretor(rs.getString("diretor"));
                filme.setAtorPrincipal(rs.getString("atorPrincipal"));
                filme.setDuracao(rs.getInt("duracao"));
                filme.setClassificacaoEtaria(rs.getInt("classificacao"));

                Categoria cat = new Categoria();
                cat.setId(rs.getInt("idcategoria"));
                CategoriaDao catDao = new CategoriaDao();

                filme.setCategoria(catDao.select(cat));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filme;
    }
}
