package model.ingresso;

import model.Cliente;

public interface IIngresso {

    public void setId(int id);

    public void setCliente(Cliente cliente);

    public void setPreco(float valor);

    public float getPreco();

    public int getId();

    public Cliente getCliente();

    public float calcValor();

    public void vender();

    public void cancelar();

    public void imprimir();

    public IIngresso clonar();

}
