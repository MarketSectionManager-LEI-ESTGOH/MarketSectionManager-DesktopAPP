package Model;

import java.sql.PreparedStatement;

public class Area {

    private int numero;
    private String designacao;

    /**
     * Construtor da Classe
     * @param aNumero Número da Área Controlada
     * @param aDesignacao Designação da Área Controlada
     */
    public Area(int aNumero, String aDesignacao){
        numero = aNumero;
        designacao = aDesignacao;
    }

    /**
     * Obter Número da Área Controlada
     * @return Int
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Definir Número da Área Controlada
     * @param numero Int - Número da Área Controlada
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obter Designação da Área Controlada
     * @return String
     */
    public String getDesignacao() {
        return designacao;
    }

    /**
     * Definir Designação da Área Controlada
     * @param designacao String - Designação da Área Controlada
     */
    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    /**
     * Remover Área Controlada da base de dados
     * @param toRemove Objeto do Tipo Area que irá ser removido
     * @return True/False
     */
    public static boolean removeAreaFromDB(Area toRemove){
        try {
            String stmt = "DELETE FROM area WHERE numero = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, toRemove.getNumero());
            boolean bb = ConnectDB.removeFromDB(ps);
            System.out.println("from dbconn to areea: "+ bb);
            return bb;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    /**
     * Imprimir Classe no Ecrã
     */
    public String toString() {
        return "Area{" +
                "numero=" + numero +
                ", designacao='" + designacao + '\'' +
                '}';
    }
}
