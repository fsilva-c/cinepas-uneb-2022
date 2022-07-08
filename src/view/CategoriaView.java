package view;

import model.Categoria;
import dao.CategoriaDao;
import java.util.*;

public abstract class CategoriaView {

    private static ArrayList<Categoria> categorias = null;

    static void setarLista() {
        CategoriaDao categoriaDao = new CategoriaDao();
        categorias = new ArrayList<Categoria>(categoriaDao.select());
    }

    public static void listarCategorias() {
        if (categorias == null)
            setarLista();
        System.out.println("\nLista de categorias:");
        for (Categoria categoria : categorias) {
            System.out.println(categoria.toString());
            System.out.println("");
        }
        System.out.println("<===========================================>");
    }

    public static Categoria selecionarCategoria() {
        listarCategorias();
        System.out.println("id da categoria: ");
        String res = System.console().readLine();
        int id = res.equals("") || res == null ? 0 : Integer.parseInt(res);

        if (id == 0)
            return null;

        Categoria categoria = new Categoria();
        categoria.setId(id);
        return procurarCategoria(categoria);
    }

    public static Categoria procurarCategoria(Categoria categoria) {
        for (Categoria cat : categorias) {
            if (categoria.getId() == cat.getId()) {
                return cat;
            }
        }
        return null;
    }

    public static void cadastrarCategoria() {
        System.out.println("Insira os dados da categoria");
        System.out.println("Nome: ");
        String nome = System.console().readLine();
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        CategoriaDao categoriaDao = new CategoriaDao();
        categoriaDao.insert(categoria);
    }

    public static void atualizarCategoria() {
        Categoria categoria = selecionarCategoria();
        if (categoria != null) {
            System.out.println("Insira os novos dados da categoria");
            System.out.println("Nome: ");
            String nome = System.console().readLine();
            if (!nome.equals(" "))
                categoria.setNome(nome);
            CategoriaDao categoriaDao = new CategoriaDao();
            categoriaDao.update(categoria);
        }
    }

    public static void deletarCategoria() {
        Categoria categoria = selecionarCategoria();
        if (categoria != null) {
            CategoriaDao categoriaDao = new CategoriaDao();
            categoriaDao.update(categoria);
        }
    }
}
