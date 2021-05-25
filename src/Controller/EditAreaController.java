package Controller;

import Model.Area;
import Model.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

public class EditAreaController {

    @FXML
    private TextField areaNumTextField;

    @FXML
    private TextField designAreaTextField;

    @FXML
    private Button editBTN;

    @FXML
    private Button cancelBTN;

    private static Area thisArea = null;

    protected static void setThisArea(Area aThisArea) {
        thisArea = aThisArea;
    }


    @FXML
    protected void initialize(){
        System.out.println("edit vrefriogerator initialize");
        areaNumTextField.setText(String.valueOf(thisArea.getNumero()));
        areaNumTextField.setEditable(false);
        designAreaTextField.setText(thisArea.getDesignacao());
    }

    public void handleCloseButtonAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBTN.getScene().getWindow();
        stage.close();
    }

    public void handleUpdateButtonAction(javafx.event.ActionEvent actionEvent) {
        String newDesign = designAreaTextField.getText();

        if(newDesign.isEmpty()){
            MainScreenController.alerts(Alert.AlertType.ERROR,"ERRO", "Todos os campos são de preenchimento obrigatório!").showAndWait();
        }else{
            try {
                String stmt = "UPDATE area SET designacao = ? WHERE numero = ?";
                PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                ps.setString(1, newDesign);
                ps.setInt(2, thisArea.getNumero());
                if(ConnectDB.updateDB(ps)){
                    MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                            "A Area Controlada foi atualizada com sucesso.").showAndWait();
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
