package Controller;

import Model.ConnectDB;
import Model.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class Main extends Application {

    public static User u;
    public TextField funcNumber = new TextField();
    public PasswordField funcPass = new PasswordField();
    public Button loginBtn = new Button();
    private Stage currentStage = null;
    @FXML
    private Text capsLbl;
    boolean isCapsOn = false;
    @FXML
    private Button minimizeBTN;


    @Override
    /**
     * Dá Load ao ecrã de Login
     */
    public void start(Stage loginScreen) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        loginScreen.getIcons().add(new Image("/Images/logoicon.png"));
        loginScreen.setTitle("Market Section Manager");
        loginScreen.setScene(new Scene(root, 360, 275));
        loginScreen.initStyle(StageStyle.UNDECORATED);
        loginScreen.setResizable(false);
        loginScreen.centerOnScreen();
        loginScreen.show();
    }

    @FXML
    /**
     * Inicializa os componentes gráficos da aplicação, de modo a providenciar
     * um alerta de Caps Lock ativo/inativo na PasswordField
     */
    protected void initialize() {
        funcPass.setOnMouseClicked( event -> {
            if ( Toolkit.getDefaultToolkit().getLockingKeyState(20) ) {
                capsLbl.setVisible(true);
                isCapsOn = true;
            }
        });
        funcPass.setOnKeyReleased( event -> {
            if ( event.getCode() == KeyCode.CAPS ) {
                if(isCapsOn){
                    isCapsOn = false;
                    capsLbl.setVisible(false);
                }else{
                    isCapsOn = true;
                    capsLbl.setVisible(true);
                }
            }
        });
    }

    /**
     * Main Function - Lança a Aplicação
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Apresenta uma confirmação ao utilizador e se este clicar "Sim" o programa termina
     * Fonte de Definição do Logotipo em Alerts: https://stackoverflow.com/questions/27976345/how-do-you-set-the-icon-of-a-dialog-control-java-fx-java-8
     */
    public void closeProgram() {
        Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) exitConfirmation.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Images/logoicon.png").toString()));
        exitConfirmation.setTitle("Confirmação de Saída");
        exitConfirmation.setHeaderText("Tem a certeza que pretende sair do programa?");
        ButtonType yesBtn = new ButtonType("Sim");
        ButtonType noBtn = new ButtonType("Não");
        exitConfirmation.getButtonTypes().setAll(yesBtn, noBtn);
        exitConfirmation.showAndWait();
        if (exitConfirmation.getResult().equals(yesBtn)) {
            System.exit(0);
        }

    }

    /**
     * Minimiza o Ecrã de Login
     */
    public void minimizePRG() {
        Stage stage = (Stage) minimizeBTN.getScene().getWindow();
        stage.setIconified(true);
    }


    /**
     * Login
     */
    public void login() {
        ConnectDB.loadProperties();
        try {
            int number = Integer.parseInt(funcNumber.getText());
            if (Model.User.checkLogin(number, funcPass.getText())) {
                cleanFields();
                currentStage = (Stage) loginBtn.getScene().getWindow();

                //Criar novo objeto utilizador
                u = User.createObjUser(number);
                if (u.getUserType() == 1) {
                    cleanFields();
                    currentStage.close();
                    new MainScreenController().start();
                } else {
                    alerts(Alert.AlertType.ERROR, "Login Inválido", "Este programa apenas permite o acesso a Administradores, pelo que a sua conta não tem permissão para fazer Login no mesmo!").showAndWait();
                    u = null;
                    cleanFields();
                }
            } else {
                alerts(Alert.AlertType.ERROR, "Credenciais Erradas", "O Número de Funcionário ou a Password estão errados, por favor tente novamente!").showAndWait();
                funcNumber.requestFocus();
                cleanFields();
            }
        } catch (NumberFormatException nfe) {
            alerts(Alert.AlertType.ERROR, "ERRO", "O Campo Número de Funcionário apenas aceita números, por favor tente novamente!").showAndWait();
            cleanFields();
        } catch (Exception e) {
            alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
            cleanFields();
        }
    }

    private final void cleanFields() {
        funcNumber.setText("");
        funcPass.setText("");
    }

    /**
     * Dá Alert PopUps
     *
     * @param aAlertType Objeto Alert.AlertType, define o tipo de alerta (Erro, Info, ...)
     * @param aTitle     Titulo do PopUp
     * @param aText      Texto o PopUp
     * @return Objeto do Tipo Alert
     */
    private Alert alerts(Alert.AlertType aAlertType, String aTitle, String aText) {
        Alert generalAlert = new Alert(aAlertType);
        Stage stage = (Stage) generalAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Images/logoicon.png"));
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText("Aconteceu um Erro: ");
        generalAlert.setContentText(aText);
        return generalAlert;
    }

    /**
     * Evento do tipo KeyEvent que é aplicado ao campo de palavra-passe do ecrã de
     * login e que permite que o utilizador confirme a "inteção de fazer login" com
     * apenas um clique no enter deste campo (PasswordField)
     * @param ke
     */
    public void detectEnterPressed(KeyEvent ke) {
        funcPass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    login();
                }
            }
        });
    }
}
