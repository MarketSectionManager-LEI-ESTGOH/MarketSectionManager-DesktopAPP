package Controller;

import Model.ArcaFrigorifica;
import Model.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

public class EditRefrigeratorController {

    @FXML
    private TextField designTF;

    @FXML
    private TextField numberTF;

    @FXML
    private TextField fabTF;

    @FXML
    private TextField tMaxTF;

    @FXML
    private TextField tMinTF;

    @FXML
    private Button editBTN;

    @FXML
    private Button cancelBTN;

    private static ArcaFrigorifica thisRefrigerator = null;

    protected static void setThisRefrigerator(ArcaFrigorifica aThisRefrigerator) {
        thisRefrigerator = aThisRefrigerator;
    }


    @FXML
    protected void initialize(){
        System.out.println("edit vrefriogerator initialize");
       designTF.setText(thisRefrigerator.getDesignacao());
       numberTF.setText(String.valueOf(thisRefrigerator.getNumero()));
       numberTF.setEditable(false);
       fabTF.setText(thisRefrigerator.getFabricante());
       tMaxTF.setText(String.valueOf(thisRefrigerator.getTempMax()));
       tMinTF.setText(String.valueOf(thisRefrigerator.getTempMin()));
    }

    public void handleCloseButtonAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBTN.getScene().getWindow();
        stage.close();
    }

    public void handleUpdateButtonAction(javafx.event.ActionEvent actionEvent) {
        String newDesign = designTF.getText();
        int number = Integer.valueOf(numberTF.getText());
        String newManufacturer = fabTF.getText();
        float newTmax = Float.parseFloat(tMaxTF.getText());
        float newTmin = Float.parseFloat(tMinTF.getText());
        if(newDesign.isEmpty()  || newManufacturer.isEmpty()){
            MainScreenController.alerts(Alert.AlertType.ERROR,"ERRO", "Todos os campos são de preenchimento obrigatório!").showAndWait();
        }else{
            if(newTmin > newTmax){
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "A Temperatura Mínima não pode ser maior que a temperatura máxima").showAndWait();
            }else{
                try {
                    String stmt = "UPDATE area_frigorifica SET designacao = ?, fabricante = ?, tem_min = ?, tem_max = ? WHERE numero = ?";
                    PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                    ps.setString(1, newDesign);
                    ps.setString(2, newManufacturer);
                    ps.setFloat(3, newTmin);
                    ps.setFloat(4,newTmax);
                    ps.setInt(5, number);
                    if(ConnectDB.updateDB(ps)){
                        MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                                "A Araca Frigorifica foi atualizada com sucesso.").showAndWait();
                        Stage stage = (Stage) cancelBTN.getScene().getWindow();
                        stage.close();
                    }else{
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                "Algo correu mal, Sem sucesso ao atualizar.").showAndWait();
                    }
                }catch (Exception e){
                    MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                            "Algo correu mal, Sem sucesso ao atualizar. "+e).showAndWait();
                }
            }
        }
    }
}
