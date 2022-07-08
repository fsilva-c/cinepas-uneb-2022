package dao;

import model.Cliente;
import model.Estudante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    private Connection conn;
    private final String Table = "cliente";

    public ClienteDao() {
        this.conn = Conexao.getInstance().conectar();
    }

    public boolean update(Cliente cliente) {
        PreparedStatement stmt = null;
        int tipoEstudante = (cliente instanceof Estudante) ? 1 : 0;
        try {
            stmt = this.conn.prepareStatement("UPDATE " + this.Table
                    + " SET nome = ?, tipoEstudante = ?, carteirinha = ? WHERE cpf = ?");
            stmt.setString(1, cliente.getNome());
            stmt.setInt(2, tipoEstudante);
            stmt.setString(3, tipoEstudante == 1 ? ((Estudante) cliente).getCarteirinha() : null);
            stmt.setString(4, cliente.getCpf());

            stmt.executeUpdate();

            this.conn.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return true;
    }

    public boolean insert(Cliente cliente) {
        PreparedStatement stmt = null;
        int tipoEstudante = (cliente instanceof Estudante) ? 1 : 0;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement(
                    "INSERT INTO " + this.Table
                            + "(cpf,nome,tipoEstudante,carteirinha) VALUES(?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setInt(3, tipoEstudante);
            stmt.setString(4, tipoEstudante == 1 ? ((Estudante) cliente).getCarteirinha() : null);

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean delete(Cliente cliente) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("DELETE FROM " + this.Table + " WHERE cpf = ?");
            stmt.setString(1, cliente.getCpf());

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public List<Cliente> select() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente;
                if (rs.getInt("tipoEstudante") == 1) {
                    cliente = new Estudante();
                    ((Estudante) cliente).setCarteirinha(rs.getString("carteirinha"));
                } else {

                    cliente = new Cliente();
                }
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNome(rs.getString("nome"));

                clientes.add(cliente);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    public Cliente select(Cliente cliente) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cli = null;
        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE cpf = ?");
            stmt.setString(1, cliente.getCpf());
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt("tipoEstudante") == 1) {
                    cli = new Estudante();
                    ((Estudante) cli).setCarteirinha(rs.getString("carteirinha"));
                } else {
                    cli = new Cliente();
                }
                cli.setCpf(rs.getString("cpf"));
                cli.setNome(rs.getString("nome"));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cli;
    }
}
