package model.ingresso;

import model.Cliente;

public final class MaquinaIngresso  {

    private static MaquinaIngresso instance;
    private Cliente cliente;
    private Ingresso ingresso;

    private MaquinaIngresso() {
        
    }

    public static MaquinaIngresso getInstance()
    {
        if(instance == null)
            instance = new MaquinaIngresso();
        return instance;
    }
    
    public Cliente RegistrarCliente(String cpf, String nome)
    {
        return this.cliente = new Cliente(cpf, nome);
    }

    public Ingresso ComprarIngresso(float preco, Cliente cliente)
    {
        return this.ingresso = new Ingresso(preco, cliente);
    }

    public Ingresso ClonarIngresso(Ingresso ingresso){
        return (Ingresso) ingresso.clonar();
    }

}