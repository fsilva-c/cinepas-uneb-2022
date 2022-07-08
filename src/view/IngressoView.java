package view;

import model.Cliente;
import model.Estudante;
import model.SalaProjecao;
import model.ingresso.*;
import model.sessao.Sessao;
import dao.IngressoDao;
import java.util.*;

public abstract class IngressoView {

    private static ArrayList<IIngresso> ingressos = null;

    public static void setarLista(Sessao sessao) {
        IngressoDao ingressosDao = new IngressoDao();
        if (sessao == null)
            ingressos = new ArrayList<IIngresso>(ingressosDao.select());
        else
            ingressos = new ArrayList<IIngresso>(ingressosDao.select(sessao));
    }

    public static void setLista(ArrayList<IIngresso> ings) {
        IngressoDao ingressosDao = new IngressoDao();
        if (ings == null)
            ingressos = new ArrayList<IIngresso>(ingressosDao.select());
        else
            ingressos = ings;
    }

    public static void listarIngressos() {
        System.out.println("\nLista de Ingressos:");
        for (IIngresso ingresso : ingressos) {
            System.out.println("");
            System.out.println(ingresso.toString());
        }
        System.out.println("<===========================================>");
    }

    public static IIngresso selecionarIngresso() {
        listarIngressos();
        System.out.println("Id do ingresso: ");
        String res = System.console().readLine();
        int id = res.equals("") || res == null ? 0 : Integer.parseInt(res);

        if (id == 0)
            return null;

        IIngresso ingresso = new Ingresso();
        ingresso.setId(id);
        return procurarIngresso(ingresso);
    }

    public static IIngresso procurarIngresso(IIngresso ingresso) {
        for (IIngresso ing : ingressos) {
            if (ing.getId() == ingresso.getId()) {
                return ing;
            }
        }

        return null;
    }

    public static IIngresso cadastrarIngresso(Sessao sessao) {

        IngressoBase ingresso = new IngressoBase(new Ingresso());
        Cliente cliente = ClienteView.selecionarCliente();
        if (cliente != null) {
            ingresso.setCliente(cliente);
            if (cliente instanceof Estudante) {
                ingresso.setIngresso(new IngressoMeia(ingresso.getIngresso()));
            }
        }

        if (sessao.getHorario().isFDS()) {
            ingresso.setIngresso(new IngressoFds(ingresso.getIngresso()));
        }

        if (SalaProjecao.IsTipoSala3D(sessao)) {
            ingresso.setIngresso(new IngressoEspecial(ingresso.getIngresso()));
        }

        IngressoDao ingressoDao = new IngressoDao();
        ingressoDao.insert(ingresso, sessao);

        return ingresso;

    }

    public static void atualizarIngresso() {
        IIngresso ingresso = selecionarIngresso();

        if (ingresso != null) {
            System.out.println("Insira os novos dados do ingresso");
            System.out.println("novo titular: ");
            Cliente novoTitular = ClienteView.selecionarCliente();
            if (novoTitular != null) {
                ingresso.setCliente(novoTitular);
            }

            IngressoDao ingressoDao = new IngressoDao();
            ingressoDao.update(ingresso);
        }
    }

    public static void deletarIngresso() {
        IIngresso ingresso = selecionarIngresso();

        if (ingresso != null) {
            IngressoDao ingressoDao = new IngressoDao();
            ingressoDao.delete(ingresso);
        }

    }

}
