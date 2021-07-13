package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
    private Button allExpirationDates = new Button();
    @FXML
    public Pane receivedPane;
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
    @FXML
    private Tooltip nameTT = new Tooltip();

    /**
     * Apresenta o Ecrã principal da aplicação
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
    /**
     * Define o nome do utilizador no campo para o mesmo especificado e avalia o comprimento do nome para
     * que este não se sobreponha a outros elementos gráficos (se o nome for extenso, apenas apresenta parte
     * do mesmo)
     */
    protected void initialize(){
        editProfileBtn.setUnderline(true);
        editProfileBtn.setTextFill(javafx.scene.paint.Color.valueOf("#6bbfa3"));
        String username = Main.u.getUsername();
        nameTT.setText(username);
        if(username.length() > 20){
            usernameLBL.setText((username.substring(0,16) + "..." ));
        }else {
            usernameLBL.setText(username);
        }
        showAllGraphs();
    }

    /**
     * Mostra a Pane de Edição do Perfil de Utilizador
     */
    public void editProfile() {
        try{
            EditUserController.setThisUser(Main.u);
            EditUserController.setSelfProfile(true);
            Stage EditStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View/EditUser.fxml"));
            EditStage.setScene(new Scene(root));
            EditStage.getIcons().add(new Image("/Images/logoicon.png"));
            EditStage.setTitle("Editar Perfil");
            EditStage.setResizable(false);
            EditStage.centerOnScreen();
            EditStage.show();
        }catch (Exception e){
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Permite ao utilizador realizar o LogOut da Aplicação
     * @throws Exception
     */
    public void logout() throws Exception {
        currentStage = (Stage) editProfileBtn.getScene().getWindow();
        currentStage.close();
        Main.u = null;
        new Main().start(new Stage());

    }

    /**
     * Permite lançar diversos tipos de Alertas em forma de um PopUp
     * @param aAlertType Objeto do Tipo Alert.AlertType.xxxx que define o tipo de popup em questão (ERROR, INFORMATION, WARNING, ...)
     * @param aTitle     Título do PopUp
     * @param aText      Texto Descritivo do PopUp
     * @return Alert (é necessária a utilização de «-showAndWait()» para a visualização do popup.
     */
    protected static Alert alerts(Alert.AlertType aAlertType, String aTitle, String aText) {
        Alert generalAlert = new Alert(aAlertType);
        Stage stage = (Stage) generalAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Images/logoicon.png"));
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText(aTitle);
        generalAlert.setContentText(aText);
        generalAlert.getDialogPane().setStyle("-fx-background-color: #ffffff");
        return generalAlert;
    }

    /**
     * Minimiza as Panes que se encontrarem abertas no ecrã principal da aplicação
     */
    public void collapse() {
        expandGraphs();
        resetButtonStyle();
        if (receivedPane != null) {
            receivedPane.setVisible(false);
        }
    }

    /**
     * Mostra a Pane dos Utilizadores da Aplicação
     */
    public void showUsersTable() {
        collapseGraphs();
        resetButtonStyle();
        editBTN.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/AllUsersPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra o Gráfico do Canto Superior Esquerdo da Dashboard
     */
    public void showGraph1() {
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/Graph1.fxml"));
            graph1Pane.getChildren().add(newLoadedPane);
            graph1Pane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra o Gráfico do Canto Superior Direito da Dashboard
     */
    public void showGraph2(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/Graph2.fxml"));
            graph2Pane.getChildren().add(newLoadedPane);
            graph2Pane.setVisible(true);

        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra a Tabela do Canto Inferior Esquerdo da Dashboard
     */
    public void showTable1(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/Table1.fxml"));
            graph3Pane.getChildren().add(newLoadedPane);
            graph3Pane.setVisible(true);

        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra a Tabela do Canto Inferior Direito da Dashboard
     */
    public void showTable2(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/Table2.fxml"));
            graph4Pane.getChildren().add(newLoadedPane);
            graph4Pane.setVisible(true);

        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra todos os elementos da Dashboard
     */
    private void showAllGraphs(){
        showGraph1();
        showGraph2();
        showTable1();
        showTable2();
    }

    /**
     * Mostra a Pane das Áreas Refrigeradas
     */
    public void showRefrigeratorsTable() {
        collapseGraphs();
        resetButtonStyle();
        refrigeratorsButton.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/RefrigeratorsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);

            receivedPane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra a Pane dos Produtos
     */
    public void showProductsTable() {
        collapseGraphs();
        resetButtonStyle();
        editProducts.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/ProductsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra a Pane das Áreas Controladas
     */
    public void showControlledAreasTable() {
        collapseGraphs();
        resetButtonStyle();
        controlledAreasButton.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/SectionsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra a Pane dos Fornecedores
     */
    public void showFornecedoresTable() {
        collapseGraphs();
        resetButtonStyle();
        suppliersButton.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/FornecedoresPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra a Pane das Validações (Rastreabilidades, Temperaturas e Limpezas)
     */
    public void showValidationsPane() {
        collapseGraphs();
        resetButtonStyle();
        validate.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/ValidatePane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra a Pane dos dados sujeitos a validação (Rastreabilidades, Temperaturas e Limpezas)
     */
    public void showAllRastData() {
        collapseGraphs();
        resetButtonStyle();
        allRastData.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/allDataPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Mostra a Pane das Datas de Validade (onde é possivel alterar/adicionar datas de validades, offset e markdowns
     */
    public void showAllExpirationDates(){
        collapseGraphs();
        resetButtonStyle();
        allExpirationDates.setStyle(BUTTON_SELECTED);
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/ExpirationDatesPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um Erro Inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Restaurar o estilo original dos botões do ecrã principal
     */
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
        allExpirationDates.setStyle(originalStyle);
    }

    /**
     * Minimizar todos os elemnetos da Dashboard
     */
    private void collapseGraphs(){
        graph1Pane.setVisible(false);
        graph2Pane.setVisible(false);
        graph3Pane.setVisible(false);
        graph4Pane.setVisible(false);
    }

    /**
     * Maximizar todos os elementos da Dashboard
     */
    private void expandGraphs(){
        graph1Pane.setVisible(true);
        graph2Pane.setVisible(true);
        graph3Pane.setVisible(true);
        graph4Pane.setVisible(true);
    }

}
