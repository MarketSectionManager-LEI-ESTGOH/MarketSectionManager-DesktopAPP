package Model;

import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Limpeza {

    private int id, userNumInterno;
    private String areaName, compName, userName;
    private Date dataLimp;
    private CheckBox validate;

    /**
     * Construtor Vazio da Classe
     */
    public Limpeza(){}

    /**
     * Construtor da classe
     * @param aID ID da Limpeza
     * @param aAreaName Nome da Área da Limpeza
     * @param aCompName Nome do Componente da Limpeza
     * @param aDataLimp Data da Limpeza
     * @param aUserName Utilizador que fez a Limpeza
     * @param aUserNumInterno Número Interno do utilizador que fez a Limpeza
     */
    public Limpeza(int aID, String aAreaName, String aCompName, Date aDataLimp, String aUserName, int aUserNumInterno){
        id=aID;
        areaName = aAreaName;
        compName = aCompName;
        dataLimp=aDataLimp;
        userName = aUserName;
        userNumInterno = aUserNumInterno;
        validate = new CheckBox();
    }

    /**
     * Contrutor da Classe
     * @param aID ID da Limpeza
     * @param aAreaName Nome da Área da Limpeza
     * @param aCompName Nome do Componente da Limpeza
     * @param aDataLimp Data da Limpeza
     * @param aUserName Nome do Utilizador que fez a Limpeza
     * @param aUserNumInterno Número Interno do Utilizador que fez a Limpeza
     * @param aAssinado Assinatura Superior da Limpeza
     */
    public Limpeza(int aID, String aAreaName, String aCompName, Date aDataLimp, String aUserName, int aUserNumInterno, int aAssinado){
        id=aID;
        areaName = aAreaName;
        compName = aCompName;
        dataLimp=aDataLimp;
        userName = aUserName;
        userNumInterno = aUserNumInterno;
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
     * Obter ID da Limpeza
     * @return Int
     */
    public int getId() {
        return id;
    }

    /**
     * Definir ID da Limpeza
     * @param id Int - ID da Limpeza
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obter Número Interno do Utilizador que fez a Limpeza
     * @return Int
     */
    public int getUserNumInterno() {
        return userNumInterno;
    }

    /**
     * Definir o Número Interno do Utilizador que fez a Limpeza
     * @param userNumInterno Int - Número Interno do Utilizador que fez a Limpeza
     */
    public void setUserNumInterno(int userNumInterno) {
        this.userNumInterno = userNumInterno;
    }

    /**
     * Obter a Área da Limpeza
     * @return String
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * Definir a área da Limpeza
     * @param areaName String -  Nome da Área da Limpeza
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * Obter o componente da Limpeza
     * @return String
     */
    public String getCompName() {
        return compName;
    }

    /**
     * Definir o Nome do Componente da Limpeza
     * @param compName String - Nome do Componente da Limpeza
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }

    /**
     * Obter o nome do utilizador que fez a Limpeza
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Definir Nome do Utilizador que fez a Limpeza
     * @param userNameM String - Nome do Utilizador que fez a Limpeza
     */
    public void setUserName(String userNameM) {
        this.userName = userNameM;
    }

    /**
     * Obter a Data da Limpeza
     * @return Date
     */
    public Date getDataLimp() {
        return dataLimp;
    }

    /**
     * Definir a Data da Limpeza
     * @param dataLimp Date - Data da Limpeza
     */
    public void setDataLimp(Date dataLimp) {
        this.dataLimp = dataLimp;
    }

    /**
     * Obter o estado da validação superior da Limpeza
     * @return Checkbox
     */
    public CheckBox getValidate() {
        return validate;
    }

    /**
     * Definir o Estado da validação superior da Limpeza
     * @param validate Checkbox - Estado da validação da Limpeza (checked/unchecked)
     */
    public void setValidate(CheckBox validate) {
        this.validate = validate;
    }
}
