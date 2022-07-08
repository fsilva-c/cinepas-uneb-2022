package dao;

import model.Cliente;
import model.ingresso.*;
import model.sessao.Sessao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngressoDao {
    private Connection conn;
    private final String Table = "ingresso";

    public IngressoDao() {
        this.conn = Conexao.getInstance().conectar();
    }

    public boolean update(IIngresso ingresso) {
        PreparedStatement stmt = null;

        int tipoEspecial = 0;
        int tipoFimDeSemana = 0;
        int tipoMeiaEntrada = 0;
        IIngresso ingressoTypeTester = ingresso;

        while (ingressoTypeTester instanceof IngressoBase) {

            if (ingressoTypeTester.getClass() == IngressoEspecial.class)
                tipoEspecial = 1;
            if (ingressoTypeTester.getClass() == IngressoFds.class)
                tipoFimDeSemana = 1;
            if (ingressoTypeTester.getClass() == IngressoMeia.class)
                tipoMeiaEntrada = 1;
            ingressoTypeTester = ((IngressoBase) ingressoTypeTester).getIngresso();
        }

        try {
            stmt = this.conn.prepareStatement("UPDATE " + this.Table
                    + " SET idcliente = ?, preco = ?, tipoEspecial = ?, tipoFimDeSemana = ?, tipoMeiaEntrada = ? WHERE id = ?");
            stmt.setString(1, ingresso.getCliente().getCpf());
            stmt.setFloat(2, ingresso.calcValor());
            stmt.setInt(3, tipoEspecial);
            stmt.setInt(4, tipoFimDeSemana);
            stmt.setInt(5, tipoMeiaEntrada);

            stmt.executeUpdate();

            this.conn.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return true;
    }

    public boolean insert(IIngresso ingresso, Sessao sessao) {
        PreparedStatement stmt = null;

        int tipoEspecial = 0;
        int tipoFimDeSemana = 0;
        int tipoMeiaEntrada = 0;
        IIngresso ingressoTypeTester = ingresso;

        while (ingressoTypeTester instanceof IngressoBase) {

            if (ingressoTypeTester.getClass() == IngressoEspecial.class)
                tipoEspecial = 1;
            if (ingressoTypeTester.getClass() == IngressoFds.class)
                tipoFimDeSemana = 1;
            if (ingressoTypeTester.getClass() == IngressoMeia.class)
                tipoMeiaEntrada = 1;
            ingressoTypeTester = ((IngressoBase) ingressoTypeTester).getIngresso();
        }

        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement(
                    "INSERT INTO " + this.Table
                            + "(idcliente,idsessao,preco,tipoEspecial,tipoFimDeSemana,tipoMeiaEntrada) VALUES(?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, ingresso.getCliente().getCpf());
            stmt.setInt(2, sessao.getId());
            stmt.setFloat(3, ingresso.calcValor());
            stmt.setInt(4, tipoEspecial);
            stmt.setInt(5, tipoFimDeSemana);
            stmt.setInt(6, tipoMeiaEntrada);

            // Execução da SQL
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                ingresso.setId(rs.getInt(1));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean delete(IIngresso ingresso) {
        PreparedStatement stmt = null;
        try {
            // Passagem de parametros
            stmt = this.conn.prepareStatement("DELETE FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, ingresso.getId());

            // Execução da SQL
            stmt.executeUpdate();

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public List<IIngresso> select() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<IIngresso> ingressos = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                IIngresso ingresso = new IngressoBase(new Ingresso());
                if (rs.getInt("tipoEspecial") == 1)
                    ingresso = new IngressoEspecial(ingresso);
                if (rs.getInt("tipoFimDeSemana") == 1) {
                    ingresso = new IngressoFds(ingresso);
                }
                if (rs.getInt("tipoMeiaEntrada") == 1) {
                    ingresso = new IngressoMeia(ingresso);
                }

                ingresso.setPreco(rs.getFloat("preco"));
                ClienteDao clienteDao = new ClienteDao();
                Cliente cliente = new Cliente();
                cliente.setCpf(rs.getString("idcliente"));
                ingresso.setCliente(clienteDao.select(cliente));
                ingresso.setId(rs.getInt("id"));
                ingressos.add(ingresso);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingressos;
    }

    public List<IIngresso> select(Sessao sessao) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<IIngresso> ingressos = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE idsessao = ?");
            stmt.setInt(1, sessao.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                IIngresso ingresso = new IngressoBase(new Ingresso());
                if (rs.getInt("tipoEspecial") == 1)
                    ingresso = new IngressoEspecial(ingresso);
                if (rs.getInt("tipoFimDeSemana") == 1) {
                    ingresso = new IngressoFds(ingresso);
                }
                if (rs.getInt("tipoMeiaEntrada") == 1) {
                    ingresso = new IngressoMeia(ingresso);
                }

                ingresso.setPreco(rs.getFloat("preco"));
                ClienteDao clienteDao = new ClienteDao();
                Cliente cliente = new Cliente();
                cliente.setCpf(rs.getString("idcliente"));
                ingresso.setCliente(clienteDao.select(cliente));
                ingresso.setId(rs.getInt("id"));
                ingressos.add(ingresso);
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingressos;
    }

    public IIngresso select(IIngresso _ingresso) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        IIngresso ingresso = null;
        try {
            stmt = this.conn.prepareStatement("SELECT * FROM " + this.Table + " WHERE id = ?");
            stmt.setInt(1, _ingresso.getId());
            rs = stmt.executeQuery();
            while (rs.next()) {
                ingresso = new IngressoBase(new Ingresso());
                if (rs.getInt("tipoEspecial") == 1)
                    ingresso = new IngressoEspecial(ingresso);
                if (rs.getInt("tipoFimDeSemana") == 1) {
                    ingresso = new IngressoFds(ingresso);
                }
                if (rs.getInt("tipoMeiaEntrada") == 1) {
                    ingresso = new IngressoMeia(ingresso);
                }

                ingresso.setPreco(rs.getFloat("preco"));
                ClienteDao clienteDao = new ClienteDao();
                Cliente cliente = new Cliente();
                cliente.setCpf(rs.getString("idcliente"));
                ingresso.setCliente(clienteDao.select(cliente));
                ingresso.setId(rs.getInt("id"));
            }

            this.conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingresso;
    }

    public boolean deleteCanceled(Sessao sessao) {
        String inMembers = "";
        int nIngressos = sessao.getIngressos().size();
        for (int i = 0; i < nIngressos; i++) {
            inMembers = inMembers.concat(",?");
        }

        if (inMembers.length() > 1) {
            inMembers = inMembers.substring(1);
            System.out.println(inMembers);
            PreparedStatement stmt = null;
            try {
                // Passagem de parametros
                stmt = this.conn
                        .prepareStatement(
                                "DELETE FROM " + this.Table + " WHERE id not in (" + inMembers + ") and idsessao = ?");
                for (int i = 0; i < nIngressos; i++) {
                    stmt.setInt(i + 1, sessao.getIngressos().get(i).getId());
                }
                stmt.setInt(nIngressos + 1, sessao.getId());

                // Execução da SQL
                stmt.executeUpdate();

                this.conn.close();
                stmt.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

}
