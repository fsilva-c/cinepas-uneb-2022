package dao;

import model.Cinema;
import model.Shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaDao {
    private Connection conn;
    private final String Table = "cinema";

    public CinemaDao() {
        this.conn = Conexao.getInstance().conectar();
    }

    public boolean update(Cinema cinema) {
        PreparedStatement stmt = null;
        try {
            stmt = this.conn.prepareStatement("UPDATE " + this.Table + " SET nome = ?, idshopping = ? WHERE id = ?");
            stmt.setString(1, cinema.getNome());
            stmt.setInt(2, cinema.getId());
            stmt.setInt(2, cinema.getShopping().getId());
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return true;
    }

    public boolean insert(Cinema cinema) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("INSERT INTO " + this.Table + "(nome, idshopping) VALUES(?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cinema.getNome());
            stmt.setInt(2, cinema.getShopping().getId());

            // Execução da SQL
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cinema.setId(rs.getInt(1));
            }
            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean delete(Cinema cinema) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("DELETE FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, cinema.getId());

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public List<Cinema> select() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cinema> cinemas = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM" + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setId(rs.getInt("id"));
                cinema.setNome(rs.getString("nome"));
                
                Shopping shopping = new Shopping();
                shopping.setId(rs.getInt("idcategoria"));    
                ShoppingDao shoppingDao = new ShoppingDao();

                cinema.setShopping(shoppingDao.select(shopping));

                cinemas.add(cinema);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cinemas;
    }

    public Cinema select(Cinema cinema) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, cinema.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                cinema.setNome(rs.getString("nome"));
                Shopping shopping = new Shopping();
                shopping.setId(rs.getInt("idcategoria"));    
                ShoppingDao shoppingDao = new ShoppingDao();

                cinema.setShopping(shoppingDao.select(shopping));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cinema;
    }
}
