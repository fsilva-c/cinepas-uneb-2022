package model.ingresso;

import model.Cliente;

public class IngressoEspecial extends IngressoBase {
    
    public void vender() { }
    public void cancelar() { }
    public void imprimir() { }


    public IngressoEspecial() {
    }

    public IngressoEspecial(IIngresso ingresso) {
        super(ingresso);
    }

    public IngressoEspecial(float preco, Cliente cliente) {
        super(preco, cliente);
    }

    // Prototype Pattern...
    private IngressoEspecial(IngressoEspecial target) {
        super(target);
    }

    @Override
    public IngressoEspecial clonar() {
        return new IngressoEspecial(this);
    }
    
    public float calcValor() {
        return super.calcValor() * 1.2f;  
    }

}
