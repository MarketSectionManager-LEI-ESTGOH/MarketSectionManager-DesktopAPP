package Controller;

import Model.ConnectDB;
import Model.Fornecedor;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

public class EditFornecedorController {

    @FXML
    private Button CancelBtn;

    @FXML
    private Button udpateBtn;

    @FXML
    private TextField NameFornTF;

    @FXML
    private TextField moradaFornTF;

    @FXML
    private TextField contactoFornTF;

    @FXML
    private TextField emailFornTF;

    @FXML
    private Text IDshowTF;

    private static Fornecedor thisForn = null;
    private String currentEmail;

    protected static void setThisForn(Fornecedor aThisForn) {
        thisForn = aThisForn;
    }

    @FXML
    protected void initialize(){
            IDshowTF.setText(thisForn.getIdentificador());
            NameFornTF.setText(thisForn.getNome());
            contactoFornTF.setText(String.valueOf(thisForn.getContacto()));
            emailFornTF.setText(thisForn.getEmail());
            moradaFornTF.setText(thisForn.getMorada());
            currentEmail = thisForn.getEmail();
    }

    public void handleCloseButtonAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) CancelBtn.getScene().getWindow();
        stage.close();
    }

    public void handleUpdateButtonAction(javafx.event.ActionEvent actionEvent) {
        String newName = NameFornTF.getText();
        String newEmail = emailFornTF.getText();

        if(!newEmail.equalsIgnoreCase(currentEmail)){
            if(User.checkEmail(newEmail)){
                try {
                    String stmt = "UPDATE fornecedor SET nome = ?, email = ?, morada = ?, contacto = ? WHERE identificador = ?";
                    PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                    ps.setString(1, NameFornTF.getText());
                    ps.setString(2, emailFornTF.getText());
                    ps.setString(3, moradaFornTF.getText());
                    ps.setInt(4, Integer.parseInt(contactoFornTF.getText()));
                    ps.setString(5, thisForn.getIdentificador());
                    if(ConnectDB.updateDB(ps)){
                        MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                                "O Fornecedor foi atualizado com sucesso.").showAndWait();
                        Stage stage = (Stage) CancelBtn.getScene().getWindow();
                        stage.close();
                    }else{
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                "Algo correu mal, Sem sucesso ao atualizar.").showAndWait();
                    }
                }catch (Exception e){
                    MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                            "Algo correu mal, Sem sucesso ao atualizar. "+e).showAndWait();
                }
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "Email errado", "Não segue o formato " +
                        "correto de email.").showAndWait();
            }
        }else{
            try {
                String stmt = "UPDATE fornecedor SET nome = ?, email = ?, morada = ?, contacto = ? WHERE identificador = ?";
                PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                ps.setString(1, NameFornTF.getText());
                ps.setString(2, emailFornTF.getText());
                ps.setString(3, moradaFornTF.getText());
                ps.setInt(4, Integer.parseInt(contactoFornTF.getText()));
                ps.setString(5, thisForn.getIdentificador());
                if(ConnectDB.updateDB(ps)){
                    MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                            "O Fornecedor foi atualizado com sucesso.").showAndWait();
                    Stage stage = (Stage) CancelBtn.getScene().getWindow();
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