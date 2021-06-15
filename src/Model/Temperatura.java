package Model;

import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Temperatura {

    private int id, temperatura;
    private Date dataHora;
    private String utilizador, areaFrig;
    private CheckBox validate;

    public Temperatura(){

    }

    public Temperatura(int aId, String aAreaFrig, int aTemperatura, Date aDataHora, String aUtilizador){
        id = aId;
        areaFrig = aAreaFrig;
        temperatura = aTemperatura;
        dataHora = aDataHora;
        utilizador = aUtilizador;
        validate = new CheckBox();
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    public String getAreaFrig() {
        return areaFrig;
    }

    public void setAreaFrig(String areaFrig) {
        this.areaFrig = areaFrig;
    }

    public CheckBox getValidate() {
        return validate;
    }

    public void setValidate(CheckBox validate) {
        this.validate = validate;
    }
}
