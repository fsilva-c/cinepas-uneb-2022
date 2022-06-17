package model;

public class Shopping {

    private int id;
    private String name;
    private Cinema cinema;

    public Shopping() { }

    public Shopping(int id, String name, Cinema cinema) {
        this.id = id;
        this.name = name;
        this.cinema = cinema;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cinema getCinema() {
        return this.cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
    
}
