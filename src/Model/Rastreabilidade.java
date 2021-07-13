package Model;

import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Rastreabilidade {

    private int id, lote;
    private Date dataEntrada;
    private String origem,produto, utilizador, fornecedor;
    private CheckBox validate;

    /**
     * Construtor Vazio da Classe
     */
    public  Rastreabilidade(){}

    /**
     * Construtor da Classe
     * @param aID ID da Rastreabilidade
     * @param aLote Lote do Produto da Rastreabilidade
     * @param aDataEntrada Data de Entrada do Produto da Rastreabilidade
     * @param aOrigem Origem do Produto da Rastreabilidade
     * @param aProduto Produto da Rastreabilidade
     * @param aUtilizador Utilizador que Registou a Rastreabilidade
     * @param aFornecedor Fornecedor do Produto da Rastreabilidade
     */
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

    /**
     * Construtor da Classe
     * @param aID ID da Rastreabilidade
     * @param aLote Lote do Produto da Rastreabilidade
     * @param aDataEntrada Data de Entrada do Produto da Rastreabilidade
     * @param aOrigem Origem do Produto da Rastreabilidade
     * @param aProduto Produto da Rastreabilidade
     * @param aUtilizador Utilizador que registou a Rastreabilidade
     * @param aFornecedor Fornecedor do Prtoduto da Rastreabilidade
     * @param aAssinado Assinatura da Rastreabilidade por parte de um superior
     */
    public Rastreabilidade(int aID, int aLote, Date aDataEntrada, String aOrigem, String aProduto, String aUtilizador, String aFornecedor, int aAssinado){
        id = aID;
        lote = aLote;
        dataEntrada = aDataEntrada;
        origem = aOrigem;
        produto = aProduto;
        utilizador = aUtilizador;
        fornecedor = aFornecedor;
        validate = new CheckBox(){
            @Override
            public void arm() {
            }
        };
        if(aAssinado>0){
            validate.setSelected(true);
        }
    }

    /**
     * Obter ID da Rastreabilidade
     * @return Int
     */
    public int getId() {
        return id;
    }

    /**
     * Definir ID da Rastreabilidade
     * @param id Int - ID da Rastreabilidade
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obter Lote do Produto da Rastreabilidade
     * @return Int
     */
    public int getLote() {
        return lote;
    }

    /**
     * Definir Lote do Produto da Rastreabilidade
     * @param lote Int - Lote do Produto da Rastreabilidade
     */
    public void setLote(int lote) {
        this.lote = lote;
    }

    /**
     * Obter Data de Entrada do Produto da Rastreabilidade
     * @return Date
     */
    public Date getDataEntrada() {
        return dataEntrada;
    }

    /**
     * Definir Data de entrada do Produto da Rastreabilidade
     * @param dataEntrada Date - Data de Entrada do Produto da Rastreabilidade
     */
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /**
     * Obter Origem do Produto da Rastreabilidade
     * @return String
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * Definir Origem do Produto da Rastreabilidade
     * @param origem String - Origem do Produto da Rastreabilidade
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    /**
     * Obter Produto da Rastreabilidade
     * @return String
     */
    public String getProduto() {
        return produto;
    }

    /**
     * Definir Produto da Rastreabilidade
     * @param produto String - Nome do Produto da Rastreabilidade
     */
    public void setProduto(String produto) {
        this.produto = produto;
    }

    /**
     * Obter utilizador que registou a Rastreabilidade
     * @return String
     */
    public String getUtilizador() {
        return utilizador;
    }

    /**
     * Definir o utilizador que registou a rastreabilidade
     * @param utilizador String - Nome do utilizador que registou a rastreabilidade
     */
    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    /**
     * Obter o Fornecedor do Produto da Rastreabilidade
     * @return String
     */
    public String getFornecedor() {
        return fornecedor;
    }

    /**
     * Definir o Fornecedor do Produto da Rastreabilidade
     * @param fornecedor String - fornecedor do Produto da Rastreabilidade
     */
    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    /**
     * Obter o Estado da validação superior da Rastreabilidade
     * @return Checkbox
     */
    public CheckBox getValidate() {
        return validate;
    }

    /**
     * Definir o estado da validação superior da Rastreabilidade
     * @param validate Checkbox - Estado da validação superior da Rastreabilidade (checked ou unchecked)
     */
    public void setValidate(CheckBox validate) {
        this.validate = validate;
    }
}
