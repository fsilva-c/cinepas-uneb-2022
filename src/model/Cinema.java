package model;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    
    private int id;
    protected List<SalaProjecao> salasProjecao = new ArrayList<SalaProjecao>();

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
    
}
