package model.ingresso;

import model.Cliente;

public class Ingresso implements IIngresso {
    protected float preco;
    protected Cliente cliente;
    
    public float calcValor() { return preco; }
    public void vender() { }
    public void cancelar() { }
    public void imprimir() { }

    public Ingresso() {
        
    }

    public Ingresso(float preco, Cliente cliente) {
        this.preco = preco;
        this.cliente = cliente;
    }

    // Prototype Pattern...
    private Ingresso(Ingresso target) {
         if (target != null) {
            this.preco = target.preco;
            this.cliente = target.cliente;
        }
    }

    public Ingresso clonar() {
        return new Ingresso(this);
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
    
}
