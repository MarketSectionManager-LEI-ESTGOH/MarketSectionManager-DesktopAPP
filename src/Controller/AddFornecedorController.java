package Controller;

import Model.ConnectDB;
import Model.Fornecedor;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

public class AddFornecedorController {

    @FXML
    private TextField NameFornTF;

    @FXML
    private TextField idFornTF;

    @FXML
    private TextField contactoFornTF;

    @FXML
    private TextField emailFornTF;

    @FXML
    private TextField moradaFornTF;

    @FXML
    private Button addFornBtn;

    /**
     * Adiciona novo Fornecedor.
     */
    public void addForn(){
        System.out.println("add Forn btn clicked!!");
        ConnectDB.loadProperties();
        if(NameFornTF.getText()!= null){
            if(User.checkEmail(emailFornTF.getText())){
                if(!Fornecedor.checkIdentificador(idFornTF.getText())){
                    if(Fornecedor.addFornecedor(idFornTF.getText(), NameFornTF.getText(), Integer.parseInt(contactoFornTF.getText()), emailFornTF.getText(), moradaFornTF.getText())){
                        MainScreenController.alerts(Alert.AlertType.INFORMATION,"SUCESSO","O Fornecedor (" + idFornTF.getText() + ") - " + NameFornTF.getText() + " foi introduzido com sucesso!").showAndWait();
                    }else{
                        MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Erro ao introduzir o utilizador, por favor tente novamente!").showAndWait();
                    }
                    clearFornRegistrationFileds();
                }else{
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "O Email introduzido já está em uso!").showAndWait();
                }
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "O Email introduzido não é válido!\nPor favor introduza o email no formato [nome]@[servidor].[domínio]\n(Ex.: john.doe@msm.com)").showAndWait();
            }
        }else{
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Nenhum nome inserido!").showAndWait();
        }
    }

    private void clearFornRegistrationFileds(){
        idFornTF.setText("");
        NameFornTF.setText("");
        contactoFornTF.setText("");
        emailFornTF.setText("");
        moradaFornTF.setText("");
    }
}