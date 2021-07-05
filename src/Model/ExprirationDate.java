package Model;

import java.util.Date;

public class ExprirationDate {
    private String ean = "", numInterno = "", nome = "", mardown = "";
    private int markdownState = -1;
    private Date expirationDate;

    public ExprirationDate(){}

    public ExprirationDate(String aEan, String aNumInterno, String aNome, int aMarkdownState, Date aExpDate){
        ean = aEan;
        numInterno = aNumInterno;
        nome = aNome;
        expirationDate = aExpDate;
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
}
