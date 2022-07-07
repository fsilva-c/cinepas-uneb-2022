package model;

public class Estudante extends Cliente {

	protected String carteirinha;

	public Estudante() {
	}

	public Estudante(String cpf, String nome, String carteirinha) {
		super(cpf, nome);
		this.carteirinha = carteirinha;
	}

	public String getCarteirinha() {
		return this.carteirinha;
	}

	public void setCarteirinha(String carteirinha) {
		this.carteirinha = carteirinha;
	}

}