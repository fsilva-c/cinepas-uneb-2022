package dao;

import model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao {
    private Connection conn;
    private final String Table = "categoria_filme";

    public CategoriaDao() {
        this.conn = Conexao.getInstance().conectar();
    }

    public boolean update(Categoria cat) {
        PreparedStatement stmt = null;
        try {
            stmt = this.conn.prepareStatement("UPDATE " + this.Table + " SET nome = ? WHERE id = ?");
            stmt.setString(1, cat.getNome());
            stmt.setInt(2, cat.getId());
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return true;
    }

    public boolean insert(Categoria cat) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("INSERT INTO " + this.Table + "(nome) VALUES(?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cat.getNome());

            // Execução da SQL
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cat.setId(rs.getInt(1));
            }
            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean delete(Categoria cat) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("DELETE FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, cat.getId());

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public List<Categoria> select() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Categoria> categorias = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                categorias.add(cat);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categorias;
    }

    public Categoria select(Categoria cat) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, cat.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                cat.setNome(rs.getString("nome"));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cat;
    }

}
