package Model;

import java.sql.PreparedStatement;

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
}
