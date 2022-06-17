package model.ingresso;

import model.Cliente;

public class IngressoMeia extends IngressoBase {
    
    
    public void vender() { }
    public void cancelar() { }
    public void imprimir() { }


    public IngressoMeia() {
    }

    public IngressoMeia(IIngresso ingresso) {
        super(ingresso);
    }

    public IngressoMeia(float preco, Cliente cliente) {
        super(preco, cliente);
    }

    // Prototype Pattern...
    private IngressoMeia(IngressoBase target) {
        super(target);
    }

    @Override
    public IngressoMeia clonar() {
        return new IngressoMeia(this);
    }

    public float calcValor() {
        return super.calcValor() / 2.0f;  
    }

}