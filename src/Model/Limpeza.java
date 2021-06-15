package Model;

import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Limpeza {

    private int id, userNumInterno;
    private String areaName, compName, userName;
    private Date dataLimp;
    private CheckBox validate;

    public Limpeza(){

    }

    public Limpeza(int aID, String aAreaName, String aCompName, Date aDataLimp, String aUserName, int aUserNumInterno){
        id=aID;
        areaName = aAreaName;
        compName = aCompName;
        dataLimp=aDataLimp;
        userName = aUserName;
        userNumInterno = aUserNumInterno;
        validate = new CheckBox();
    }

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserNumInterno() {
        return userNumInterno;
    }

    public void setUserNumInterno(int userNumInterno) {
        this.userNumInterno = userNumInterno;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userNameM) {
        this.userName = userNameM;
    }

    public Date getDataLimp() {
        return dataLimp;
    }

    public void setDataLimp(Date dataLimp) {
        this.dataLimp = dataLimp;
    }

    public CheckBox getValidate() {
        return validate;
    }

    public void setValidate(CheckBox validate) {
        this.validate = validate;
    }
}
