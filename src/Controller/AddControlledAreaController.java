package Controller;

import Model.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddControlledAreaController {

    public Button addCAComponentsBTN;
    public Button addCABTN;
    @FXML
    private TextField addCANumberTF;
    @FXML
    private TextField addCADesignTF;

    public void insertControlledArea(){
        System.out.println(" -- @ insertControllerArea() --");
        try{
            int areaNumber = -1;
            if(!addCANumberTF.getText().isEmpty()){
                areaNumber = Integer.parseInt(addCANumberTF.getText());
                if(!addCADesignTF.getText().isEmpty()){
                    if(registerControlledArea(areaNumber, addCADesignTF.getText())){
                        MainScreenController.alerts(Alert.AlertType.INFORMATION, "SUCESSO", " A Área Controlada " + addCADesignTF.getText() + " com o Número Interno " + areaNumber + "\nfoi inserida com sucesso!").showAndWait();
                        cleanControlledAreaFields();
                    }else{
                        MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inseperado, por favor tente novamente!").showAndWait();
                    }
                }else{
                    System.out.println("design empty");
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "A Designação da Área Controlada é de Preenchimento Obrigatório!").showAndWait();
                }
            }else{
                System.out.println("number empty");
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "O número da Área Controlada é de Preenchimento Obrigatório!").showAndWait();
            }
        }catch(Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Algo correu mal, por favor tente novamente! \n\n " + e).showAndWait();

        }
    }

    private void cleanControlledAreaFields(){
        addCANumberTF.setText("");
        addCADesignTF.setText("");
    }

    private boolean registerControlledArea(int aNumber, String aDesign){
        try {
            String stmt = "INSERT INTO area (numero, designacao) VALUES (?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumber);
            ps.setString(2,aDesign);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
