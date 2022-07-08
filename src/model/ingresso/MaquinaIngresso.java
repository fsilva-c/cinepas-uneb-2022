package model.ingresso;

import java.util.ArrayList;

import model.Cliente;
import model.SalaProjecao;
import model.sessao.*;
import model.Filme;
import model.datahora.AdapterDataHora;
import view.*;

public final class MaquinaIngresso {
    // PAS: Utilização do padrão FACADE+SINGLETON para centralizar a interface das
    // operações com o cliente:
    private static MaquinaIngresso instance;
    static private CuidadoraSessao gerenciadorSessao;
    private IIngresso ingresso;

    private MaquinaIngresso() {
        gerenciadorSessao = new CuidadoraSessao();
    }

    public static MaquinaIngresso getInstance() {
        if (instance == null)
            instance = new MaquinaIngresso();
        return instance;
    }

    // Crud & Definir as sessões e filmes de uma sala
    public void CRUD() {
        System.out.println("Sistema CRUD:");
        System.out.println("1. Cadastros");
        System.out.println("2. Edições");
        System.out.println("3. Visualizações");
        System.out.println("4. Exclusões:");
        System.out.println("Any. Sair");
        String opcao = System.console().readLine();
        switch (opcao) {
            case "1":
                MaquinaIngressoView.Cadastros();
                break;
            case "2":
                MaquinaIngressoView.Edicoes();
                break;
            case "3":
                MaquinaIngressoView.Views();
                break;
            case "4":
                MaquinaIngressoView.Deletes();
                break;
            default:
                return;
        }
        CRUD();
    }

    // Vender ingresso
    public void VenderIngresso() {
        FilmeView.setarLista();
        Filme filme = FilmeView.selecionarFilme();
        ArrayList<SalaProjecao> salas = SalaProjecao.SalasPorFilme(filme);
        ArrayList<Sessao> sessoes = new ArrayList<Sessao>();
        if (salas.size() <= 0) {
            System.out.println("Não existem salas com este filme em exibição");
            return;
        }

        for (SalaProjecao sala : salas) {
            if (sala.getSessoes().size() <= 0) {
                System.out.println("Não existem sessões disponíveis para este filme");
                return;
            }
            for (Sessao sessao : sala.getSessoes()) {
                if (sessao.getFilme().getId() == filme.getId()
                        && sessao.getVagasOcupadas() < sala.getCapacidade()) {
                    sessoes.add(sessao);
                }
            }
            SessaoView.setLista(sessoes);
            SessaoView.listarSessoes();
        }

        SalaProjecaoView.setLista(salas);
        SalaProjecao sala = SalaProjecaoView.selecionarSala();

        sessoes.clear();
        for (Sessao sessao : sala.getSessoes()) {
            if (sessao.getFilme().getId() == filme.getId()) {
                sessoes.add(sessao);
            }
        }
        SessaoView.setLista(sessoes);
        Sessao sessao = SessaoView.selecionarSessao();

        gerenciadorSessao.setSessao(sessao);
        gerenciadorSessao.backup();
        ingresso = IngressoView.cadastrarIngresso(sessao);
        sessao.addIngresso(ingresso);

    }

    // Cancelar venda de ingresso
    public void ConfirmarVenda(boolean cancelar) {
        // PAS: Utilização do padrão MEMENTO para gerenciamento do estado da sessões:
        // Estado é preservador na venda de ingresso e pode ser recuperado ao cancelar a
        // venda.
        if (cancelar)
            gerenciadorSessao.desfazer();
        else
            ImprimirIngresso();
    }

    // Imprimir ticket (pode ser uma impressão na tela mesmo)
    public void ImprimirIngresso() {
        System.out.println(ingresso.toString());
    }

    // Consultar filmes em exibição em um determinado dia
    public void checkFilmes(String datahora) {
        AdapterDataHora dataHora = new AdapterDataHora(datahora);
        ArrayList<SalaProjecao> salas = SalaProjecao.SalasPorData(dataHora.sqlDate());
        ArrayList<Filme> filmes = new ArrayList<Filme>();

        if (salas.size() <= 0) {
            System.out.println("Não existem salas para esta data de exibição");
            return;
        }

        for (SalaProjecao sala : salas) {
            if (sala.getSessoes().size() <= 0) {
                System.out.println("Não existem sessões disponíveis para esta data");
                return;
            }
            for (Sessao sessao : sala.getSessoes()) {
                if (sessao.getVagasOcupadas() < sala.getCapacidade()) {
                    filmes.add(sessao.getFilme());
                }
            }
            if (filmes.size() <= 0) {
                System.out.println("Não existem filmes disponíveis para esta data");
                return;
            }
            System.out.println(sala.toString());
            FilmeView.setLista(filmes);
            FilmeView.listarFilmes();
        }
    }

    // Verificar se existe vaga disponível em uma sala/sessão em uma data
    public void checkDisponibilidade(String datahora) {
        AdapterDataHora dataHora = new AdapterDataHora(datahora);
        ArrayList<SalaProjecao> salas = SalaProjecao.SalasPorData(dataHora.sqlDate());
        ArrayList<Sessao> sessoes = new ArrayList<Sessao>();
        for (SalaProjecao sala : salas) {
            for (Sessao sessao : sala.getSessoes()) {
                if (sessao.getVagasOcupadas() < sala.getCapacidade()) {
                    sessoes.add(sessao);
                }
            }
            System.out.println(sala.toString());
            SessaoView.setLista(sessoes);
            SessaoView.listarSessoes();

        }
    }

    public IIngresso ComprarIngresso(float preco, Cliente cliente) {
        return this.ingresso = new Ingresso(preco, cliente);
    }

    public IIngresso ClonarIngresso(Ingresso ingresso) {
        return (Ingresso) ingresso.clonar();
    }

}