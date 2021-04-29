package View;

import Controller.User;
import Model.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

public class EditUserController{

    @FXML
    public Text text_ID;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public Button CancelBtn;

    @FXML
    public Button udpateBtn;

    @FXML
    public RadioButton funcRadioBtn;

    @FXML
    public ToggleGroup typeGroup;

    @FXML
    public RadioButton adminRadioBtn;

    private String currentEmail;

    private static User thisUser = null;

    protected static void setThisUser(User aThisUser) {
        thisUser = aThisUser;
    }

    @FXML
    protected void initialize(){
            text_ID.setText("Número Interno: "+thisUser.getUserID());
            nameTextField.setText(thisUser.getUsername());
            emailTextField.setText(thisUser.getEmail());
            currentEmail = thisUser.getEmail();
            if(thisUser.getUserType() == 0){
                funcRadioBtn.setSelected(true);
                adminRadioBtn.setSelected(false);
            }else if(thisUser.getUserType() == 1){
                funcRadioBtn.setSelected(false);
                adminRadioBtn.setSelected(true);
            }else{
                funcRadioBtn.setSelected(false);
                adminRadioBtn.setSelected(false);
            }
    }

    public void handleCloseButtonAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) CancelBtn.getScene().getWindow();
        stage.close();
    }

    public void handleUpdateButtonAction(javafx.event.ActionEvent actionEvent) {
        String newName = nameTextField.getText();
        String newEmail = emailTextField.getText();
        int newType;

        if(!newEmail.equalsIgnoreCase(currentEmail)){
            if(User.checkEmailExists(newEmail)){
                MainScreenController.alerts(Alert.AlertType.ERROR, "Email em uso", "Este email já está em " +
                        "uso por outro utilizador.").showAndWait();
            }else{
                if(User.checkEmail(newEmail)){
                    try {
                        String stmt = "UPDATE user SET tipo = ?, nome = ?, email = ? WHERE num_interno = ?";
                        PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                        if(funcRadioBtn.isSelected()){
                            ps.setInt(1,0);
                        }else if(adminRadioBtn.isSelected()){
                            ps.setInt(1,1);
                        }else{
                            int n = Integer.parseInt(null);
                            ps.setInt(1, n);
                        }
                        ps.setString(2, nameTextField.getText());
                        ps.setString(3, emailTextField.getText());
                        ps.setInt(4, thisUser.getUserID());
                        if(ConnectDB.updateDB(ps)){
                            MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                                    "O utilizador foi atualizado com sucesso.").showAndWait();
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
            }
        }else{
            try {
                String stmt = "UPDATE user SET tipo = ?, nome = ? WHERE num_interno = ?";
                PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                if(funcRadioBtn.isSelected()){
                    ps.setInt(1,0);
                }else if(adminRadioBtn.isSelected()){
                    ps.setInt(1,1);
                }else{
                    int n = Integer.parseInt(null);
                    ps.setInt(1, n);
                }
                ps.setString(2, nameTextField.getText());
                ps.setInt(3, thisUser.getUserID());
                if(ConnectDB.updateDB(ps)){
                    MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                            "O utilizador foi atualizado com sucesso.").showAndWait();
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