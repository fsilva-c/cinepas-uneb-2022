package model.ingresso;

import model.Cliente;

public class IngressoFds extends IngressoBase {

    public void vender() {
    }

    public void cancelar() {
    }

    public void imprimir() {
    }

    public IngressoFds() {
    }

    public IngressoFds(IIngresso ingresso) {
        super(ingresso);
    }

    public IngressoFds(float preco, Cliente cliente) {
        super(preco, cliente);
    }

    // Prototype Pattern...
    private IngressoFds(IngressoFds target) {
        super(target);
    }

    @Override
    public IngressoFds clonar() {
        return new IngressoFds(this);
    }

    public float calcValor() {
        return super.calcValor() * 1.05f;
    }

    @Override
    public String toString() {
        return super.toString() + "[ tipo: FDS ]";
    }
}
