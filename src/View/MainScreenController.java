package View;

import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.soap.Text;

public class MainScreenController {

    public Button editProfileBtn = new Button();
    public Button logoutBtn = new Button();




    public void start() throws Exception{
        Stage MainScreen = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        MainScreen.setTitle("Market Section Manager");
        MainScreen.setScene(new Scene(root, 1200, 602));
        //MainScreen.initStyle(StageStyle.UNDECORATED);
        MainScreen.setResizable(false);
        MainScreen.centerOnScreen();
        MainScreen.show();
        //editProfileBtn.setText(View.Main.u.getUsername());
    }


    public void editProfile(){
        System.out.println("edit profile btn clicked");

    }

    public void logout(){
        System.out.println("logout btn clicked");
    }
}
