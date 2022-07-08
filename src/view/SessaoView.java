package view;

import view.FilmeView;
import model.Filme;
import model.sessao.Sessao;
import model.SalaProjecao;
import model.datahora.*;
import dao.SessaoDao;
import java.util.*;

public abstract class SessaoView {

    private static ArrayList<Sessao> Sessoes = null;

    public static void setarLista(SalaProjecao sala) {
        SessaoDao sessaoDao = new SessaoDao();
        if (sala == null)
            Sessoes = new ArrayList<Sessao>(sessaoDao.select());
        else
            Sessoes = new ArrayList<Sessao>(sessaoDao.select(sala));
    }

    public static void setLista(ArrayList<Sessao> sessoes) {
        SessaoDao sessaoDao = new SessaoDao();
        if (sessoes == null)
            Sessoes = new ArrayList<Sessao>(sessaoDao.select());
        else
            Sessoes = sessoes;
    }

    public static void listarSessoes() {
        System.out.println("\nLista de Sessoes:");
        for (Sessao sessao : Sessoes) {
            System.out.println(sessao.toString());
            System.out.println("");
        }
        System.out.println("<===========================================>");
    }

    public static Sessao selecionarSessao() {
        listarSessoes();
        System.out.println("id da Sessao: ");
        String res = System.console().readLine();
        int id = res.equals("") || res == null ? 0 : Integer.parseInt(res);

        if (id == 0)
            return null;

        Sessao Sessao = new Sessao();
        Sessao.setId(id);
        return procurarSessao(Sessao);
    }

    public static Sessao procurarSessao(Sessao Sessao) {
        for (Sessao sessao : Sessoes) {
            if (Sessao.getId() == sessao.getId()) {
                return sessao;
            }
        }
        return null;
    }

    public static void cadastrarSessao(SalaProjecao salaProjecao) {
        System.out.println("Insira os dados da Sessao");
        System.out.println("Horario: ");
        String horario = System.console().readLine();
        Sessao Sessao = new Sessao();
        Sessao.setHorario(new AdapterDataHora(horario));
        Sessao.setVagasOcupadas(0);
        System.out.println("Filme: ");
        FilmeView.setarLista();
        Filme filme = FilmeView.selecionarFilme();
        Sessao.setFilme(filme);

        SessaoDao SessaoDao = new SessaoDao();
        SessaoDao.insert(Sessao, salaProjecao);
    }

    public static void atualizarSessao() {
        Sessao Sessao = selecionarSessao();
        if (Sessao != null) {
            System.out.println("Insira os novos dados da Sessao");
            System.out.println("Horario: ");
            String horario = System.console().readLine();
            System.out.println("Filme: ");
            Filme filme = FilmeView.selecionarFilme();
            Sessao.setFilme(filme);

            if (!horario.equals(" "))
                Sessao.setHorario(new AdapterDataHora(horario));
            if (filme != null)
                Sessao.setFilme(filme);
            Sessao.setVagasOcupadas(0);

            SessaoDao SessaoDao = new SessaoDao();
            SessaoDao.update(Sessao);
        }
    }

    public static void deletarSessao() {
        Sessao Sessao = selecionarSessao();
        if (Sessao != null) {
            SessaoDao SessaoDao = new SessaoDao();
            SessaoDao.update(Sessao);
        }
    }
}
