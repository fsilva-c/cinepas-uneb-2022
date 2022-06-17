package model.sessao;
import java.util.List;
import java.util.ArrayList;

class CuidadoraSessao {
    
    private List<SessaoMemento> estadosSessoes = new ArrayList<SessaoMemento>();
    private Sessao sessao;

    public void backup(){
        this.estadosSessoes.add(this.sessao.salvarContexto());
    }

    public void desfazer(){
        if(this.estadosSessoes.size() > 0){
            int ultimoIndice = this.estadosSessoes.size() - 1;
            SessaoMemento memento = this.estadosSessoes.get(ultimoIndice);
            this.estadosSessoes.remove(ultimoIndice);
            this.sessao.RestaurarContexto(memento);
        }
    }

    public void Historico(){
        for (SessaoMemento memento : estadosSessoes) {
            System.out.println(memento.getRegistro());
        }
        
    }
}