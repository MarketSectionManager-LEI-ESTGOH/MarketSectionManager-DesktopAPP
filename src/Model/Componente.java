package Model;

import javafx.scene.control.CheckBox;

public class Componente {
    private int id;
    private String nome;
    private CheckBox check;

    /**
     * Construtor Vazio da Classe
     */
    public Componente(){}

    /**
     * Construtor da Classe
     * @param aId ID do Componente
     * @param aNome Nome do Componente
     */
    public Componente(int aId, String aNome){
        this.id = aId;
        this.nome = aNome;
        this.check = new CheckBox();
    }

    /**
     * Obter ID do Componente
     * @return Int
     */
    public int getId() {
        return id;
    }

    /**
     * Obter Nome do Componente
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     * Definir ID do Componente
     * @param id Int - ID do Componente
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Definir Nome do Componente
     * @param nome String - Nome do Componente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obter Estado do componente (Se está Checked ou não=
     * @return Checkbox
     */
    public CheckBox getCheck() {
        return check;
    }

    /**
     * Definir Estado do Componente
     * @param check Checkbox
     */
    public void setCheck(CheckBox check) {
        this.check = check;
    }

    @Override
    /**
     * Imprimir Classe no Ecrã
     */
    public String toString() {
        return "Componente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
