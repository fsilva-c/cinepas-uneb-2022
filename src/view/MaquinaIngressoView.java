package view;

import model.Categoria;
import model.Cinema;
import model.Shopping;

public abstract class MaquinaIngressoView {

    public static void maquinaIngresso() {
        prelucio();

        ShoppingView.selecionarShopping();

        System.out.println("==============================");
    }

    public static void prelucio() {
        System.out.println("==============================");
        System.out.println("========== !CinePas! =========");
        System.out.println("==============================");
    }

    // comprar novo ingresso...

    // listar shoppings...

    // listar sessoes...

    // Menu
    public static void Cadastros() {
        System.out.println("Cadastros:");
        System.out.println("1. Clientes");
        System.out.println("2. Categorias");
        System.out.println("3. Filmes");
        System.out.println("4. Shoppings:");
        System.out.println("5. Cinema:");
        System.out.println("6. Salas de Cinema:");
        System.out.println("7. Sessao:");
        System.out.println("Any. Sair");

        String opcao = System.console().readLine();
        switch (opcao) {
            case "1":
                ClienteView.cadastrarCliente();
                break;
            case "2":
                CategoriaView.cadastrarCategoria();
                break;
            case "3":
                FilmeView.cadastrarFilme();
                break;
            case "4":
                ShoppingView.cadastrarShopping();
                break;
            case "5":
                ShoppingView.setarLista();
                CinemaView.cadastrarCinema(ShoppingView.selecionarShopping());
                break;
            case "6":
                CinemaView.setarLista(null);
                SalaProjecaoView.cadastrarSala(CinemaView.selecionarCinema());
                break;
            case "7":
                SalaProjecaoView.setLista(null);
                SessaoView.cadastrarSessao(SalaProjecaoView.selecionarSala());
                break;
            default:
                return;
        }
        Cadastros();
    }

    public static void Edicoes() {
        System.out.println("Edicoes:");
        System.out.println("1. Clientes");
        System.out.println("2. Categorias");
        System.out.println("3. Filmes");
        System.out.println("4. Shoppings:");
        System.out.println("5. Cinema:");
        System.out.println("6. Salas de Cinema:");
        System.out.println("7. Sessao:");
        System.out.println("8. Cliente:");
        System.out.println("Any. Sair");

        String opcao = System.console().readLine();
        switch (opcao) {
            case "1":
                ClienteView.atualizarCliente();
                break;
            case "2":
                CategoriaView.atualizarCategoria();
                break;
            case "3":
                FilmeView.atualizarFilme();
                break;
            case "4":
                ShoppingView.atualizarShopping();
                break;
            case "5":
                ShoppingView.setarLista();
                CinemaView.setarLista(ShoppingView.selecionarShopping());
                CinemaView.atualizarCinema();
                break;
            case "6":
                CinemaView.setarLista(null);
                SalaProjecaoView.setarLista(CinemaView.selecionarCinema());
                SalaProjecaoView.atualizarSala();
                break;
            case "7":
                SalaProjecaoView.setLista(null);
                SessaoView.setarLista(SalaProjecaoView.selecionarSala());
                SessaoView.atualizarSessao();
                break;
            case "8":
                SessaoView.setLista(null);
                IngressoView.setarLista(SessaoView.selecionarSessao());
                IngressoView.atualizarIngresso();
                break;
            default:
                return;
        }
        Edicoes();
    }

    public static void Deletes() {
        System.out.println("Edicoes:");
        System.out.println("1. Clientes");
        System.out.println("2. Categorias");
        System.out.println("3. Filmes");
        System.out.println("4. Shoppings:");
        System.out.println("5. Cinema:");
        System.out.println("6. Salas de Cinema:");
        System.out.println("7. Sessao:");
        System.out.println("8. Cliente:");
        System.out.println("Any. Sair");

        String opcao = System.console().readLine();
        switch (opcao) {
            case "1":
                ClienteView.deletarCliente();
                break;
            case "2":
                CategoriaView.deletarCategoria();
                break;
            case "3":
                FilmeView.deletarFilme();
                break;
            case "4":
                ShoppingView.deletarShopping();
                break;
            case "5":
                ShoppingView.setarLista();
                CinemaView.setarLista(ShoppingView.selecionarShopping());
                CinemaView.deletarCinema();
                break;
            case "6":
                CinemaView.setarLista(null);
                SalaProjecaoView.setarLista(CinemaView.selecionarCinema());
                SalaProjecaoView.deletarSala();
                break;
            case "7":
                SalaProjecaoView.setLista(null);
                SessaoView.setarLista(SalaProjecaoView.selecionarSala());
                SessaoView.deletarSessao();
                break;
            case "8":
                SessaoView.setLista(null);
                IngressoView.setarLista(SessaoView.selecionarSessao());
                IngressoView.deletarIngresso();
                break;
            default:
                return;
        }
        Deletes();
    }

    public static void Views() {
        System.out.println("Visualizações:");
        System.out.println("1. Clientes");
        System.out.println("2. Categorias");
        System.out.println("3. Filmes");
        System.out.println("4. Shoppings:");
        System.out.println("5. Cinema:");
        System.out.println("6. Salas de Cinema:");
        System.out.println("7. Sessao:");
        System.out.println("8. Ingressos:");
        System.out.println("Any. Sair");

        String opcao = System.console().readLine();
        switch (opcao) {
            case "1":
                ClienteView.listarClientes();
                break;
            case "2":
                CategoriaView.listarCategorias();
                break;
            case "3":
                FilmeView.listarFilmes();
                break;
            case "4":
                ShoppingView.listarShoppings();
                break;
            case "5":
                ShoppingView.setarLista();
                CinemaView.setarLista(ShoppingView.selecionarShopping());
                CinemaView.listarCinemas();
                break;
            case "6":
                CinemaView.setarLista(null);
                SalaProjecaoView.setarLista(CinemaView.selecionarCinema());
                SalaProjecaoView.listarSalas();
                break;
            case "7":
                SalaProjecaoView.setLista(null);
                SessaoView.setarLista(SalaProjecaoView.selecionarSala());
                SessaoView.listarSessoes();
                break;
            case "8":
                SessaoView.setLista(null);
                IngressoView.setarLista(SessaoView.selecionarSessao());
                IngressoView.listarIngressos();
                break;
            default:
                return;
        }
        Views();
    }

}
