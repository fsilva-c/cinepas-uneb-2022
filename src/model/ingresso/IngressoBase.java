package model.ingresso;

import model.Cliente;

public class IngressoBase implements IIngresso {
    
    protected float preco;
    protected Cliente cliente;
    private IIngresso ingresso;

    public IngressoBase() {

    }

    public IngressoBase(IIngresso ingresso) {
        this.ingresso = ingresso;
    }


    public IngressoBase(float preco, Cliente cliente) {
        this.preco = preco;
        this.cliente = cliente;
    }

    // Prototype Pattern...
    private IngressoBase(IngressoBase target) {
        if (target != null) {
            this.preco = target.preco;
            this.cliente = target.cliente;
            this.ingresso = target.ingresso;
        }
    }

    public float getPreco() {
        return this.preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "{" +
            " preco='" + getPreco() + "'" +
            ", cliente='" + getCliente() + "'" +
            "}";
    }

    @Override
    public float calcValor(){

        return ingresso.calcValor();
    }

    public void vender(){

    }

    public void cancelar(){

    }

    public void imprimir(){
        
    }

     public IngressoBase clonar() {
        return new IngressoBase(this);
    }

}
