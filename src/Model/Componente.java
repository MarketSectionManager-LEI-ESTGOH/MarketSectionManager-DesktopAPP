package Model;

import javafx.scene.control.CheckBox;

public class Componente {
    private int id;
    private String nome;
    private CheckBox check;

    public Componente(){
    }

    public Componente(int aId, String aNome){
        this.id = aId;
        this.nome = aNome;
        this.check = new CheckBox();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "Componente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
