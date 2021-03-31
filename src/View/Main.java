package View;

import Model.ConnectDB;
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

    public static void main(String[] args)  {
            //Dá RUN AO FRAME
            launch(args);
    }

}
