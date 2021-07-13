package Controller;

import Model.ConnectDB;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRefrigeratorController {

    public Button insertArcaInDB;
    public TextField refrigeratorDesign;
    public TextField refrigeratorNumber;
    public TextField refrigeratorManufacturer;
    public TextField refrigeratorMaxTemp;
    public TextField refrigeratorMinTemp;
    private static DateFormat timstampRefrigerator = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");


    /**
     * Adicionar Área Refrigerada
     */
    public void insertRefrigerator(){
        System.out.println("insert refrigerator in db button clicked!");
        if((!refrigeratorDesign.getText().toString().isEmpty()) && (!refrigeratorNumber.getText().toString().isEmpty())
                && (!refrigeratorManufacturer.getText().toString().isEmpty())
                && (!refrigeratorMaxTemp.getText().toString().isEmpty())
                && (!refrigeratorMinTemp.getText().toString().isEmpty())){
            if(Float.valueOf(refrigeratorMaxTemp.getText()) < Float.valueOf(refrigeratorMinTemp.getText())){
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "A temperatura máxima não pode ser mais baixa que a temperatura mínima!").showAndWait();
            }else {
                if (registerRefrigerator(Integer.parseInt(refrigeratorNumber.getText()), refrigeratorDesign.getText(), refrigeratorManufacturer.getText(),
                        timstampRefrigerator.format(new Date()), Float.parseFloat(refrigeratorMinTemp.getText()), Float.parseFloat(refrigeratorMaxTemp.getText()))) {
                    MainScreenController.alerts(Alert.AlertType.INFORMATION, "SUCESSO", "A Arca Frigorifica foi inserida com sucesso!").showAndWait();
                    clearRefrigeratorFields();
                } else {
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro ao inserir a Arca Frigorifica, por favor tente novamente!").showAndWait();
                }
            }
        }else{
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Todos os Campos são de Preenchimento Obrigatório.\n Prencha todos os campos e tente novamente!").showAndWait();
        }
    }

    /**
     * Limpar Campos de inserção da área refrigerada após o término da mesma operação
     */
    private void clearRefrigeratorFields(){
        refrigeratorManufacturer.setText("");
        refrigeratorMinTemp.setText("");
        refrigeratorMaxTemp.setText("");
        refrigeratorNumber.setText("");
        refrigeratorDesign.setText("");
    }

    /**
     * Registar área Refrigerada na base de dados
     * @param aNumber Número da Área Refrigerada
     * @param aDesignation Designação da Área Refrigerada
     * @param aManufacturer Fabricante da Área Refrigerada
     * @param aInsertionDate Date de Inserção da Área Refrigerada
     * @param aMinTemp Temperatura Minima da Área Refrigerada
     * @param aMaxTemp Temperatura Máxima da Área Refrigerada
     * @return True/False
     */
    public static boolean registerRefrigerator(int aNumber, String aDesignation, String aManufacturer, String aInsertionDate, float aMinTemp, float aMaxTemp){
        try {
            String stmt = "INSERT INTO area_frigorifica (numero, designacao, fabricante, d_t_adicao, tem_min, tem_max) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumber);
            ps.setString(2,aDesignation);
            ps.setString(3, aManufacturer);
            ps.setString(4, aInsertionDate);
            ps.setFloat(5, aMinTemp);
            ps.setFloat(6, aMaxTemp);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
