package View;

import Model.ConnectDB;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;

public class MainScreenController {

    public Button editProfileBtn = new Button();
    public Button logoutBtn = new Button();
    private Stage currentStage = null;
    public Button registerBTN = new Button();
    public Pane registerPane = new Pane();
    public Button addUserBtn = new Button();
    public Button collapseBtn = new Button();
    public ComboBox userTypeCombo = new ComboBox();
    public TextField userNameTF = new TextField();
    public TextField userNumberTF = new TextField();
    public TextField userEmailTF = new TextField();
    public PasswordField userPassPF = new PasswordField();




    public void start() throws Exception{
        Stage MainScreen = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        MainScreen.setTitle("Market Section Manager");
        MainScreen.setScene(new Scene(root, 1200, 602));
        //MainScreen.initStyle(StageStyle.UNDECORATED);
        MainScreen.setResizable(false);
        MainScreen.centerOnScreen();
        MainScreen.show();
    }


    public void editProfile(){
        System.out.println("edit profile btn clicked");

    }

    public void logout() throws Exception{
        currentStage = (Stage) logoutBtn.getScene().getWindow();
        currentStage.close();
        View.Main.u = null;
        new Main().start(new Stage());

    }

    public void register(){
        System.out.println("register btn clicked!!");
        registerPane.setVisible(true);
    }

    public void addUser(){
        System.out.println("add user btn clicked!!");
            try {
                ConnectDB.loadProperties();
                if(View.Main.u.registerUser(userTypeCombo.getSelectionModel().getSelectedIndex(), userNameTF.getText(), Integer.parseInt(userNumberTF.getText()), userPassPF.getText(), userEmailTF.getText())){
                    alerts(Alert.AlertType.INFORMATION,"SUCESSO","Utilizador inserido com sucesso!").showAndWait();
                }
                clearUserRegistrationFileds();
            }catch(NumberFormatException nfe){
                alerts(Alert.AlertType.INFORMATION,"ERRO","O campo número de funcionário apenas aceita números").showAndWait();
                clearUserRegistrationFileds();
            }catch(Exception e){
                alerts(Alert.AlertType.INFORMATION,"ERRO","Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
                clearUserRegistrationFileds();
            }

    }

    private void clearUserRegistrationFileds(){
        userTypeCombo.getSelectionModel().select(-1);
        userNameTF.setText("");
        userNumberTF.setText("");
        userPassPF.setText("");
        userEmailTF.setText("");
    }

    private Alert alerts(Alert.AlertType aAlertType, String aTitle, String aText){
        Alert generalAlert = new Alert(aAlertType);
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText(aTitle);
        generalAlert.setContentText(aText);
        return generalAlert;
    }

    public void collapse(){
        clearUserRegistrationFileds();
        registerPane.setVisible(false);
    }
}
