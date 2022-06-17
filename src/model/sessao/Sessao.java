package model.sessao;

import java.util.List;
import java.util.ArrayList;

import model.Filme;
import model.ingresso.IIngresso;
import model.datahora.AdapterDataHora;

public class Sessao {
    
    private int id;
    private Filme filme;
    private String horario;
    private int vagasOcupadas;
    protected List<IIngresso> ingressos = new ArrayList<IIngresso>();

    public Sessao() {

    }

    public Sessao(int id, Filme filme, String horario, int vagasOcupadas, List<IIngresso> ingressos) {
        this.id = id;
        this.filme = filme;
        this.horario = horario;
        this.vagasOcupadas = vagasOcupadas;
        this.ingressos = ingressos;
    }

    public void clone(Sessao sessao) {
        this.id = sessao.id;
        this.filme = sessao.filme;
        this.horario = sessao.horario;
        this.vagasOcupadas = sessao.vagasOcupadas;
        this.ingressos = sessao.ingressos;
    }
    
    public String horarioFimSessao(){
        AdapterDataHora datahora = new AdapterDataHora(this.horario);
        return datahora.somarMinutos(this.filme.getDuracao());
    }

    public SessaoMemento salvarContexto(){
        return new SessaoMemento(this);
    }

    public void RestaurarContexto(SessaoMemento memento){
        clone(memento.state());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Filme getFilme() {
        return this.filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public String getHorario() {
        return this.horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<IIngresso> getIngressos() {
        return this.ingressos;
    }

    public void setIngressos(List<IIngresso> ingressos) {
        this.ingressos = ingressos;
    }

    public int getVagasOcupadas() {        
        return ingressos.size();
    }

    public void setVagasOcupadas(int vagasOcupadas) {
        this.vagasOcupadas = vagasOcupadas;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", filme='" + getFilme() + "'" +
            ", horario='" + getHorario() + "'" +
            ", ingressos='" + getIngressos() + "'" +
            "}";
    }

}
