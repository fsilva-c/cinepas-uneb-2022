package view;

import model.Shopping;
import model.Cinema;
import dao.ShoppingDao;
import java.util.*;

abstract class ShoppingView {

    private static ArrayList<Shopping> shoppings = null;

    public static void setarLista() {
        ShoppingDao shoppingDao = new ShoppingDao();
        shoppings = new ArrayList<Shopping>(shoppingDao.select());
    }

    public static void listarShoppings() {
        if (shoppings == null)
            setarLista();
        System.out.println("\nLista de shoppings:");
        for (Shopping shop : shoppings) {
            System.out.println(shop.toString());
            System.out.println("");
        }
        System.out.println("<===========================================>");
    }

    public static Shopping selecionarShopping() {
        listarShoppings();
        System.out.println("ID do shopping: ");
        String res = System.console().readLine();
        int idShopping = res.equals("") || res == null ? 0 : Integer.parseInt(res);

        if (idShopping == 0)
            return null;

        Shopping shopping = new Shopping();
        shopping.setId(idShopping);

        return procurarShopping(shopping);
    }

    public static Shopping procurarShopping(Shopping shopping) {
        for (Shopping shop : shoppings) {
            if (shop.getId() == shopping.getId()) {
                return shop;
            }
        }
        return null;
    }

    public static void cadastrarShopping() {
        System.out.println("Insira os dados do shopping");
        System.out.println("Nome: ");
        String nome = System.console().readLine();

        Shopping shopping = new Shopping();
        ShoppingDao shoppingDao = new ShoppingDao();

        shopping.setNome(nome);
        shoppingDao.insert(shopping);
    }

    public static void atualizarShopping() {
        Shopping shopping = selecionarShopping();

        if (shopping != null) {
            System.out.println("Insira os novos dados do shopping");
            System.out.println("Nome: ");
            String nome = System.console().readLine();

            if (!nome.equals(" ")) {
                shopping.setNome(nome);
            }

            ShoppingDao shoppingDao = new ShoppingDao();

            shoppingDao.update(shopping);
        }
    }

    public static void deletarShopping() {
        Shopping shopping = selecionarShopping();

        if (shopping != null) {
            ShoppingDao shoppingDao = new ShoppingDao();
            shoppingDao.delete(shopping);
        }

    }

}
