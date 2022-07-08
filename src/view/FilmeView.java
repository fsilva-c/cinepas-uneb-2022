package view;

import model.Filme;
import model.Categoria;
import dao.FilmeDao;
import java.util.*;

public abstract class FilmeView {

    private static ArrayList<Filme> filmes = null;

    public static void setarLista() {
        FilmeDao filmeDao = new FilmeDao();
        filmes = new ArrayList<Filme>(filmeDao.select());
    }

    public static void setLista(ArrayList<Filme> Filmes) {
        if (Filmes == null) {
            FilmeDao filmeDao = new FilmeDao();
            filmes = new ArrayList<Filme>(filmeDao.select());
        } else {
            filmes = Filmes;
        }
    }

    public static void listarFilmes() {
        if (filmes == null) {
            setLista(null);
        }
        System.out.println("\nLista de filmes:");
        for (Filme filme : filmes) {
            System.out.println(filme.toString());
            System.out.println("");
        }
        System.out.println("<===========================================>");
    }

    public static Filme selecionarFilme() {
        listarFilmes();
        System.out.println("id do filme: ");
        String res = System.console().readLine();
        int id = res.equals("") || res == null ? 0 : Integer.parseInt(res);

        if (id == 0)
            return null;

        Filme filme = new Filme();
        filme.setId(id);
        return procurarFilme(filme);
    }

    public static Filme procurarFilme(Filme filme) {
        for (Filme film : filmes) {
            if (film.getId() == filme.getId()) {
                return film;
            }
        }
        return null;
    }

    public static void cadastrarFilme() {
        System.out.println("Insira os dados do filme");
        System.out.println("Titulo: ");
        String titulo = System.console().readLine();
        System.out.println("Duração: (Minutos)");
        int duracao = Integer.parseInt(System.console().readLine());
        Categoria categoria = CategoriaView.selecionarCategoria();
        Filme filme = new Filme();
        filme.setTitulo(titulo);
        filme.setDuracao(duracao);
        if (categoria != null)
            filme.setCategoria(categoria);
        FilmeDao filmeDao = new FilmeDao();
        filmeDao.insert(filme);
    }

    public static void atualizarFilme() {
        Filme filme = selecionarFilme();
        if (filme != null) {
            System.out.println("Insira os novos dados do Filme");
            System.out.println("Titulo: ");
            String titulo = System.console().readLine();
            System.out.println("Duração: ");
            int duracao = Integer.parseInt(System.console().readLine());
            Categoria categoria = CategoriaView.selecionarCategoria();
            if (!titulo.equals(" "))
                filme.setTitulo(titulo);
            if (duracao > 0)
                filme.setDuracao(duracao);
            if (categoria != null)
                filme.setCategoria(categoria);

            FilmeDao filmeDao = new FilmeDao();
            filmeDao.insert(filme);
        }
    }

    public static void deletarFilme() {
        Filme filme = selecionarFilme();
        if (filme != null) {
            FilmeDao filmeDao = new FilmeDao();
            filmeDao.delete(filme);
        }
    }
}
