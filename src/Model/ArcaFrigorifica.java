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

    /**
     * Construtor da Classe
     * @param aNumero Número da Área Frigorífica
     * @param aDesignacao Designação da Área Frigorífica
     * @param aFabricante Fabricante da Área Frigorífica
     * @param aAddDate Data em que a Área Frigorífica foi adicionada
     * @param aTempMin Temperatura Mínima da Área Frigorífica
     * @param aTempMax Temperatura Méxima da Área Frigorífica
     * @param aLimpDate Última Limpeza da Área Frigorífica
     * @param aUserLimp Utilizador que efetuou a Última Limpeza à Área Frigorífica
     * @param aUserName Nome de Utilizador
     */
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

    /**
     * Remover Área Frigorífica da Base de Dados
     * @param toRemove Objeto to Tipo ArcaFrigorifica que irá ser removido
     * @return True/False
     */
    public static boolean removeRefrigeratorFromDB(ArcaFrigorifica toRemove){
        try {
            String stmt = "DELETE FROM area_frigorifica WHERE numero = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, toRemove.getNumero());
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Obter Número da Área Frigorífica
     * @return Int
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Definir Número da Área Frigorífica
     * @param numero Int - Número da Área Frigorífica
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obter Designação da Área Frigorífica
     * @return String
     */
    public String getDesignacao() {
        return designacao;
    }

    /**
     * Definir Designação da Área Frigorífica
     * @param designacao String - Designação da Área Frigorífica
     */
    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    /**
     * Obter Fabricante da Área Frigorífica
     * @return String
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * Definir Fabricante da Área Frigorífica
     * @param fabricante String - Fabricante da Área Frigorífica
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * Obter Data em que a Área Frigorífica foi adicionada
     * @return Date
     */
    public Date getAddDate() {
        return addDate;
    }

    /**
     * Definir a Data em que a Área Frigorífica foi adicionada
     * @param addDate Date - Data em que a Área Frigorífica foi adicionada
     */
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    /**
     * Obter Data da última limpeza da Área Frigorífica
     * @return Date
     */
    public Date getLimpDate() {
        return limpDate;
    }

    /**
     * Definir Data da última Limpeza da Área Frigorífica
     * @param limpDate Date - Data da última limpeza da Área Frigorífica
     */
    public void setLimpDate(Date limpDate) {
        this.limpDate = limpDate;
    }

    /**
     * Obter Temperatura mínima da Área Frigorífica
     * @return Float
     */
    public float getTempMin() {
        return tempMin;
    }

    /**
     * Definir Temperatura mínima da Área Frigorífica
     * @param tempMin Float - Temperatura Mínima da Área Frigorífica
     */
    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    /**
     * Obter Temperatura máxima da Área Frigorífica
     * @return Float
     */
    public float getTempMax() {
        return tempMax;
    }

    /**
     * Definir a Temperatura máxima da Área Frigorífica
     * @param tempMax Float - Temperatuar Máxima da Área Frigorífica
     */
    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    /**
     * Obter o utilizador que efetuoo a última limpeza da Área Frigorífica
     * @return BigDecimal
     */
    public BigDecimal getUserLimp() {
        return userLimp;
    }

    /**
     * Definir o utilizador que efetuou a última limmpeza da Área Frigorífica
     * @param userLimp BigDecimal - Utilizador que efetuou a última limpeza da Área Frigorífica
     */
    public void setUserLimp(BigDecimal userLimp) {
        this.userLimp = userLimp;
    }

    /**
     * Obter o Nome de utilizador
     * @return String
     */
    public String getUserName() {
        return userName;
    }
}
