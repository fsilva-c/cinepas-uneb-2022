package model.ingresso;

import model.Cliente;

public class IngressoBase implements IIngresso {
    protected int id;
    protected float preco;
    protected Cliente cliente;
    private IIngresso ingresso;

    public IngressoBase() {

    }

    public IngressoBase(IIngresso ingresso) {
        this.ingresso = ingresso;
        if (this.cliente == null) {
            this.cliente = ingresso.getCliente();
        }
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
        if (this.preco > 0.f)
            return this.preco;
        return this.calcValor();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IIngresso getIngresso() {
        return ingresso;
    }

    public void setIngresso(IIngresso ingresso) {
        this.ingresso = ingresso;
    }

    @Override
    public String toString() {
        if (!(this.ingresso instanceof Ingresso))
            if (this.getId() > 0)
                return "[ " + "id: " + getId() + " preco: " + getPreco() + " cliente: " + getCliente() + " ]"
                        + this.ingresso.toString();
            else
                return this.ingresso.toString();
        else if (getId() > 0)
            return "[ " + "id: " + getId() + " preco: " + getPreco() + " cliente: " + getCliente() + " ]";
        return "";

    }

    @Override
    public float calcValor() {

        return ingresso.calcValor();
    }

    public void vender() {

    }

    public void cancelar() {

    }

    public void imprimir() {

    }

    public IngressoBase clonar() {
        return new IngressoBase(this);
    }

}
