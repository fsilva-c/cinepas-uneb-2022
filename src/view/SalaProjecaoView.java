package view;

import model.SalaProjecao;
import model.SalaProjecao3D;
import model.Cinema;
import java.util.*;

import dao.SalaProjecaoDao;

public abstract class SalaProjecaoView {
    private static ArrayList<SalaProjecao> salas = null;

    public static void setarLista(Cinema cinema) {
        SalaProjecaoDao salaDao = new SalaProjecaoDao();
        if (cinema == null)
            salas = new ArrayList<SalaProjecao>(salaDao.select());
        else
            salas = new ArrayList<SalaProjecao>(salaDao.select(cinema));
    }

    public static void setLista(ArrayList<SalaProjecao> Salas) {
        SalaProjecaoDao salaDao = new SalaProjecaoDao();
        if (Salas == null)
            salas = new ArrayList<SalaProjecao>(salaDao.select());
        else
            salas = Salas;
    }

    public static void listarSalas() {
        System.out.println("\nLista de salas:");
        for (SalaProjecao sala : salas) {
            // String salaTipo3D = (sala instanceof SalaProjecao3D)
            // ? " Tipo: 3D"
            // : " Tipo Simples";
            // System.out.println("id: " + sala.getId() + " Número: " + sala.getNumero() + "
            // Capacidade: "
            // + sala.getCapacidade() + salaTipo3D);
            System.out.println(sala.toString());
            System.out.println("");
        }
        System.out.println("<===========================================>");
    }

    public static SalaProjecao selecionarSala() {
        listarSalas();
        System.out.println("Id da sala: ");

        String res = System.console().readLine();
        int id = res.equals("") || res == null ? 0 : Integer.parseInt(res);

        if (id == 0)
            return null;

        SalaProjecao sala = new SalaProjecao();
        sala.setId(id);
        return procurarSala(sala);
    }

    public static SalaProjecao procurarSala(SalaProjecao sala) {
        for (SalaProjecao sal : salas) {
            if (sal.getId() == sala.getId()) {
                return sal;
            }
        }
        return null;
    }

    public static void cadastrarSala(Cinema cinema) {
        SalaProjecao sala = null;
        System.out.println("Insira os dados do Sala");
        System.out.println("numero: ");
        String numero = System.console().readLine();
        System.out.println("Capacidade: ");
        int capacidade = Integer.parseInt(System.console().readLine());
        System.out.println("A sala é 3D?");
        System.out.println("S/N: ");
        String tipoSalaCheck = System.console().readLine();
        if (tipoSalaCheck.toUpperCase().equals("S")) {
            System.out.println("Equipamentos: ");
            String equipamentos = System.console().readLine();
            sala = new SalaProjecao3D(equipamentos);
        } else {
            sala = new SalaProjecao();
        }
        sala.setCapacidade(capacidade);
        sala.setNumero(numero);
        SalaProjecaoDao salaDao = new SalaProjecaoDao();
        salaDao.insert(sala, cinema);
    }

    public static void atualizarSala() {
        SalaProjecao sala = selecionarSala();
        if (sala != null) {
            System.out.println("Insira os novos dados do sala");
            System.out.println("numero: ");
            String numero = System.console().readLine();
            System.out.println("Capacidade: ");
            int capacidade = Integer.parseInt(System.console().readLine());
            if (!numero.equals(" "))
                sala.setNumero(numero);
            if (capacidade > 0)
                sala.setCapacidade(capacidade);
            if (sala instanceof SalaProjecao3D) {
                System.out.println("Equipamentos: ");
                String equipamentos = System.console().readLine();
                if (!equipamentos.equals(" "))
                    ((SalaProjecao3D) sala).setEquipamentos(equipamentos);
            }
            SalaProjecaoDao salaDao = new SalaProjecaoDao();
            salaDao.update(sala);
        }
    }

    public static void deletarSala() {
        SalaProjecao sala = selecionarSala();
        if (sala != null) {
            SalaProjecaoDao salaDao = new SalaProjecaoDao();
            salaDao.delete(sala);
        }
    }

}
