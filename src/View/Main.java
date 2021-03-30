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

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        primaryStage.setTitle("Market Section Manager");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args)  {


        //ISTO É APENAS UM TESTE AO FICHEIRO DAS PROPRIEDADES
        ConnectDB c = new ConnectDB();

        c.loadProperties();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(c.getUrl(), c.getProperties());
            PreparedStatement ps = conn.prepareStatement("SELECT nome FROM user WHERE nome = ?");
            ps.clearParameters();
            ps.setString(1, "John Doe");

            if(c.checkString(ps, "John Doe")){
                System.out.println("yes");
            }else{
                System.out.println("no");
            }
            //Dá RUN AO FRAME
            launch(args);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

    }

}
