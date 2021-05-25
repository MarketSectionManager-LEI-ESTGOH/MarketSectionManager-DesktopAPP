package Controller;

import Model.User;
import Model.ConnectDB;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static User u;
    public TextField funcNumber = new TextField();
    public PasswordField funcPass = new PasswordField();
    public Button loginBtn = new Button();
    private Stage currentStage = null;


    @Override
    public void start(Stage loginScreen) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        loginScreen.setTitle("Market Section Manager");
        loginScreen.setScene(new Scene(root, 360, 275));
        loginScreen.initStyle(StageStyle.UNDECORATED);
        loginScreen.setResizable(false);
        loginScreen.centerOnScreen();
        loginScreen.show();
    }

    /**
     * Main Function
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Apresenta uma cofirmação ao utilizador e se este clicar "Sim" o programa termina
     */
    public void closeProgram() {
        Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
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

    ;

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
                cleanFields();
            }
        } catch (NumberFormatException nfe) {
            alerts(Alert.AlertType.ERROR, "ERRO", "O Campo Número de Funcionário apenas aceita números, por favor tente novamente!").showAndWait();
            cleanFields();
        } catch (Exception e) {
            System.out.println(e);
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
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText("Aconteceu um Erro: ");
        generalAlert.setContentText(aText);
        return generalAlert;
    }

    public void detectEnterPressed(KeyEvent ke){
        funcPass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("enter pressed!");
                    login();
                }
            }
        });
    }

}