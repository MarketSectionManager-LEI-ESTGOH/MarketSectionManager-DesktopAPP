package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainScreenController{

    public Button editProfileBtn = new Button();
    private Stage currentStage = null;
    private boolean firstUserRun = false;
    @FXML
    private Pane receivedPane;

    /**
     * Referencia apresentação de dados: 20/04/2021
     * https://www.youtube.com/watch?v=tw_NXq08NUE
     *
     * Referência filtro de pesquisa: 26/04/2021
     * https://www.youtube.com/watch?v=FeTrcNBVWtg
     *
     */
    public void start() throws Exception{
        Stage MainScreen = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        MainScreen.getIcons().add(new Image("/Images/logoicon.png"));
        MainScreen.setTitle("Market Section Manager");
        MainScreen.setScene(new Scene(root, 1200, 602));
        MainScreen.setResizable(false);
        MainScreen.centerOnScreen();
        MainScreen.show();
    }

    public void editProfile(){
        alerts(Alert.AlertType.WARNING, "Função Indisponível", "Esta função ainda não está disponível!").showAndWait();

    }

    public void logout() throws Exception{
        currentStage = (Stage) editProfileBtn.getScene().getWindow();
        currentStage.close();
        Main.u = null;
        new Main().start(new Stage());

    }

    protected static Alert alerts(Alert.AlertType aAlertType, String aTitle, String aText){
        Alert generalAlert = new Alert(aAlertType);
        Stage stage = (Stage) generalAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Images/logoicon.png"));
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText(aTitle);
        generalAlert.setContentText(aText);
        return generalAlert;
    }

    public void collapse(){
        System.out.println("home (collapse) clicked!");

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

    public void showValidationsPane(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/ValidatePane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        }catch(Exception e ){
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }


}
