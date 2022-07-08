package view;

import model.Cliente;
import model.Estudante;
import dao.ClienteDao;
import java.util.*;

public abstract class ClienteView {
    private static ArrayList<Cliente> clientes = null;

    public static void setarLista() {
        ClienteDao shoppingDao = new ClienteDao();
        clientes = new ArrayList<Cliente>(shoppingDao.select());
    }

    public static void listarClientes() {
        setarLista();
        System.out.println("\nLista de clientes:");
        for (Cliente cliente : clientes) {
            // String ehEstudante = (cliente instanceof Estudante)
            // ? " Carteirinha: " + ((Estudante) cliente).getCarteirinha()
            // : "";
            // System.out.println("CPF: " + cliente.getCpf() + "Cliente: " +
            // cliente.getNome() + ehEstudante);
            System.out.println("");
            System.out.println(cliente.toString());
        }
        System.out.println("<===========================================>");
    }

    public static Cliente selecionarCliente() {
        listarClientes();
        System.out.println("CPF do cliente: ");
        String cpf = System.console().readLine();

        if (cpf == "" || cpf == null)
            return null;

        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        return procurarCliente(cliente);
    }

    public static Cliente procurarCliente(Cliente cliente) {
        for (Cliente cli : clientes) {
            if (cliente.getCpf().equals(cli.getCpf())) {
                return cli;
            }
        }
        return null;
    }

    public static void cadastrarCliente() {
        Cliente cliente = null;
        System.out.println("Insira os dados do cliente");
        System.out.println("CPF: ");
        String cpf = System.console().readLine();
        System.out.println("Nome: ");
        String nome = System.console().readLine();
        System.out.println("O Cliente é um Estudante?");
        System.out.println("S/N: ");
        String estudanteCheck = System.console().readLine();
        if (estudanteCheck.toUpperCase().equals("S")) {
            System.out.println("Carteirinha: ");
            String carteirinha = System.console().readLine();
            cliente = new Estudante(cpf, nome, carteirinha);
        } else {
            cliente = new Cliente(cpf, nome);
        }
        ClienteDao clienteDao = new ClienteDao();
        clienteDao.insert(cliente);
    }

    public static void atualizarCliente() {
        Cliente cliente = selecionarCliente();
        if (cliente != null) {
            System.out.println("Insira os novos dados do cliente");
            System.out.println("CPF: ");
            String cpf = System.console().readLine();
            System.out.println("Nome: ");
            String nome = System.console().readLine();
            if (!cpf.equals(" "))
                cliente.setCpf(cpf);
            if (!nome.equals(" "))
                cliente.setNome(nome);
            if (cliente instanceof Estudante) {
                System.out.println("Carteirinha: ");
                String carteirinha = System.console().readLine();
                if (!carteirinha.equals(" "))
                    ((Estudante) cliente).setCarteirinha(carteirinha);
            }
            ClienteDao clienteDao = new ClienteDao();
            clienteDao.update(cliente);
        }
    }

    public static void deletarCliente() {
        Cliente cliente = selecionarCliente();
        if (cliente != null) {
            ClienteDao clienteDao = new ClienteDao();
            clienteDao.delete(cliente);
        }
    }

}