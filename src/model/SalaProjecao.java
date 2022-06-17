package model;

import model.sessao.Sessao;
import java.util.ArrayList;
import java.util.List;

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


}
