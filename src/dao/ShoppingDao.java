package dao;

import model.Shopping;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingDao {
    private Connection conn;
    private final String Table = "shopping";

    public ShoppingDao() {
        this.conn = Conexao.getInstance().conectar();
    }

    public boolean update(Shopping shop) {
        PreparedStatement stmt = null;
        try {
            stmt = this.conn.prepareStatement("UPDATE " + this.Table + " SET nome = ? WHERE id = ?");
            stmt.setString(1, shop.getNome());
            stmt.setInt(2, shop.getId());
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return true;
    }

    public boolean insert(Shopping shop) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("INSERT INTO " + this.Table + "(nome) VALUES(?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, shop.getNome());

            // Execução da SQL
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                shop.setId(rs.getInt(1));
            }
            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean delete(Shopping shop) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("DELETE FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, shop.getId());

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public List<Shopping> select() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Shopping> Shoppings = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM" + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Shopping shop = new Shopping();
                shop.setId(rs.getInt("id"));
                shop.setNome(rs.getString("nome"));
                Shoppings.add(shop);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Shoppings;
    }

    public Categoria select(Shopping shop) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, shop.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                shop.setNome(rs.getString("nome"));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shop;
    }
}
