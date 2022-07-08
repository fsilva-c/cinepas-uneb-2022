package model;

import java.util.*;

public class Cinema {

    private int id;
    private String nome;
    protected List<SalaProjecao> salasProjecao = new ArrayList<SalaProjecao>();

    public Cinema() {
    }

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

    public void addSalaProjecao(SalaProjecao salaProjecao) {
        this.salasProjecao.add(salaProjecao);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        String result = "[ " + "id: " + getId() + " nome: " + getNome() + "\n salas: ";
        for (SalaProjecao sala : getSalasProjecao()) {
            result += "\n" + sala;
        }
        result += " ]";
        return result;
    }
}
