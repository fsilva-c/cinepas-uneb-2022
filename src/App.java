import model.Cliente;
import model.ingresso.*;
import dao.*;
import dao.FilmeDao;
import model.*;
import java.util.*;
import model.sessao.Sessao;
import model.datahora.AdapterDataHora;
import view.*;

public class App {

    private static MaquinaIngresso maquinaIngresso = MaquinaIngresso.getInstance();

    public static void main(String[] args) throws Exception {

        Menu();

    }

    public static void Menu() {

        System.out.println("CinePAS:");
        System.out.println("1. CRUD");
        System.out.println("2. Venda de Ingressos");
        System.out.println("3. Cancelar Venda");
        System.out.println("4. Filmes em Exbição (Data):");
        System.out.println("5. Sessões Disponíveis (Data):");
        System.out.println("Any. Sair");
        String opcao = System.console().readLine();
        switch (opcao) {
            case "1":
                maquinaIngresso.CRUD();
                break;
            case "2":
                maquinaIngresso.VenderIngresso();
                break;
            case "3":
                System.out.println("Confirmar Cancelamento ?");
                System.out.println("S/N: ");
                Boolean cancelar = (System.console().readLine()).toUpperCase().equals("S");
                maquinaIngresso.ConfirmarVenda(cancelar);
                break;
            case "4":
                System.out.println("Data:");
                String data = System.console().readLine();
                maquinaIngresso.checkFilmes(data);
                break;
            case "5":
                System.out.println("Data:");
                String data2 = System.console().readLine();
                maquinaIngresso.checkDisponibilidade(data2);
                break;
            default:
                return;
        }
        Menu();
    }

    public static void Tests() {
        System.out.println("Hello, World!");

        MaquinaIngresso maquinaIngresso = MaquinaIngresso.getInstance();
        // teste prototype pattern...
        Cliente cliente = new Cliente("cpf", "nome");
        Ingresso ingresso = (Ingresso) maquinaIngresso.ComprarIngresso(21.99f, cliente);
        Ingresso outroIngresso = (Ingresso) maquinaIngresso.ClonarIngresso(ingresso);
        // Cliente cliente = new Cliente("cpf", "nome");
        // Ingresso ingresso = new Ingresso(21.99f, cliente);
        // Ingresso outroIngresso = (Ingresso) ingresso.clonar();

        System.out.println("Ingresso: " + ingresso.toString());
        System.out.println("outroIngresso: " + outroIngresso.toString());

        // teste adapter...
        AdapterDataHora datahora = new AdapterDataHora("16/06/2022 23:04:00");
        System.out.println(datahora.hora());
        System.out.println(datahora.data());
        System.out.println(datahora.datahora());
        System.out.println(datahora.somarMinutos(78));

        AdapterDataHora datahora2 = new AdapterDataHora("16/06/2022 23:04:00");
        AdapterDataHora datahora3 = new AdapterDataHora(datahora.somarMinutos(78));
        System.out.println(datahora3.maiorQue(datahora2.datahora()));

        // teste decorator...
        IIngresso ing = new Ingresso(11.5f, cliente);
        IIngresso ing2 = new IngressoMeia(new IngressoEspecial(new Ingresso(34.f, cliente)));
        System.out.println(ing2.calcValor());

        // teste ponteiro objeto...
        Cliente cliente2 = cliente;
        System.out.println(cliente2.getCpf());
        cliente2.setCpf("084");
        System.out.println(cliente.getCpf());

        // Teste DAOS
        // ClienteDao cliDao = new ClienteDao();
        // cliDao.insert(cliente);
        // Filme filme = new Filme();
        // filme.setTitulo("Alvorada dos desnumbrantes corpos");
        // filme.setDuracao(96);
        // CategoriaDao cDao = new CategoriaDao();
        // Categoria categoria = new Categoria();
        // categoria.setId(1);
        // filme.setCategoria(cDao.select(categoria));
        // FilmeDao fDao = new FilmeDao();
        // fDao.insert(filme);
        // Sessao sessao = new Sessao();
        // sessao.setFilme(filme);
        // sessao.setHorario(datahora);
        // sessao.addIngresso(ing);
        // sessao.addIngresso(ing2);
        // SalaProjecao sala = new SalaProjecao();
        // sala.setCapacidade(200);
        // sala.setNumero("105B");
        // sala.addSessao(sessao);
        // Cinema cinema = new Cinema();
        // cinema.setNome("Cineapolis");
        // cinema.addSalaProjecao(sala);
        // Shopping shopping = new Shopping();
        // shopping.setNome("ShopPAS");
        // shopping.addCinema(cinema);
        ShoppingDao sDao = new ShoppingDao();
        // sDao.insert(shopping);

        List<Shopping> shoppings = sDao.select();
        for (Shopping shop : shoppings) {
            System.out.println(shop.getNome());
            for (Cinema cine : shop.getCinemas()) {
                System.out.println(cine.getNome());
                for (SalaProjecao salaP : cine.getSalasProjecao()) {
                    System.out.println(salaP.getNumero());
                    for (Sessao sess : salaP.getSessoes()) {
                        System.out.println(sess.getHorario().datahora() + " " + sess.getFilme().getTitulo());
                        for (IIngresso ingress : sess.getIngressos()) {
                            System.out.println(
                                    ingress.getId() + " " + ingress.getPreco() + " " + ingress.getCliente().getNome());
                        }
                    }
                }
            }
        }

        // Menu
        // CategoriaView.listarCategorias();
        // CategoriaView.atualizarCategoria();

        MaquinaIngressoView.Cadastros();
        // maquinaIngresso.VenderIngresso();
        // maquinaIngresso.ConfirmarVenda(true);
        // // maquinaIngresso.checkFilmes("16/06/2022 00:00:00");
        // maquinaIngresso.checkDisponibilidade("16/06/2022 00:00:00");
    }

}
