package view;

import java.util.ArrayList;
import model.*;
import dao.CinemaDao;

public abstract class CinemaView {

    private static ArrayList<Cinema> cinemas = null;

    static void setarLista(Shopping Shopping) {
        CinemaDao cinemaDao = new CinemaDao();
        if (Shopping == null)
            cinemas = new ArrayList<Cinema>(cinemaDao.select());
        else
            cinemas = new ArrayList<Cinema>(cinemaDao.select(Shopping));

    }

    public static void listarCinemas() {
        if (cinemas == null)
            setarLista(null);
        System.out.println("\nLista de Cinemas:");
        for (Cinema cinema : cinemas) {
            System.out.println(cinema.toString());
            System.out.println("");
        }
        System.out.println("<===========================================>");
    }

    public static Cinema selecionarCinema() {
        listarCinemas();
        System.out.println("id da Cinema: ");
        String res = System.console().readLine();
        int id = res.equals("") || res == null ? 0 : Integer.parseInt(res);

        if (id == 0)
            return null;

        Cinema Cinema = new Cinema();
        Cinema.setId(id);
        return procurarCinema(Cinema);
    }

    public static Cinema procurarCinema(Cinema Cinema) {
        for (Cinema cine : cinemas) {
            if (Cinema.getId() == cine.getId()) {
                return cine;
            }
        }
        return null;
    }

    public static void cadastrarCinema(Shopping shopping) {
        System.out.println("Insira os dados do Cinema");
        System.out.println("Nome: ");
        String nome = System.console().readLine();
        Cinema Cinema = new Cinema();
        Cinema.setNome(nome);
        CinemaDao CinemaDao = new CinemaDao();

        CinemaDao.insert(Cinema, shopping);
    }

    public static void atualizarCinema() {
        Cinema Cinema = selecionarCinema();
        if (Cinema != null) {
            System.out.println("Insira os novos dados da Cinema");
            System.out.println("Nome: ");
            String nome = System.console().readLine();
            if (!nome.equals(" "))
                Cinema.setNome(nome);
            CinemaDao CinemaDao = new CinemaDao();
            CinemaDao.update(Cinema);
        }
    }

    public static void deletarCinema() {
        Cinema Cinema = selecionarCinema();
        if (Cinema != null) {
            CinemaDao CinemaDao = new CinemaDao();
            CinemaDao.update(Cinema);
        }
    }

}