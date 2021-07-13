package Controller;

import Model.ConnectDB;
import Model.User;
import javafx.scene.control.*;

public class AddUserController {

    public ComboBox userTypeCombo = new ComboBox();
    public TextField userNameTF = new TextField();
    public TextField userNumberTF = new TextField();
    public TextField userEmailTF = new TextField();
    public PasswordField userPassPF = new PasswordField();
    public PasswordField userPassConfPF = new PasswordField();
    public Button addCAComponentsBTN;
    public Button addCABTN;

    /**
     * Adicionar Utilizador
     */
    public void addUser(){
        ConnectDB.loadProperties();
        if(userTypeCombo.getSelectionModel().getSelectedIndex() != -1){
            if(userPassPF.getText().equals(userPassConfPF.getText())){
                if(User.checkEmail(userEmailTF.getText())){
                    if(!User.checkEmailExists(userEmailTF.getText())){
                        if(Main.u.registerUser(userTypeCombo.getSelectionModel().getSelectedIndex(), userNameTF.getText(), Integer.parseInt(userNumberTF.getText()), userPassPF.getText(), userEmailTF.getText())){
                            MainScreenController.alerts(Alert.AlertType.INFORMATION,"SUCESSO","O " + userTypeCombo.getSelectionModel().getSelectedItem() + " (" + userNumberTF.getText() + ") - " + userNameTF.getText() + " foi introduzido com sucesso!").showAndWait();
                        }else{
                            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Erro ao introduzir o utilizador, por favor tente novamente!").showAndWait();
                        }
                        clearUserRegistrationFileds();
                    }else{
                        MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "O Email introduzido já está em uso!").showAndWait();
                    }
                }else{
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "O Email introduzido não é válido!\nPor favor introduza o email no formato [nome]@[servidor].[domínio]\n(Ex.: john.doe@msm.com)").showAndWait();
                }
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "As Palavras Passe não Coíncidem, por favor tente novamente!").showAndWait();
            }
        }else{
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "O tipo de utilizador é de preenchimento obrigatório!").showAndWait();
        }
        clearUserRegistrationFileds();
    }

    /**
     * Limpar o campos de inserção do utilizador após o término da mesma operação
     */
    private void clearUserRegistrationFileds(){
        userTypeCombo.getSelectionModel().select(-1);
        userNameTF.setText("");
        userNumberTF.setText("");
        userPassPF.setText("");
        userPassConfPF.setText("");
        userEmailTF.setText("");
    }
}
