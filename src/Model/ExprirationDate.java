package Model;

import java.sql.PreparedStatement;
import java.util.Date;

public class ExprirationDate {
    private String ean = "", numInterno = "", nome = "", mardown = "";
    private int markdownState = -1, offset = 20;
    private Date expirationDate;

    /**
     * Construtor vazio da Classe
     */
    public ExprirationDate(){}

    /**
     * Coinstrutor da Classe
     * @param aEan EAN (Código de Barras) do Produto
     * @param aNumInterno Número Interno do Produto
     * @param aNome Nome do Produto
     * @param aMarkdownState Estado do Markdown do Produto 0 - Markdown Inativo, - Mardown Ativo)
     * @param aExpDate Data de Validade do Produto
     * @param aOffset Offset do Produto
     */
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

    /**
     * Obter EAN do Produto
     * @return String
     */
    public String getEan() {
        return ean;
    }

    /**
     * Definir EAN do Produto
     * @param ean String - EAN do Produto
     */
    public void setEan(String ean) {
        this.ean = ean;
    }

    /**
     * Obter Número Interno do Produto
     * @return String
     */
    public String getNumInterno() {
        return numInterno;
    }

    /**
     * Definir Número Interno do Produto
     * @param numInterno String - Número Interno do Produto
     */
    public void setNumInterno(String numInterno) {
        this.numInterno = numInterno;
    }

    /**
     * Obter Nome do Produto
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     * Definir Nome do Produto
     * @param nome String - Nome do Produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obter Markdown do Produto
     * @return String - Sim se o Markdwon estiver ativo, Não se estiver Inativo
     */
    public String getMardown() {
        return mardown;
    }

    /**
     * Definir Markdown do Produto
     * @param mardown String - Markdown do Produto
     */
    public void setMardown(String mardown) {
        this.mardown = mardown;
    }

    /**
     * Obter estaco do Markdown do Produto
     * @return Int -  se o Markdown estiver inativo, 1 se estiver ativo
     */
    public int getMarkdownState() {
        return markdownState;
    }

    /**
     * Definir estado do Markdown do Produto
     * @param markdownState Int -  para inativo, 1 para ativo
     */
    public void setMarkdownState(int markdownState) {
        this.markdownState = markdownState;
    }

    /**
     * Obter Data de Validade do Produto
     * @return Date
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Definir Data de Validade do Produto
     * @param expirationDate Date - Data de Validade do Produto
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Obter Offset do Produto
     * @return Int
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Definir Offset do Produto
     * @param offset Int - Offset do Produto
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Remover Registo de Validade da Base de Dados
     * @param toRemove Objeto do tipo ExprirationDate que irá ser removido
     * @return True/False
     */
    public static boolean removeExpirationDateFromDB(ExprirationDate toRemove){
        try {
            String stmt = "DELETE FROM validade WHERE n_interno = '" + toRemove.getNumInterno() + "' AND validade = '" + toRemove.getExpirationDate() + "'";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Atualizar Offset de Produto na Base de Dados
     * @param toUpdate Objeto do tipo ExprirationDate que irá ser atualizado
     * @param aNewOffset Novo Offset
     * @return True/False
     */
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

    /**
     * Atualizar estado do Markdown do Produto na Base de Dados
     * @param toUpdate Objeto do tipo ExprirationDate que irá ser atualizado
     * @param aBit 0/1 consoante o estado do markdown pretendido
     * @return True/False
     */
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

    /**
     * Atualizar a Data de Validade do Produto na Base de Dados
     * @param toUpdate Objeto do tipo ExprirationDate que irá ser atualizado
     * @param aNewExpDate Nova data de validade
     * @return True/False
     */
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
    /**
     * Imprimir a Classe no Ecrã
     */
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
