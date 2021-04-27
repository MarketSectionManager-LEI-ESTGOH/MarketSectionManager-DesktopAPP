package View;

import Controller.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

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
                System.out.println("Email ja existe");
            }else{
                if(User.checkEmail(newEmail)){
                    System.out.println("Cool shit");
                }else{
                    System.out.println("Email invalido");
                }
            }
        }


    }
}