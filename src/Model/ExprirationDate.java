package Model;

import java.sql.PreparedStatement;
import java.util.Date;

public class ExprirationDate {
    private String ean = "", numInterno = "", nome = "", mardown = "";
    private int markdownState = -1, offset = 20;
    private Date expirationDate;

    public ExprirationDate(){}

    public ExprirationDate(String aEan, String aNumInterno, String aNome, int aMarkdownState, Date aExpDate, int aOffset){
        ean = aEan;
        numInterno = aNumInterno;
        nome = aNome;
        expirationDate = aExpDate;
        offset = aOffset;
        if(aMarkdownState == 1){
            mardown = "Sim";
        }else if(aMarkdownState == 0){
            mardown = "Não";
        }else{
            mardown = "Sem Informação";
        }
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getNumInterno() {
        return numInterno;
    }

    public void setNumInterno(String numInterno) {
        this.numInterno = numInterno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMardown() {
        return mardown;
    }

    public void setMardown(String mardown) {
        this.mardown = mardown;
    }

    public int getMarkdownState() {
        return markdownState;
    }

    public void setMarkdownState(int markdownState) {
        this.markdownState = markdownState;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public static boolean removeExpirationDateFromDB(ExprirationDate toRemove){
        try {
            String stmt = "DELETE FROM validade WHERE n_interno = '" + toRemove.getNumInterno() + "' AND validade = '" + toRemove.getExpirationDate() + "'";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){

        }
        return false;
    }

    public static boolean updateOffsetInDB(ExprirationDate toUpdate, int aNewOffset){
        try {
            String stmt = "UPDATE validade SET offset = ? WHERE n_interno = '" + toUpdate.getNumInterno() + "'";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1,aNewOffset);
            return ConnectDB.updateDB(ps);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateMarkdownInDB(ExprirationDate toUpdate, int aBit){
        try {
            String stmt = "UPDATE validade SET markdown = ? WHERE n_interno = '" + toUpdate.getNumInterno() + "'";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1,aBit);
            return ConnectDB.updateDB(ps);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateExpDateInDB(ExprirationDate toUpdate, String aNewExpDate){
        try {
            String stmt = "UPDATE validade SET validade = ? WHERE n_interno = '" + toUpdate.getNumInterno() + "' AND validade = '" + toUpdate.getExpirationDate() + "'";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setString(1,aNewExpDate);
            return ConnectDB.updateDB(ps);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "ExprirationDate{" +
                "ean='" + ean + '\'' +
                ", numInterno='" + numInterno + '\'' +
                ", nome='" + nome + '\'' +
                ", mardown='" + mardown + '\'' +
                ", markdownState=" + markdownState +
                ", offset=" + offset +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
