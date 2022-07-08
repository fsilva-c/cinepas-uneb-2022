package model;

public class SalaProjecao3D extends SalaProjecao {

    protected String equipamentos;

    public SalaProjecao3D() {

    }

    public SalaProjecao3D(String equipamentos) {
        this.equipamentos = equipamentos;
    }

    public String getEquipamentos() {
        return this.equipamentos;
    }

    public void setEquipamentos(String equipamentos) {
        this.equipamentos = equipamentos;
    }

    @Override
    public String toString() {
        return "[ type: 3D ]" + super.toString();
    }

}
