package model;

public class Filme {

    private int id;
    private String titulo, diretor, atorPrincipal;
    private int duracao;
    private int classificacaoEtaria;
    private Categoria categoria;

    public Filme() {

    }

    public Filme(int id, String titulo, String diretor, String atorPrincipal, int duracao, int classificacaoEtaria,
            Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.atorPrincipal = atorPrincipal;
        this.duracao = duracao;
        this.classificacaoEtaria = classificacaoEtaria;
        this.categoria = categoria;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return this.diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getAtorPrincipal() {
        return this.atorPrincipal;
    }

    public void setAtorPrincipal(String atorPrincipal) {
        this.atorPrincipal = atorPrincipal;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getClassificacaoEtaria() {
        return this.classificacaoEtaria;
    }

    public void setClassificacaoEtaria(int classificacaoEtaria) {
        this.classificacaoEtaria = classificacaoEtaria;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "[ " + "id: " + getId() + " titulo: " + getTitulo() + " duracao: " + getDuracao() + " min "
                + " categoria: "
                + getCategoria() + " ]";
    }

}
