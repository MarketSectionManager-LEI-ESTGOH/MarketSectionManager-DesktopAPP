package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainScreenController{

    public Button editProfileBtn = new Button();
    private Stage currentStage = null;
    public Button editBTN = new Button();
    public Button addUserBtn = new Button();
    public Button collapseBtn = new Button();
    public ComboBox userTypeCombo = new ComboBox();
    public TextField userNameTF = new TextField();
    public TextField userNumberTF = new TextField();
    public TextField userEmailTF = new TextField();
    public PasswordField userPassPF = new PasswordField();
    public PasswordField userPassConfPF = new PasswordField();

    @FXML
    private Pane receivedPane;

    public Button addCAComponentsBTN;
    public Button addCABTN;


    /**
     * Referencia apresentação de dados: 20/04/2021
     * https://www.youtube.com/watch?v=tw_NXq08NUE
     *
     * Referencia filtro de pesquisa: 26/04/2021
     * https://www.youtube.com/watch?v=FeTrcNBVWtg
     *
     */
    public void start() throws Exception{
        Stage MainScreen = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
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
        currentStage = (Stage) editProfileBtn.getScene().getWindow();
        currentStage.close();
        Main.u = null;
        new Main().start(new Stage());

    }

    public void addUser(){
        System.out.println("add user btn clicked!!");
        ConnectDB.loadProperties();
        if(userTypeCombo.getSelectionModel().getSelectedIndex() != -1){
            if(userPassPF.getText().equals(userPassConfPF.getText())){
                if(User.checkEmail(userEmailTF.getText())){
                    if(!User.checkEmailExists(userEmailTF.getText())){
                        if(Main.u.registerUser(userTypeCombo.getSelectionModel().getSelectedIndex(), userNameTF.getText(), Integer.parseInt(userNumberTF.getText()), userPassPF.getText(), userEmailTF.getText())){
                            alerts(Alert.AlertType.INFORMATION,"SUCESSO","O " + userTypeCombo.getSelectionModel().getSelectedItem() + " (" + userNumberTF.getText() + ") - " + userNameTF.getText() + " foi introduzido com sucesso!").showAndWait();
                        }else{
                            alerts(Alert.AlertType.ERROR, "ERRO", "Erro ao introduzir o utilizador, por favor tente novamente!").showAndWait();
                        }
                        clearUserRegistrationFileds();
                    }else{
                        alerts(Alert.AlertType.ERROR, "ERRO", "O Email introduzido já está em uso!").showAndWait();
                    }
                }else{
                    alerts(Alert.AlertType.ERROR, "ERRO", "O Email introduzido não é válido!\nPor favor introduza o email no formato [nome]@[servidor].[domínio]\n(Ex.: john.doe@msm.com)").showAndWait();
                }
            }else{
                alerts(Alert.AlertType.ERROR, "ERRO", "As Palavras Passe não Coíncidem, por favor tente novamente!").showAndWait();
            }
        }else{
            alerts(Alert.AlertType.ERROR, "ERRO", "O tipo de utilizador é de preenchimento obrigatório!").showAndWait();
        }
    }

    private void clearUserRegistrationFileds(){
        userTypeCombo.getSelectionModel().select(-1);
        userNameTF.setText("");
        userNumberTF.setText("");
        userPassPF.setText("");
        userPassConfPF.setText("");
        userEmailTF.setText("");
    }

    protected static Alert alerts(Alert.AlertType aAlertType, String aTitle, String aText){
        Alert generalAlert = new Alert(aAlertType);
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText(aTitle);
        generalAlert.setContentText(aText);
        return generalAlert;
    }

    public void collapse(){
        System.out.println("home (collapse) clicked!");
        clearUserRegistrationFileds();
        if(receivedPane != null){
            receivedPane.setVisible(false);
        }
    }

    public void showUsersTable(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/AllUsersPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        }catch(Exception e ){
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showRefrigeratorsTable(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/RefrigeratorsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        }catch(Exception e ){
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showProductsTable(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/ProductsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        }catch(Exception e ){
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showControlledAreasTable(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/SectionsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        }catch(Exception e ){
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showFornecedoresTable(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/FornecedoresPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        }catch(Exception e ){
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }


}
