package model.ingresso;

public interface IIngresso {
    
    public float calcValor();
    public void vender();
    public void cancelar();
    public void imprimir();
    public IIngresso clonar();

}
