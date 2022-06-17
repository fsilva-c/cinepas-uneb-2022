package model.sessao;
import model.datahora.*;

class SessaoMemento {
    private Sessao estadoSessao;
    private IDataHora datahora;

    public SessaoMemento(Sessao sessao) {
        this.estadoSessao = sessao;
        this.datahora = new AdapterDataHora();
    }

    public Sessao state(){
        return this.estadoSessao;
    }

    public IDataHora getDatahora() {
        return datahora;
    }

    public String getRegistro() {
        return (this.datahora.datahora() + "/ (" + this.estadoSessao.toString() + " )");
    }

}