package model.sessao;

import model.ingresso.*;
import model.datahora.*;

public class SessaoMemento {
    private Sessao estadoSessao;
    private IDataHora datahora;

    public SessaoMemento(Sessao sessao) {
        this.estadoSessao = new Sessao();

        this.estadoSessao.setId(sessao.getId());
        for (IIngresso ing : sessao.getIngressos()) {
            Ingresso ingresso = new Ingresso();
            ingresso.setId(ing.getId());
            this.estadoSessao.addIngresso(ingresso);
        }

        System.out.println(estadoSessao.getIngressos());
        this.datahora = new AdapterDataHora();
    }

    public Sessao state() {
        return this.estadoSessao;
    }

    public IDataHora getDatahora() {
        return datahora;
    }

    public String getRegistro() {
        return (this.datahora.datahora() + "/ (" + this.estadoSessao.toString() + " )");
    }

}