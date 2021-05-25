package Model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;

public class ArcaFrigorifica {

    private int numero;
    private String designacao, fabricante, userName;
    private Date addDate, limpDate;
    private float tempMin, tempMax;
    private BigDecimal userLimp;

    public ArcaFrigorifica(int aNumero, String aDesignacao, String aFabricante, Date aAddDate, float aTempMin, float aTempMax, Date aLimpDate, BigDecimal aUserLimp, String aUserName){
        numero = aNumero;
        designacao = aDesignacao;
        fabricante = aFabricante;
        addDate = aAddDate;
        tempMin = aTempMin;
        tempMax = aTempMax;
        limpDate = aLimpDate;
        userLimp = aUserLimp;
        userName = aUserName;
    }

    public static boolean removeRefrigeratorFromDB(ArcaFrigorifica toRemove){
        try {
            String stmt = "DELETE FROM area_frigorifica WHERE numero = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, toRemove.getNumero());
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){

        }
        return false;
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

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getLimpDate() {
        return limpDate;
    }

    public void setLimpDate(Date limpDate) {
        this.limpDate = limpDate;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public BigDecimal getUserLimp() {
        return userLimp;
    }

    public void setUserLimp(BigDecimal userLimp) {
        this.userLimp = userLimp;
    }

    public String getUserName() {
        return userName;
    }
}
