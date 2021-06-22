package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;


public class MainScreenController {

    private final String BUTTON_SELECTED = "-fx-background-color: #6bbfa3;";
    private Stage currentStage = null;
    private boolean firstUserRun = false;
    @FXML
    private Button controlledAreasButton = new Button();
    @FXML
    private Button refrigeratorsButton = new Button();
    @FXML
    private Button editProducts = new Button();
    @FXML
    private Button editBTN = new Button();
    @FXML
    private Button validate = new Button();
    @FXML
    private Button allRastData = new Button();
    @FXML
    private Button editProfileBtn = new Button();
    @FXML
    private Button suppliersButton = new Button();
    @FXML
    private Pane receivedPane;
    @FXML
    private Text usernameLBL;
    @FXML
    private Pane graph1Pane = new Pane();
    @FXML
    private Pane graph2Pane = new Pane();

    @FXML
    private Pane graph3Pane = new Pane();

    @FXML
    private Pane graph4Pane = new Pane();

    /**
     * Referencia apresentação de dados: 20/04/2021
     * https://www.youtube.com/watch?v=tw_NXq08NUE
     * <p>
     * Referência filtro de pesquisa: 26/04/2021
     * https://www.youtube.com/watch?v=FeTrcNBVWtg
     */
    public void start() throws Exception {
        Stage MainScreen = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        MainScreen.getIcons().add(new Image("/Images/logoicon.png"));
        MainScreen.setTitle("Market Section Manager");
        MainScreen.setScene(new Scene(root, 1200, 602));
        MainScreen.setResizable(false);
        MainScreen.centerOnScreen();
        MainScreen.show();
    }

    @FXML
    protected void initialize(){
        editProfileBtn.setUnderline(true);
        editProfileBtn.setTextFill(javafx.scene.paint.Color.valueOf("#6bbfa3"));
        usernameLBL.setText(Main.u.getUsername());
        showAllGraphs();
    }

    public void editProfile() {
        alerts(Alert.AlertType.WARNING, "Função Indisponível", "Esta função ainda não está disponível!").showAndWait();

    }

    public void logout() throws Exception {
        currentStage = (Stage) editProfileBtn.getScene().getWindow();
        currentStage.close();
        Main.u = null;
        new Main().start(new Stage());

    }

    protected static Alert alerts(Alert.AlertType aAlertType, String aTitle, String aText) {
        Alert generalAlert = new Alert(aAlertType);
        Stage stage = (Stage) generalAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Images/logoicon.png"));
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText(aTitle);
        generalAlert.setContentText(aText);
        return generalAlert;
    }

    public void collapse() {
        System.out.println("home (collapse) clicked!");
        expandGraphs();
        resetButtonStyle();
        if (receivedPane != null) {
            receivedPane.setVisible(false);
        }
    }

    public void showUsersTable() {
        collapseGraphs();
        resetButtonStyle();
        editBTN.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/AllUsersPane.fxml"));

            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);

        } catch (Exception e) {
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showGraph1() {
        System.out.println("showgraph1  __ here");
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/Graph1.fxml"));
            graph1Pane.getChildren().add(newLoadedPane);
            graph1Pane.setVisible(true);

        } catch (Exception e) {
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }
    public void showGraph2(){}
    public void showTable1(){}
    public void showTable2(){}

    private void showAllGraphs(){
        showGraph1();
        showGraph2();
        showTable1();
        showTable2();
    }

    public void showRefrigeratorsTable() {
        collapseGraphs();
        resetButtonStyle();
        refrigeratorsButton.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/RefrigeratorsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);

            receivedPane.setVisible(true);
        } catch (Exception e) {
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showProductsTable() {
        collapseGraphs();
        resetButtonStyle();
        editProducts.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/ProductsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showControlledAreasTable() {
        collapseGraphs();
        resetButtonStyle();
        controlledAreasButton.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/SectionsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showFornecedoresTable() {
        collapseGraphs();
        resetButtonStyle();
        suppliersButton.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/FornecedoresPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showValidationsPane() {
        collapseGraphs();
        resetButtonStyle();
        validate.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/ValidatePane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showAllRastData() {
        collapseGraphs();
        resetButtonStyle();
        allRastData.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/allDataPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    private void resetButtonStyle() {
        String originalStyle = "-fx-background-color: #253437;";
        controlledAreasButton.setStyle(originalStyle);
        refrigeratorsButton.setStyle(originalStyle);
        editProducts.setStyle(originalStyle);
        editBTN.setStyle(originalStyle);
        validate.setStyle(originalStyle);
        allRastData.setStyle(originalStyle);
        editProfileBtn.setStyle(originalStyle);
        suppliersButton.setStyle(originalStyle);
    }

    private void collapseGraphs(){
        graph1Pane.setVisible(false);
        graph2Pane.setVisible(false);
        graph3Pane.setVisible(false);
        graph4Pane.setVisible(false);
    }

    private void expandGraphs(){
        graph1Pane.setVisible(true);
        graph2Pane.setVisible(true);
        graph3Pane.setVisible(true);
        graph4Pane.setVisible(true);
    }

}
