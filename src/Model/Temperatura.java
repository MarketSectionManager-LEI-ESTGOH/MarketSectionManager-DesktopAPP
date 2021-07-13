package Model;

import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Temperatura {

    private int id, temperatura;
    private Date dataHora;
    private String utilizador, areaFrig;
    private CheckBox validate;

    /**
     * Construtor vazio da Classe
     */
    public Temperatura(){ }

    /**
     * Construtor da Classe
     * @param aId ID do Registo de Temperatura
     * @param aAreaFrig Nome da Área Frigorífica do Registo de Temperatura
     * @param aTemperatura Temperatura da Área Frigorífica do Registo de Temperatura
     * @param aDataHora Data/Hora do Registo de Temperatura
     * @param aUtilizador Utilizador que fez o Registo de Temperatura
     */
    public Temperatura(int aId, String aAreaFrig, int aTemperatura, Date aDataHora, String aUtilizador){
        id = aId;
        areaFrig = aAreaFrig;
        temperatura = aTemperatura;
        dataHora = aDataHora;
        utilizador = aUtilizador;
        validate = new CheckBox();
    }

    /**
     * Construtor da Classe
     * @param aId Id do Registo de Temperatura
     * @param aAreaFrig Nome da Área Frigorífica do Registo de Temperatura
     * @param aTemperatura Temperatura da Área Frigorífica do Registo de Temperatura
     * @param aDataHora Data/Hora do Registo de Temperatura
     * @param aUtilizador Utilizador que fez o Registo de Temperatura
     * @param aAssinado Assinatura do superior que verificou o Registo de Temperatura
     */
    public Temperatura(int aId, String aAreaFrig, int aTemperatura, Date aDataHora, String aUtilizador, int aAssinado){
        id = aId;
        areaFrig = aAreaFrig;
        temperatura = aTemperatura;
        dataHora = aDataHora;
        utilizador = aUtilizador;
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
     * Obter o ID do Registo de Temperatura
     * @return Int
     */
    public int getId() {
        return id;
    }

    /**
     * Definir o ID do Registo de Temperatura
     * @param id Int - ID do Registo de Temperatura
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obter a Temperatura da Área Frigorífica do Registo de Temperatura
     * @return Int
     */
    public int getTemperatura() {
        return temperatura;
    }

    /**
     * Definir Temperatura da Área Frigorífica do Registo de Temperatura
     * @param temperatura Int - Temperatura da Área Frigorífica do Registo de Temperatura
     */
    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * Obter Data/Hora do Registo de Temperatura
     * @return Date
     */
    public Date getDataHora() {
        return dataHora;
    }

    /**
     * Definir Data/Hora do Registo de Temperatura
     * @param dataHora Date - Data/Hora do Registo de Temperatura
     */
    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * Obter o Nome do Utilizador que fez o Registo de Temperatura
     * @return String
     */
    public String getUtilizador() {
        return utilizador;
    }

    /**
     * Definir o Nome do Utilizador que fez o Registo de Temperatura
     * @param utilizador String - Nome do Utilizador que fez o Registo de Temperatura
     */
    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    /**
     * Obter o Nome da Área Frigorífica do Registo de Temperatura
     * @return String
     */
    public String getAreaFrig() {
        return areaFrig;
    }

    /**
     * Definir o Nome da Área Frigoríofica do Registo de Temperatura
     * @param areaFrig String - Nome da Área Frigorífica do Registo de Temperatura
     */
    public void setAreaFrig(String areaFrig) {
        this.areaFrig = areaFrig;
    }

    /**
     * Obter o estado da validação superior do Registo de Temperatura
     * @return Checkbox
     */
    public CheckBox getValidate() {
        return validate;
    }

    /**
     * Definir o valor da validação superior do Registo de Temperatura
     * @param validate Checkbox - Valor da validação superior do Registo de Temperatura (checked ou unchecked)
     */
    public void setValidate(CheckBox validate) {
        this.validate = validate;
    }
}
