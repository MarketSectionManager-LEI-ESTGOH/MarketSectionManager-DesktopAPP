package Controller;

import Model.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;

public class AddExpirationDateController {

    @FXML
    private Text hidenLBLAddVal;
    @FXML
    private TextField nIntAddValTF;
    @FXML
    private DatePicker valAddValDP;
    @FXML
    private Button saveBtn;

    @FXML
    protected void initialize(){
        saveBtn.setDisable(true);
        hidenLBLAddVal.setVisible(false);
    }

    public void checkIfProductExists(){
        int inputedCode = -1;
        String prodName;
        System.out.println("Verifying if Product exists ...");
        System.out.println(" nIntAddValTF @ the Moment = " + nIntAddValTF.getText() + "_ Lenght: " + nIntAddValTF.getText().length() + ";; ");
        try{
            System.out.println("::: TRY ::: ");
            if(nIntAddValTF.getText().length() > 0){
                inputedCode = Integer.parseInt(nIntAddValTF.getText());
            }
            System.out.println(">> passou o parse");
            if(nIntAddValTF.getText().equalsIgnoreCase("") || nIntAddValTF.getText().length() == 0){
                System.out.println("Entrou no nIntAddValTF.getText().length() == 0");
                prodName = null;
            }else{
                System.out.println("Entrou no __ELSE__ nIntAddValTF.getText().length() == 0");
                prodName = ConnectDB.getString(ps(inputedCode));
            }

            if( prodName != null){
                System.out.println("prodName != null");
                hidenLBLAddVal.setText(ConnectDB.getString(ps(inputedCode)));
                hidenLBLAddVal.setVisible(true);
                saveBtn.setDisable(false);
                System.out.println("__ Produto Encontrado !!");
            }else{
                System.out.println(" prodName = null; ");
                prodName = null;
                hidenLBLAddVal.setVisible(false);
                saveBtn.setDisable(true);
                System.out.println("__ Produto Não Encontrado !!");
            }
            if(nIntAddValTF.getText().equalsIgnoreCase("")){
                System.out.println("__ nIntAddValTF.getText().equalsIgnoreCase __");
                hidenLBLAddVal.setText("");
                prodName = null;
                System.out.println("__ Produto Não Encontrado !!");
                hidenLBLAddVal.setVisible(false);
                saveBtn.setDisable(true);
            }
        }catch(NumberFormatException nfe){
            if(!nIntAddValTF.getText().isEmpty()){
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Este Campo não aceita Letras, APENAS NÚMEROS").showAndWait();
                nIntAddValTF.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private PreparedStatement ps(int aCode){
        String stmt = "SELECT nome FROM produto WHERE n_interno = ? OR ean = ?";
        try {
            PreparedStatement prepSt = ConnectDB.getConn().prepareStatement(stmt);
            prepSt.setInt(1,aCode);
            prepSt.setInt(2,aCode);
            return prepSt;
        }catch (Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
        return null;
    }

    public void saveExpirationDate(){

    }
}
