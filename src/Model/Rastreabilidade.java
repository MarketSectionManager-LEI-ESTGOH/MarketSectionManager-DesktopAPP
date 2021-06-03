package Model;

import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Rastreabilidade {

    private int id, lote;
    private Date dataEntrada;
    private String origem,produto, utilizador, fornecedor;
    private CheckBox validate;

    public  Rastreabilidade(){

    }

    public Rastreabilidade(int aID, int aLote, Date aDataEntrada, String aOrigem, String aProduto, String aUtilizador, String aFornecedor){
        id = aID;
        lote = aLote;
        dataEntrada = aDataEntrada;
        origem = aOrigem;
        produto = aProduto;
        utilizador = aUtilizador;
        fornecedor = aFornecedor;
        validate = new CheckBox();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public CheckBox getValidate() {
        return validate;
    }

    public void setValidate(CheckBox validate) {
        this.validate = validate;
    }
}
