package View;

import Model.ConnectDB;

import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

    public static Controller.User u = new Controller.User();
    public TextField funcNumber = new TextField();
    public PasswordField funcPass = new PasswordField();

    @Override
    public void start(Stage loginScreen) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        loginScreen.setTitle("Market Section Manager");
        loginScreen.setScene(new Scene(root, 360, 275));
        loginScreen.initStyle(StageStyle.UNDECORATED);
        loginScreen.setResizable(false);
        loginScreen.centerOnScreen();
        loginScreen.show();
    }

    /**
     * Main Function
     * @param args
     */
    public static void main(String[] args)  {
            launch(args);
    }

    /**
     * Apresenta uma cofirmação ao utilizador e se este clicar "Sim" o programa termina
     */
    public void closeProgram(){
        System.out.println("closed pushed!!");
        Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        exitConfirmation.setTitle("Confirmação de Saída");
        exitConfirmation.setHeaderText("Tem a certeza que pretende sair do programa?");
        ButtonType yesBtn = new ButtonType("Sim");
        ButtonType noBtn = new ButtonType("Não");
        exitConfirmation.getButtonTypes().setAll(yesBtn, noBtn);
        exitConfirmation.showAndWait();
        if(exitConfirmation.getResult().equals(yesBtn)){
            System.out.println("é isto!!");
            System.exit(0);
        }

    };

    /**
     * Login
     */
    public void login(){
        ConnectDB.loadProperties();
        try{
            int number = Integer.parseInt(funcNumber.getText());
            System.out.println("number::" + number + "\nfuncPass::"+ funcPass.getText());
            if(u.checkLogin(number, funcPass.getText())){
                alerts(Alert.AlertType.INFORMATION,"Sucesso", "Bem Vindo!").showAndWait();
            }else{
                alerts(Alert.AlertType.ERROR,"Credenciais Erradas", "O Número de Funcionário ou a Password estão errados, por favor tente novamente!").showAndWait();
                funcNumber.setText("");
                funcPass.setText("");
            }
        }catch(NumberFormatException nfe){
            alerts(Alert.AlertType.ERROR,"ERRO", "O Campo Número de Funcionário apenas aceita números, por favor tente novamente!").showAndWait();
        }catch(Exception e){
            alerts(Alert.AlertType.ERROR,"ERRO", "Aconteceu um erro insperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Dá Alert PopUps
     * @param aAlertType Objeto Alert.AlertType, define o tipo de alerta (Erro, Info, ...)
     * @param aTitle Titulo do PopUp
     * @param aText Texto o PopUp
     * @return Objeto do Tipo Alert
     */
    private Alert alerts(Alert.AlertType aAlertType, String aTitle, String aText){
        Alert generalAlert = new Alert(aAlertType);
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText(aText);
        return generalAlert;
    }

}
