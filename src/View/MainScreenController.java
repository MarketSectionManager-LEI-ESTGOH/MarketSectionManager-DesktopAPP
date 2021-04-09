package View;

import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.soap.Text;

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

    }

    public void collapse(){
        registerPane.setVisible(false);

    }
}
