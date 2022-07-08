package model;

import java.util.*;

public class Shopping {

    private int id;
    private String nome;
    protected List<Cinema> cinemas = new ArrayList<Cinema>();

    public Shopping() {
    }

    public Shopping(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public void addCinema(Cinema cinema) {
        this.cinemas.add(cinema);
    }

    @Override
    public String toString() {
        String result = "[ " + "id: " + getId() + " nome: " + getNome() + "\n cinemas: ";
        for (Cinema cine : getCinemas()) {
            result += " \n" + cine;
        }
        result += " ]";
        return result;
    }

}
