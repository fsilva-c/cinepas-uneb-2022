package model;

import java.util.List;

public class Cinema {
    
    private int id;
    private String nome;
    private Shopping shopping;
    private List<SalaProjecao> salasProjecao;

    public Cinema() { }

    public Cinema(int id, List<SalaProjecao> salasProjecao) {
        this.id = id;
        this.salasProjecao = salasProjecao;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SalaProjecao> getSalasProjecao() {
        return this.salasProjecao;
    }

    public void setSalasProjecao(List<SalaProjecao> salasProjecao) {
        this.salasProjecao = salasProjecao;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Shopping getShopping() {
        return this.shopping;
    }

    public void setShopping(Shopping shopping) {
        this.shopping = shopping;
    }
    
}
