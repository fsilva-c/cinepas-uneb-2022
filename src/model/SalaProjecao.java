package model;

import model.sessao.Sessao;
import java.util.ArrayList;
import java.util.List;

import dao.SalaProjecaoDao;

public class SalaProjecao {

    protected int id;
    protected String numero;
    protected int capacidade;
    protected List<Sessao> sessoes = new ArrayList<Sessao>();

    public SalaProjecao() {

    }

    public SalaProjecao(int id, String numero, int capacidade, List<Sessao> sessoes) {
        this.id = id;
        this.numero = numero;
        this.capacidade = capacidade;
        this.sessoes = sessoes;
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    public void addSessao(Sessao sessao) {
        this.sessoes.add(sessao);
    }

    public static boolean IsTipoSala3D(Sessao sessao) {
        SalaProjecaoDao salaDao = new SalaProjecaoDao();
        SalaProjecao sala = salaDao.select(sessao);
        if (sala != null)
            return (sala instanceof SalaProjecao3D);
        return false;
    }

    public static ArrayList<SalaProjecao> SalasPorData(String datahora) {
        SalaProjecaoDao salaDao = new SalaProjecaoDao();
        ArrayList<SalaProjecao> result = new ArrayList<SalaProjecao>(salaDao.select(datahora));
        return result;
    }

    public static ArrayList<SalaProjecao> SalasPorFilme(Filme filme) {
        SalaProjecaoDao salaDao = new SalaProjecaoDao();
        ArrayList<SalaProjecao> result = new ArrayList<SalaProjecao>(salaDao.select(filme));
        return result;
    }

    @Override
    public String toString() {
        String result = "[ " + "id: " + getId() + " numero: " + getNumero() + " capacidade: " + getCapacidade()
                + "\n sessoes: ";
        for (Sessao sessao : getSessoes()) {
            result += "\n" + sessao;
        }
        result += " ]";
        return result;
    }

}
