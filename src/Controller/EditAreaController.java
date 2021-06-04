package Controller;

import Model.Area;
import Model.Componente;
import Model.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditAreaController {
    @FXML
    private TextField editCANumberTF;
    @FXML
    private TextField editCADesignTF;
    @FXML
    private Button editCABTN;
    @FXML
    private TableView<Componente> compsTbl;
    @FXML
    private TableColumn<Componente, Integer> compID;
    @FXML
    private TableColumn<Componente, String> compColumn;
    @FXML
    private TableColumn<Componente, CheckBox> chekColumn;
    @FXML
    private TextArea editCACompsAtuaisTA;
    @FXML
    private Button removeCompBTN;
    private static Area initialArea = null, thisArea = null;

    protected static void setThisArea(Area aThisArea) {
        thisArea = aThisArea;
    }
    private int dbID = -1;

    @FXML
    protected void initialize(){
        System.out.println("edit controlled area initialize");
        editCANumberTF.setEditable(false);
        editCANumberTF.setDisable(true);
        editCANumberTF.setText(String.valueOf(thisArea.getNumero()));
        editCADesignTF.setText(thisArea.getDesignacao());
        dbID = getControlledAreaDatabaseID(thisArea.getNumero());
    }



    public void handleUpdateButtonAction(javafx.event.ActionEvent actionEvent) {
        String newDesign = "";

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
                    Stage stage = (Stage) editCABTN.getScene().getWindow();
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

    private int getControlledAreaDatabaseID(int aNumInterno) {
        try {
            String stmt = "SELECT id FROM area WHERE numero = ?";
            PreparedStatement ps = null;
            ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumInterno);
            return ConnectDB.getControlledAreaID(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }
}
