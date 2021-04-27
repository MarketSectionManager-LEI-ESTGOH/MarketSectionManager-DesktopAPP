package View;

import Controller.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    private static User thisUser = null;

    protected static void setThisUser(User aThisUser) {
        thisUser = aThisUser;
    }

    @FXML
    protected void initialize(){
            text_ID.setText("Número Interno: "+thisUser.getUserID());
            nameTextField.setText(thisUser.getUsername());
            emailTextField.setText(thisUser.getEmail());
    }

}