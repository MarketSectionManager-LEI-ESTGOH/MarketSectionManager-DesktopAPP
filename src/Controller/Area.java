package Controller;

public class Area {

    private int numero;
    private String designacao;

    public Area(int aNumero, String aDesignacao){
        numero = aNumero;
        designacao = aDesignacao;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }
}
